package com.project.properity.mapper;

import com.project.properity.pojo.Log;
import com.project.properity.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {

    //查询
    List<Log> page(@Param("params") User params);

    @Insert("insert into log( name, time, username,ip)" +
            " values(#{name},#{time},#{username},#{ip})")
    void insert(Log log);

    void delete(List<Integer> ids);

}
