package com.website.springboot.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取新闻的接口
 */
public interface NewsService {
    /**
     * 按照给出的开始和长度查询数据库的新闻
     * @param start
     * @param len
     * @return JSONObject
     */
    public JSONObject getNewsByLen(int start,int len);

}
