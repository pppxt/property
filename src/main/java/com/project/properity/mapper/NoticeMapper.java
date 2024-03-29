package com.project.properity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.properity.pojo.Notice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper extends BaseMapper<Notice> {

    List<Notice> list(String title);

    void delete(List<Integer> ids);

    void update(Notice notice);

    @Select("select * from notice where id = #{id} order by id desc")
    Notice selectById(Integer id);

}
