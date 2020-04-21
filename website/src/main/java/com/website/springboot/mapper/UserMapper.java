package com.website.springboot.mapper;

import com.website.springboot.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
@Mapper
public interface UserMapper {
    @Select("select * from users where id = #{id}")
    public User queryUserById(User user);

    @Select("select * from users where email = #{email} and password =#{password}")
    public User queryUserByNP(User user);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into users (password,email,isRoot) values (#{password},#{email},0)")
    public int insertUser(User user);

    @Select("select * from users")
    public ArrayList<User> queryAll();

    @Update("update users set password = #{password},email = #{email},isRoot = #{isRoot}")
    public int updateUser(User user);

    @Delete("delete from users where id = #{id}")
    public int deleteUser(Integer id);

}
