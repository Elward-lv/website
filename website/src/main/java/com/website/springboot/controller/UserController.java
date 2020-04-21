package com.website.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.website.springboot.Util.CommonUtil;
import com.website.springboot.bean.User;
import com.website.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 简单测试User的使用,RESTful 风格的CRUD
 */
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;
    /**
     * 查询某个员工
     * @method: Get
     * @param id
     * @return
     */
    @CrossOrigin
    @GetMapping("/user/{id}")
    public JSONObject getUser(@PathVariable("id") Integer id){
        User user = new User();
        user.setId(id);
        return CommonUtil.successJson(userMapper.queryUserById(user));
    }
    /**
     * 查询所有员工
     * @param : null
     * @method: Get
     * @return  JSONObject
     */
    @CrossOrigin
    @GetMapping("/users")
    public JSONObject getUsers(){
        return CommonUtil.successJson(userMapper.queryAll());
    }

    /**
     * 添加新的用户
     * @param user
     * @method: Post
     * @return
     */
    @CrossOrigin
    @PostMapping("/user")
    public JSONObject addUser(User user){
        userMapper.insertUser(user);
        return CommonUtil.successJson("success");
    }
    /**
     * 更新用户
     * @param user
     * @method: Put
     * @return
     */
    @CrossOrigin
    @PutMapping ("/user")
    public JSONObject updateUser(User user){
        userMapper.updateUser(user);
        return CommonUtil.successJson("success");
    }
    /**
     * 删除用户
     * @param id
     * @method: Put
     * @return
     */
    @CrossOrigin
    @DeleteMapping("/{id}")
    public JSONObject delete(@PathVariable("id") Integer id){
        userMapper.deleteUser(id);
        return CommonUtil.successJson();
    }
}
