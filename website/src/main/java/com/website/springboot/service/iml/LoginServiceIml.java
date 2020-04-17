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
        String password = jsonObject.getString("password");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setIsRoot(0);
        userMapper.insertUser(user);
        //设置session的登陆信息
        session.setAttribute("user",user);
        logger.info(((User)session.getAttribute("user")).toString());
        JSONObject info  = new JSONObject();
        info.put("result","success");
        return CommonUtil.successJson(info);
    }

    @Override
    public JSONObject getUser(String username, String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        User result = userMapper.queryUserByNP(user);
        if(result!=null){
           CommonUtil.successJson("登陆成功");
        }
        return CommonUtil.errorJson(ErrorEnum.E_502);
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
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getRegisterCodeJson(int code) {
        JSONObject code_json = new JSONObject();
        code_json.put("RegisterCode",code);
        return CommonUtil.successJson(code_json);
    }
}
