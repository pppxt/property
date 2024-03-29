package com.project.properity.mapper;

import com.project.properity.pojo.Fix;
import com.project.properity.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FixMapper {

    //查询
    List<Fix> page(@Param("params")User params);

    List<Fix> list(String reason);

    @Insert("insert into fix( reason, time, address,status,cause,userId)" +
            " values(#{reason},#{time},#{address},#{status},#{cause},#{userId})")
    void insert(Fix fix);

    void delete(List<Integer> ids);

    void update(Fix fix);

    @Select("select * from fix")
    List<Fix> findAll();

}
