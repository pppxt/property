package com.project.properity.mapper;

import com.project.properity.pojo.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsMapper {

    List<News> list(String title);
    /**
     * 新增
     * @param news
     */
    @Insert("insert into news(title,description, content,authorid,time)" +
            " values(#{title},#{description},#{content},#{authorid},#{time})")
    void insert(News news);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新
     * @param news
     */
    void update(News news);

    @Select("select * from news where id = #{id} order by id desc")
    News selectById(Integer id);
}
