package com.website.springboot.mapper.spyderDataMapper;

import com.website.springboot.bean.ChinaData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ChinaDataMapper {

    @Insert("insert into chinadata(provinceName,currentConfirmedCount,confirmedCount,suspectedCount,curedCount,deadCount,statisticsData,updateTime)" +
            "values (#{provinceName},#{currentConfirmedCount},#{confirmedCount},#{suspectedCount},#{curedCount},#{deadCount},#{statisticsData},#{updateTime})")
    public int insert(ChinaData chinaData);

    /**
     * 分页查询
     * @Param: start:开始
     * @Param: nums:查询的个数
     */
    @Select("select * from chinadata limit #{start},#{nums}")
    public ArrayList<ChinaData> queryByPage(int start,int nums);
}
