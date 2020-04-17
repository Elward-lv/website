package com.website.springboot.mapper;

import com.website.springboot.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface UserMapper {
    @Select("select * from users where id = #{id}")
    public User queryUserById(User user);

    @Select("select * from users where name = #{name} and password =#{password}")
    public User queryUserByNP(User user);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into users (name,password,email,isRoot) values (#{name},#{password},#{email},0)")
    public int insertUser(User user);

    @Select("select * from users")
    public ArrayList<User> queryAll();

    @Update("update users set name = #{name},password = #{password},email = #{email},isRoot = #{isRoot}")
    public int updateUser(User user);

    @Delete("delete from users where id = #{id}")
    public int deleteUser(Integer id);

}
