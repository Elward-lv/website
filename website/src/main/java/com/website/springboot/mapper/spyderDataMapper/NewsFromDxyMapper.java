package com.website.springboot.mapper.spyderDataMapper;

import com.website.springboot.bean.NewsFromDxy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface NewsFromDxyMapper {
    @Insert("insert into newsfromdxy(title,pub_date,infoSoure,summary,sourceUrl,updateTime) values(#{title},#{pubDate},#{infoSoure}," +
            "#{summary},#{sourceUrl},#{updateTime})")
    public int insertNews(NewsFromDxy newsFromDxy);

    @Select("select * from newsfromdxy limit #{start},#{len}")
    public ArrayList<NewsFromDxy> queryByLen(int start, int len);

}
