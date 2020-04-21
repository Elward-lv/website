package com.website.springboot.spyder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@EnableConfigurationProperties(ElwardProperties.class)
public class HttpClientUtil {

    @Autowired
    private ElwardProperties elwardProperties;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static PoolingHttpClientConnectionManager clientConnectionManager;

    //test properties
    public void getProperties(){
        String path = elwardProperties.getImageStoragePath();
        String site = elwardProperties.getSiteCharset();
        System.out.println("path:"+path+" site:"+site);
        logger.info("load successfully form yml");
    }

    /**
     * 根据请求地址获取对应的HTML页面
     *
     * @return
     */
    public String doGetHtml(String url)  {
        // 1.创建HttpClient,获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();
        //2.创建get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36");
        // Request配置（比如请求超时如何处理。连接数有限，不可能无节制等待）
        httpGet.setConfig(getRequestConfig());
        logger.info("请求地址:{}", httpGet);

        CloseableHttpResponse response = null;
        try{
            // 3.发送请求得到响应
            response = httpClient.execute(httpGet);
            // 4.解析
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // 响应体不为空
                if (entity != null) {
                    String content = EntityUtils.toString(entity, elwardProperties.getSiteCharset());
                    //logger.info("响应的数据:{}", content);
                    return content;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally{
            if(response!= null){
                try {
                    response.close();
                    // httpClient不用关闭，连接池会管理
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 根据图片URL下载图片
     *
     * @param url
     * @return
     */
    public String doGetImage(String url){
        // 1.创建HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();
        // 2.添加url，构建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36");
        // Request配置（比如请求超时如何处理。连接数有限，不可能无节制等待）
        httpGet.setConfig(getRequestConfig());
        logger.info("请求地址:{}", httpGet);

        CloseableHttpResponse response = null;
        try {
            // 3.发送请求得到响应
            response = httpClient.execute(httpGet);
            // 4.解析
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // 响应体不为空
                if (entity != null) {
                    // 获取原图片后缀
                    String extentionName = StringUtils.substringAfterLast(url, ".");
                    // 重命名图片
                    String picName = UUID.randomUUID().toString() + "." + extentionName;
                    // 把图片写入本地存储
                    File file = new File(elwardProperties.getImageStoragePath());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(file, picName));
                    response.getEntity().writeTo(fileOutputStream);
                    // 返回图片名称
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // httpClient不用关闭，连接池会管理
            }

        }
        return "";
    }


    /**
     * Request配置
     *
     * @return
     */
    private RequestConfig getRequestConfig() {
        RequestConfig conf= RequestConfig
                .custom()
                .setConnectTimeout(1000) // 创建连接的最长时间，毫秒
                .setConnectionRequestTimeout(500) // 获取连接的最长时间，毫秒
                .setSocketTimeout(1000 * 10) // 数据传输的最长时间，毫秒
                .build();
        return conf;
    }
}
