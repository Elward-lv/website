package com.website.springboot.service.iml;

import com.alibaba.fastjson.JSONObject;
import com.website.springboot.Util.CommonUtil;
import com.website.springboot.Util.constants.ErrorEnum;
import com.website.springboot.bean.User;
import com.website.springboot.mapper.UserMapper;
import com.website.springboot.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
@Service
public class LoginServiceIml implements LoginService {

    private Logger logger = LoggerFactory.getLogger(LoginServiceIml.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public JSONObject authLogin(JSONObject jsonObject, HttpSession session) {
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        User user = getUser(email,password);
        if(user==null){
            return  CommonUtil.errorJson(ErrorEnum.E_502);
        }
        setSession(user,session);
        logger.info(((User)session.getAttribute("user")).toString());
        JSONObject info  = new JSONObject();
        info.put("result","success");
        return CommonUtil.successJson(info);
    }

    @Override
    public JSONObject authRegister(JSONObject jsonObject, HttpSession session) {
        String password = jsonObject.getString("password");
        //String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        User user = new User();
        //user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        if(getUser(password,email)!=null){
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        user.setIsRoot(0);
        userMapper.insertUser(user);
        //调用登陆函数
        return authLogin(jsonObject,session);
    }

    public boolean setSession(User user,HttpSession session){
        //设置session的登陆信息
        session.setAttribute("user",user);
        return  true;
    }

    @Override
    public User getUser(String email, String password) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        User result = userMapper.queryUserByNP(user);
        if(result!=null){
            return  result;
           //CommonUtil.successJson("登陆成功");
        }
        return null;
        //return CommonUtil.errorJson(ErrorEnum.E_502);
    }

    @Override
    public JSONObject getInfo(HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user!= null){
            return CommonUtil.successJson(user);
        }
        return CommonUtil.errorJson(ErrorEnum.E_502);

    }

    @Override
    public JSONObject logout(HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user!= null) {
            session.removeAttribute("user");
        }
        JSONObject json = new JSONObject();
        json.put("result","success");
        return CommonUtil.successJson(json);
    }

    @Override
    public JSONObject getRegisterCodeJson(int code) {
        JSONObject code_json = new JSONObject();
        code_json.put("RegisterCode",code);
        return CommonUtil.successJson(code_json);
    }
}
