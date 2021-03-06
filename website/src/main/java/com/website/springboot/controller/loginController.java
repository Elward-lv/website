package com.website.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.website.springboot.Util.CommonUtil;
import com.website.springboot.Util.MailUtil;
import com.website.springboot.Util.constants.ErrorEnum;
import com.website.springboot.bean.User;
import com.website.springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 注册登陆的控制器
 * redirect:/main.html重定向
 */
@Controller
@RequestMapping("/api/login")
public class loginController {

    @Autowired
    LoginService loginService;

    @CrossOrigin
    @PostMapping(value = "/",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject,HttpServletRequest req){
        CommonUtil.hasAllRequired(jsonObject,"email,password");
        return loginService.authLogin(jsonObject,req.getSession());
    }

    /**
     * 注册code获取
     * @param jsonObject
     * @return json
     */
    @CrossOrigin
    @RequestMapping(value = "/getRegisterCode",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public JSONObject register(@RequestBody JSONObject jsonObject){
        System.out.println("email 发验证码了");
        CommonUtil.hasAllRequired(jsonObject,"email");
        String email = jsonObject.getString("email");
        int code =MailUtil.sendEmail(email);
        return loginService.getRegisterCodeJson(code);
    }

    /**
     * 注册功能实现
     * @return
     */
    @CrossOrigin
    @PostMapping("/register")
    @ResponseBody
    public JSONObject registerUser(@RequestBody JSONObject jsonObject, HttpServletRequest req){

        CommonUtil.hasAllRequired(jsonObject,"password,email");
        System.out.println("register中session Id:"+req.getSession().getId());
        return loginService.authRegister(jsonObject,req.getSession());
    }

    @CrossOrigin
    @PostMapping("/getInfo")
    @ResponseBody
    public JSONObject getInfo(HttpServletRequest req){

        System.out.println("getInfo中sessionId："+req.getSession().getId());
        return loginService.getInfo(req.getSession());
    }

    @CrossOrigin
    @PostMapping("/logout")
    @ResponseBody
    public JSONObject logout(HttpServletRequest req){

        return loginService.logout(req.getSession());
    }

    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @CrossOrigin
    @RequestMapping(value = "/api/login",method = RequestMethod.POST,produces = "application/json; charset=UTF-8")
    @ResponseBody
    public JSONObject login(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return CommonUtil.errorJson(ErrorEnum.E_90003);
        }else {
            //验证数据中是否含有匹配的用户
            return null;
        }
    }

}
