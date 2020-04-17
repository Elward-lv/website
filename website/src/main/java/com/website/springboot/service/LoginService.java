package com.website.springboot.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;

/**
 * 注册登陆功能的服务接口
 */
public interface LoginService {
    /**
     * 提交注册表单
     * @param jsonObject
     * @return
     */
    public JSONObject authLogin(JSONObject jsonObject, HttpSession session);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    JSONObject getUser(String username, String password);

    /**
     * 查询当前登录用户信息
     */
    JSONObject getInfo(HttpSession session);

    /**
     * 退出登录
     */
    JSONObject logout(HttpSession session);

    /**
     * 获取注册的code
     */
    JSONObject getRegisterCodeJson(int code);

}
