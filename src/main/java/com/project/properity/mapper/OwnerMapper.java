package com.project.properity.mapper;

import com.project.properity.pojo.Owner;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OwnerMapper {

    /**
     * 业主信息查询
     * @return
     */
    List<Owner> list(String name, String number, String phone);

    /**
     * 新增业主
     * @param owner
     */
    @Insert("insert into owner(address,name, number, phone, gender, status, store, notes)" +
            " values(#{address},#{name},#{number},#{phone},#{gender},#{status},#{store},#{notes})")
    void insert(Owner owner);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新
     * @param owner
     */
    void update(Owner owner);

    @Select("select * from owner")
    List<Owner> selectAll();
}

