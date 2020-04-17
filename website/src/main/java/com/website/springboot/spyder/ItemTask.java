package com.website.springboot.spyder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.website.springboot.bean.Item;
import com.website.springboot.mapper.SpyderMapper;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * spyder 爬虫程序  未完成
 *
 * 源项目是哦那个JPA访问数据库，而此项目使用Mybatis来实现访问数据库
 */
@Component
public class ItemTask {
    @Autowired
    HttpClientUtil httpClientUtil;
    //可能需要一个Mapper来写入数据库
    @Autowired
    SpyderMapper spyderMapper;

    @Autowired
    ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 定时任务
     *
     * 本次规则比较简单，直接用Spring自带的定时任务，如有需要可以使用其他定时任务框架
     * fixedDelay，每隔多少毫秒执行一次任务，这里设定每隔100秒抓取一次
     */
    @Scheduled(fixedDelay = 1000*10)
    public void itemTask() throws IOException {
        logger.info("========执行任务:去京东抓取手机商品数据========");
        // 初始URL，注意最后的page参数，后面要设置具体的页码，实现分页抓取
        String url = "https://search.jd.com/search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&cid2=653&cid3=655&s=54&click=0&page=";

        // 作为Demo，我们只抓取手机分类下前五页数据（京东的分页是1 3 5，代表第1 2 3页）
        for (int i = 1; i < 10; i=i+2) {
            String html = httpClientUtil.doGetHtml(url + i);
            // 解析HTML获取商品数据并存储
            this.parse(html);
        }
    }

    /**
     * 1.解析HTML获取商品数据
     * 2.存储到数据库
     *
     * 这里涉及对HTML的文档解析，光看代码是看不懂的，需要自己打开京东的手机商品页面查看源码对照着看
     *
     * @param html
     */
    public void parse(String html) throws JsonProcessingException {

        // 解析HTML获取DOM对象
        Document doc = Jsoup.parse(html);
        // 获取当前页面所有spu,每个li标签
        Elements spuElements = doc.select("div#J_goodsList > ul > li");
        // 遍历页面所有spu
        for (Element spuElement : spuElements) {
            // 集中保存每个spu下的所有sku item
            List<Item> itemsToSave = new ArrayList<>();

            // 得到Item.spuId
            long spuId = Long.parseLong(spuElement.attr("data-spu"));
            // 获取当前spu下所有sku
            Elements skuElements = spuElement.select("li.gl-item");
            for (Element skuElement : skuElements) {
                /**
                 * 每一个sku的HTML结构
                 * <li class="ps-item">
                 *     <a href="javascript:;" title="幻影蓝" class="curr">
                 *          <img data-presale="" data-sku="100004590739" data-img="1" width="25" height="25" data-lazy-img="done" class="" data-done="1" src="//img14.360buyimg.com/n9/jfs/t1/97830/25/11145/235519/5e26c26dE569ab8fe/2ce89b36a7f4054d.jpg">
                 *     </a>
                 * </li>
                 */
                long skuId = Long.parseLong(skuElement.select("img[data-sku]").attr("data-sku"));
                // 根据sku查询数据
                Item item = new Item();
                item.setSkuId(skuId);
                List<Item> list = this.spyderMapper.selectBySku(item.getSkuId());
                if (CollectionUtils.isNotEmpty(list)) {
                    // 当前sku已经存在，跳过本次循环，不重复保存
                    continue;
                }

                // 获取sku详情页url
                String itemDetailUrl = "https://item.jd.com/" + skuId + ".html";

                // 获取sku图片（实际抓取和网页源码看到的不一样，这里根据debug看到的HTML文档结构做了调整）
                String imageUrl = "https:" + skuElement.select("img[data-sku]").first().attr("data-lazy-img");
                // 获取到的sku图片太小，n9是小图，n1是大图
                imageUrl = imageUrl.replace("/n9/", "/n1/");
                // 下载图片保存到本地，返回图片名称
                String pic = this.httpClientUtil.doGetImage(imageUrl);

                // 获取sku价格 spu下有多个sku，每个sku价格是不同的，京东会在鼠标悬停到sku时根据skuId发起请求得到sku价格
                String priceJson = this.httpClientUtil.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + skuId);
                double price = objectMapper.readTree(priceJson).get(0).get("p").asDouble();

                // 获取sku标题
                String itemDetailHtml = this.httpClientUtil.doGetHtml(itemDetailUrl);
                String title = Jsoup.parse(itemDetailHtml).select("div.sku-name").text();
                //自己获取时间
                Calendar date = Calendar.getInstance();
                item.setSpuId(spuId);
                item.setUrl(itemDetailUrl);
                item.setPic(pic);
                item.setPrice(price);
                item.setTitle(title);
                item.setUpdated(date.getTime());
                item.setCreated(date.getTime());
                // 把item收集起来
                itemsToSave.add(item);
                System.out.println(item);
            }
            // 批量保存spu（下的sku）到数据库
            for(Item item :itemsToSave){
                //spyderMapper.InsertItem(item);
            }
        }
    }
}
