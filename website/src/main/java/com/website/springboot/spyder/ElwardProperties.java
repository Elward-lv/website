package com.website.springboot.spyder;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.lvyanwei.elward")
public class ElwardProperties {
    private String imageStoragePath;
    private String siteCharset;

    public String getImageStoragePath() {
        return imageStoragePath;
    }

    public void setImageStoragePath(String imageStoragePath) {
        this.imageStoragePath = imageStoragePath;
    }

    public String getSiteCharset() {
        return siteCharset;
    }

    public void setSiteCharset(String siteCharset) {
        this.siteCharset = siteCharset;
    }
}
