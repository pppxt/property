package com.project.properity.mapper;

import com.project.properity.pojo.Building;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingMapper {

    //查询
    List<Building> list(String name, String comName);

    @Insert("insert into building(name, typeId) values(#{name},#{typeId})")
    void insert(Building building);

    void delete(List<Integer> ids);

    void update(Building building);

}
