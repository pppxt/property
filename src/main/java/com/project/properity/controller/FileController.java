package com.project.properity.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.project.properity.common.AuthAccess;
import com.project.properity.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.net.URLEncoder;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    //文件根目录,当前项目目录下files
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";

    //文件上传
    @PostMapping("/upload")
    //文件类型MultipartFile
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // a.png 即文件名.后缀名
        String mainName = FileUtil.mainName(originalFilename);  // a
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        // 如果当前上传的文件已经存在，那么重名一个文件名称
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        return Result.success(url);  //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是后台提供出来的
    }

    //文件下载
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  //附件下载
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        String filePath = ROOT_PATH  + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }

    @PostMapping("/editor/upload")
    public Dict editorUpload(@RequestParam MultipartFile file,@RequestParam String type) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称
        // a.png
        String mainName = FileUtil.mainName(originalFilename);  // a
        String extName = FileUtil.extName(originalFilename);// png
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);  // 如果当前文件的父级目录不存在，就创建
        }
        // 如果当前上传的文件已经存在，那么重名一个文件名称
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
            originalFilename = System.currentTimeMillis() + "_" + mainName + "." + extName;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(saveFile);  // 存储文件到本地的磁盘里面去
        String url = "http://" + ip + ":" + port + "/file/download/" + originalFilename;
        if ("img".equals(type)) {  // 上传图片
            return Dict.create().set("errno", 0).set("data", CollUtil.newArrayList(Dict.create().set("url", url)));
        } else if ("video".equals(type)) {
            return Dict.create().set("errno", 0).set("data", Dict.create().set("url", url));
        }
        return Dict.create().set("errno", 0);
    }
}
