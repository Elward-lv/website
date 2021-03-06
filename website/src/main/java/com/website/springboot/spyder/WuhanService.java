package com.website.springboot.spyder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.website.springboot.bean.ChinaData;
import com.website.springboot.bean.HttpPojo;
import com.website.springboot.bean.NewsFromDxy;
import com.website.springboot.mapper.spyderDataMapper.ChinaDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WuhanService {

    private static String url = "https://ncov.dxy.cn/ncovh5/view/pneumonia";

    public static void main(String[] args) {
        getAllTimelineService();
    }
    /**
     * 获取丁香医生的总共确诊病例、疑似病例、治愈人数、死亡人数等数据
     * @return
     */
    public static String getStatisticsService(){
        //模拟请求
        HttpPojo httpPojo = setNewPojo();
        Map paramObj = new HashMap();
        String htmlResult = httpSendGet(url, paramObj, httpPojo); //整个html页面
        //System.out.println(htmlResult);

        //正则获取数据
        //因为html的数据格式看着就像json格式，所以我们正则获取json：{"id":1,"createTime":1579537899000,"modifyTime":1580571956000,"infectSource":"野生动物，可能为中华菊头蝠","passWay":"经呼吸道飞沫传播，亦可通过接触传播","imgUrl":"https://img1.dxycdn.com/2020/0201/450/3394153392393266839-135.png","dailyPic":"https://img1.dxycdn.com/2020/0201/693/3394145745204021706-135.png","summary":"","deleted":false,"countRemark":"","confirmedCount":11901,"suspectedCount":17988,"curedCount":275,"deadCount":259,"virus":"新型冠状病毒 2019-nCoV","remark1":"易感人群: 人群普遍易感。老年人及有基础疾病者感染后病情较重，儿童及婴幼儿也有发病","remark2":"潜伏期: 一般为 3~7 天，最长不超过 14 天，潜伏期内存在传染性","remark3":"","remark4":"","remark5":"","generalRemark":"疑似病例数来自国家卫健委数据，目前为全国数据，未分省市自治区等","abroadRemark":""}
        String reg= "window.getStatisticsService = (.*?)\\}(?=catch)";
        Pattern totalPattern = Pattern.compile(reg);
        Matcher totalMatcher = totalPattern.matcher(htmlResult);

        String result="";
        if (totalMatcher.find()){
            result = totalMatcher.group(1);
            System.out.println(result);
            //JSONObject jsonObject = JSONObject.parseObject(result);
            //String modifyTime = jsonObject.getString("modifyTime");
            //System.out.println("modifyTime："+modifyTime);
        }

        return result;
    }


    /**
     * 获取全国各个省市的确诊、死亡和治愈人数
     * @return
     */
    public static ArrayList<ChinaData> getAreaStat(){
        //模拟请求
        ArrayList<ChinaData> arrayList = new ArrayList();

        HttpPojo httpPojo = setNewPojo();
        Map paramObj = new HashMap();
        String htmlResult = httpSendGet(url, paramObj, httpPojo); //整个html页面
        //System.out.println(htmlResult);

        //正则获取数据
        //因为html的数据格式看着就像json格式，所以我们正则获取json
        String reg= "window.getAreaStat = (.*?)\\}(?=catch)";
        Pattern totalPattern = Pattern.compile(reg);
        Matcher totalMatcher = totalPattern.matcher(htmlResult);

        String result="";
        if (totalMatcher.find()){
            result = totalMatcher.group(1);
            System.out.println(result);
            //各个省市的是一个列表List，如果想保存到数据库中，要遍历结果，下面是demo
            JSONArray array = JSON.parseArray(result);
            for(int i=0;i<array.size();i++){
                JSONObject jsonObject = JSON.parseObject(array.getString(i));
                String provinceName = jsonObject.getString("provinceName");
                int currentConfirmedCount = jsonObject.getIntValue("currentConfirmedCount");
                int confirmedCount = jsonObject.getIntValue("confirmedCount");//bigint
                int suspectedCount = jsonObject.getIntValue("suspectedCount");
                int curedCount = jsonObject.getIntValue("curedCount");//bigint
                int deadCount = jsonObject.getIntValue("deadCount");
                String statisticsData = jsonObject.getString("statisticsData");

                ChinaData chinaData = new ChinaData();
                chinaData.setProvinceName(provinceName);
                chinaData.setCurrentConfirmedCount(currentConfirmedCount);
                chinaData.setConfirmedCount(confirmedCount);
                chinaData.setSuspectedCount(suspectedCount);
                chinaData.setCuredCount(curedCount);
                chinaData.setDeadCount(deadCount);
                chinaData.setStatisticsData(statisticsData);

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                chinaData.setUpdateTime(date);
                System.out.println(provinceName+" "+currentConfirmedCount+" "+confirmedCount+" "+suspectedCount+" "+curedCount+" "+deadCount+" "+statisticsData+" "+date);
                arrayList.add(chinaData);
            }
        }

        return arrayList;
    }

    /**
     * 获取全球各个国家的确诊、死亡和治愈人数
     * @return
     */
    public static String getListByCountryTypeService2(){
        //模拟请求
        HttpPojo httpPojo = setNewPojo();
        Map paramObj = new HashMap();
        String htmlResult = httpSendGet(url, paramObj, httpPojo); //整个html页面
        //System.out.println(htmlResult);

        //正则获取数据
        //因为html的数据格式看着就像json格式，所以我们正则获取json
        String reg= "window.getListByCountryTypeService2true = (.*?)\\}(?=catch)";
        Pattern totalPattern = Pattern.compile(reg);
        Matcher totalMatcher = totalPattern.matcher(htmlResult);

        String result="";
        if (totalMatcher.find()){
            result = totalMatcher.group(1);
            System.out.println(result);
            //各个国家的是一个列表List，如果想保存到数据库中，要遍历结果，下面是demo
            /*JSONArray array = JSONArray.parseArray(result);
            JSONObject jsonObject = JSONObject.parseObject(array.getString(0));
            String provinceName = jsonObject.getString("continents");
            System.out.println("continents："+provinceName);*/
        }

        return result;
    }




    /**
     * 获取页面的实时播报
     * @return
     */
    public static String getTimelineService(){
        //模拟请求
        HttpPojo httpPojo = setNewPojo();
        Map paramObj = new HashMap();
        String htmlResult = httpSendGet(url, paramObj, httpPojo); //整个html页面
        //System.out.println(htmlResult);

        //正则获取数据
        //因为html的数据格式看着就像json格式，所以我们正则获取json
        String reg= "window.getTimelineService2 = (.*?)\\}(?=catch)";
        Pattern totalPattern = Pattern.compile(reg);
        Matcher totalMatcher = totalPattern.matcher(htmlResult);

        String result="";
        if (totalMatcher.find()){
            result = totalMatcher.group(1);
            System.out.println(result);
            //是一个列表List，如果想保存到数据库中，要遍历结果，下面是demo
            JSONArray array = JSONArray.parseArray(result);
            for (int i = 0; i < array.size(); i++) {
                JSONObject jsonObject = JSONObject.parseObject(array.getString(i));
                String title = jsonObject.getString("title");
                System.out.println("title："+title+" pubDateStr:"+jsonObject.getString("pubDateStr")+" summary:"+jsonObject.getString("summary")+" infoSource:"+jsonObject.getString("infoSource"));
            }/**/

        }

        return result;
    }


    /**
     * 获取页面实时播报的所有历史数据
     * @return
     */
    public static ArrayList<NewsFromDxy> getAllTimelineService(){
        String url="https://file1.dxycdn.com/2020/0130/492/3393874921745912795-115.json?"+Math.round(Math.random()*100000000);
        //模拟请求
        ArrayList<NewsFromDxy> datas  =  new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        HttpPojo httpPojo = setNewPojo();

        Map paramObj = new HashMap();
        String htmlResult = httpSendGet(url, paramObj, httpPojo); //整个html页面
        //System.out.println(htmlResult);

        //是一个列表List，如果想保存到数据库中，要遍历结果，下面是demo
        JSONObject resultJo = JSONObject.parseObject(htmlResult);
        String dataStr = resultJo.getString("data");
        JSONArray array = JSONArray.parseArray(dataStr);
        for (int i = 0; i < array.size(); i++) {
            NewsFromDxy newsFromDxy = new NewsFromDxy();
            JSONObject jsonObject = JSONObject.parseObject(array.getString(i));
            String title = jsonObject.getString("title");
            Date pubDate = jsonObject.getDate("pubDate");
            String infoSoure = jsonObject.getString("infoSource");
            String summary = jsonObject.getString("summary");
            summary = summary.replaceAll("\\s*","");
            String sourceUrl = jsonObject.getString("sourceUrl");
            Date updateDate = calendar.getTime();

            newsFromDxy.setTitle(title);
            newsFromDxy.setPubDate(pubDate);
            newsFromDxy.setInfoSoure(infoSoure);
            newsFromDxy.setSummary(summary);
            newsFromDxy.setSourceUrl(sourceUrl);
            newsFromDxy.setUpdateTime(updateDate);

            datas.add(newsFromDxy);

            //System.out.println("message："+jsonObject.toJSONString());
        }/**/
        return datas;
    }



    /**
     * http请求
     * @param url
     * @param paramObj
     * @param httpPojo
     * @return
     */
    private static String httpSendGet(String url, Map paramObj, HttpPojo httpPojo){
        String result = "";
        String urlName = url + "?" + parseParam(paramObj);

        BufferedReader in=null;
        try {

            URL realURL = new URL(urlName);
            URLConnection conn = realURL.openConnection();
            //伪造ip访问
            String ip = randIP();
            System.out.println("目前伪造的ip："+ip);
            conn.setRequestProperty("X-Forwarded-For", ip);
            conn.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);
            conn.setRequestProperty("HTTP_CLIENT_IP", ip);
            conn.setRequestProperty("REMOTE_ADDR", ip);
            conn.setRequestProperty("Host", httpPojo.getHttpHost());
            conn.setRequestProperty("accept", httpPojo.getHttpAccept());
            conn.setRequestProperty("connection", httpPojo.getHttpConnection());
            conn.setRequestProperty("user-agent", httpPojo.getHttpUserAgent());
            conn.setRequestProperty("Referer",httpPojo.getHttpReferer()); //伪造访问来源
            conn.setRequestProperty("Origin", httpPojo.getHttpOrigin()); //伪造访问域名
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String s : map.keySet()) {
                //System.out.println(s + "-->" + map.get(s));
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
        return result;
    }

    /**
     * 返回新的HttpPojo
     * @return
     */
    public static HttpPojo setNewPojo(){
        HttpPojo httpPojo = new HttpPojo();
        httpPojo.setHttpHost("ncov.dxy.cn");
        httpPojo.setHttpAccept("*/*");
        httpPojo.setHttpConnection("keep-alive");
        httpPojo.setHttpUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        httpPojo.setHttpReferer("https://ncov.dxy.cn/ncovh5/view/pneumonia_timeline?from=dxy&link=&share=&source=");
        httpPojo.setHttpOrigin("https://ncov.dxy.cn");
        return  httpPojo;
    }


    /**
     * 解析map
     * @param paramObj
     * @return
     */
    public static String parseParam(Map paramObj){
        String param="";
        if (paramObj!=null&&!paramObj.isEmpty()){
            for (Object key:paramObj.keySet()){
                String value = paramObj.get(key).toString();
                param+=(key+"="+value+"&");

            }
        }
        return param;
    }

    /**
     * 伪造ip地址
     * @return
     */
    public static String randIP() {
        Random random = new Random(System.currentTimeMillis());
        return (random.nextInt(255) + 1) + "." + (random.nextInt(255) + 1)
                + "." + (random.nextInt(255) + 1) + "."
                + (random.nextInt(255) + 1);
    }
}
