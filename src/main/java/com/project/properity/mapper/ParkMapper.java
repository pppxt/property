package com.project.properity.mapper;

import com.project.properity.pojo.Park;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParkMapper {

    List<Park> list(String name);
    /**
     * 新增
     * @param park
     */
    @Insert("insert into park(name,money,number,date,trips)" +
            " values(#{name},#{money},#{number},#{date},,#{trips})")
    void insert(Park park);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新
     * @param park
     */
    void update(Park park);

    /*@Select("select * from park where id = #{id} order by id desc")
    Park selectById(Integer id);
*/
    @Select("select * from park")
    List<Park> selectAll();
}
