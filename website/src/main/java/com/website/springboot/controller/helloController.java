package com.website.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.website.springboot.bean.TestBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试转化为JSON数据返回的controller
 */
@Controller
public class helloController {

    @CrossOrigin
    @ResponseBody
    @GetMapping("/testJson")
    public TestBean testJson(){
        TestBean testBean = new TestBean();
        testBean.setId(1);
        testBean.setName("lvyanwei");
        return  testBean;
    }

    @ResponseBody
    @RequestMapping(value = "/testJsonMap",produces="application/json;charset=UTF-8")
    public Map<String,Object> testJsonMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("博客地址", "http://blog.itcodai.com");
        map.put("CSDN地址", "http://blog.csdn.net/eson_15");
        map.put("粉丝数量", 4153);
        return map;
    }

    @GetMapping("/testJson2")
    public String testJson2(HttpServletResponse resp)  {
        TestBean bean = new TestBean();
        bean.setId(5);
        bean.setName("hello");
        String be = JSON.toJSONString(bean);
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.println(be);
            writer.flush();
            writer.close();
        } catch (IOException e) {e.printStackTrace();

        }
        return null;
    }

}
