package com.website.springboot.mapper.spyderDataMapper;

import com.website.springboot.bean.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpyderMapper {

    @Select({"select * form jd_item where id = #{id}"})
    public Item selectById(Integer id);

    @Insert("insert into jd_item(spu_id,sku_id,title,price,pic,url,created,updated) values (#{spuId},#{skuId},#{title},#{price},#{pic},#{url},#{created},#{updated})")
    public int InsertItem(Item item);

    @Select("select * from jd_item where sku_id = #{sku}")
    public List<Item> selectBySku(Long sku);
}
