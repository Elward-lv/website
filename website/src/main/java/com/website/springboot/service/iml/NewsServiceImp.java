package com.website.springboot.service.iml;

import com.alibaba.fastjson.JSONObject;
import com.website.springboot.Util.CommonUtil;
import com.website.springboot.bean.NewsFromDxy;
import com.website.springboot.mapper.spyderDataMapper.NewsFromDxyMapper;
import com.website.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NewsServiceImp implements NewsService {

    @Autowired
    NewsFromDxyMapper newsFromDxyMapper;

    @Override
    public JSONObject getNewsByLen(int start, int len) {
        JSONObject json = new JSONObject();
        ArrayList<NewsFromDxy> arrays= newsFromDxyMapper.queryByLen(start,len);
        json.put("result",arrays);
        return json;
    }
}
