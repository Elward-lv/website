package com.website.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.website.springboot.Util.CommonUtil;
import com.website.springboot.bean.ChinaData;
import com.website.springboot.mapper.spyderDataMapper.ChinaDataMapper;
import com.website.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 新闻和数据的控制器
 */
@RestController
public class newsController {
    @Autowired
    NewsService newsService;
    @Autowired
    ChinaDataMapper chinaDataMapper;

    @CrossOrigin
    @PostMapping("/news")
    public JSONObject getNews(@RequestBody JSONObject jsonObject){
        String start = jsonObject.getString("start");
        String len = jsonObject.getString("len");
        if(start==null || len == null){
            start = "0";
            len = "5";
        }
        int st = Integer.parseInt(start);
        int le = Integer.parseInt(len);
        return CommonUtil.successJson(newsService.getNewsByLen(st,le));
    }

    @CrossOrigin
    @PostMapping("/datas")
    public JSONObject getDatas(){
        ArrayList<ChinaData> arrayList = chinaDataMapper.queryAll();
        JSONObject json = new JSONObject();
        json.put("result",arrayList);
        return CommonUtil.successJson(json);
    }
}
