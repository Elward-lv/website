package com.website.springboot;

import com.website.springboot.bean.ChinaData;
import com.website.springboot.bean.NewsFromDxy;
import com.website.springboot.mapper.spyderDataMapper.ChinaDataMapper;
import com.website.springboot.mapper.spyderDataMapper.NewsFromDxyMapper;
import com.website.springboot.spyder.HttpClientUtil;
import com.website.springboot.spyder.WuhanService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

@SpringBootTest
class SpringbootApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(SpringbootApplicationTests.class);

    @Autowired
    ApplicationContext applicationContext;//应用上下文获取bean

    @Autowired
    ChinaDataMapper chinaDataMapper;

    @Autowired
    NewsFromDxyMapper newsFromDxyMapper;

    @Test
    void contextLoads() {
        ArrayList<NewsFromDxy> array= WuhanService.getAllTimelineService();
        for(NewsFromDxy newsFromDxy:array){
            newsFromDxyMapper.insertNews(newsFromDxy);
            logger.info(newsFromDxy.toString());
        }

//        ArrayList<ChinaData> array = chinaDataMapper.queryByPage(5,5);
//        for(ChinaData chinaData:array){
//            logger.info(chinaData.toString());
//        }
    }

}
