package com.project.properity.mapper;

import com.project.properity.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名和密码查询管理员
     * @param
     * @return
     */
    //指的是实体类中的属性名

    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);

    @Insert("insert into user(username, password,role) values(#{username},#{password},#{role})")
    void insert(User user);

    @Select("select * from user where id = #{id} order by id desc")
    User selectById(Integer id);

    void update(User user);

    List<User> list(String username);

}
