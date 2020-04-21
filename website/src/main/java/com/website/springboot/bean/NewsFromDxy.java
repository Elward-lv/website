package com.website.springboot.bean;

import java.util.Date;

public class NewsFromDxy {
    private int id;
    private String title;
    private Date pubDate;
    private String infoSoure;
    private String summary;
    private String sourceUrl;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getInfoSoure() {
        return infoSoure;
    }

    public void setInfoSoure(String infoSoure) {
        this.infoSoure = infoSoure;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "NewsFromDxy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + updateTime +
                ", infoSoure='" + infoSoure + '\'' +
                ", summary='" + summary + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
