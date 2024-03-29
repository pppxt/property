package com.project.properity.mapper;

import com.project.properity.pojo.Renter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RenterMapper {

    /**
     * 租户信息查询
     * @return
     */
    List<Renter> list(String name, String number, String phone);

    /**
     * 新增业主
     * @param renter
     */
    @Insert("insert into renter(address,name, number, phone, gender)" +
            " values(#{address},#{name},#{number},#{phone},#{gender})")
    void insert(Renter renter);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 更新
     * @param renter
     */
    void update(Renter renter);

    @Select("select address from renter ")
    List<Renter> findAll();
}

