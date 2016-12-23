package com.befiring.haduonews.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
//        (indexes = {@Index(value = "id DESC",unique = true)})
public class News extends Response {

    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String title;
    private String date;
    private String thumbnail_pic_s;
    private String author_name;
    private String thumbnail_pic_so2;
    private String thumbnail_pic_so3;
    private String url;
    private String type;
    private String realtype;

    public News() {
    }

    @Generated(hash = 1763077757)
    public News(Long id, String title, String date, String thumbnail_pic_s,
            String author_name, String thumbnail_pic_so2, String thumbnail_pic_so3,
            String url, String type, String realtype) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.author_name = author_name;
        this.thumbnail_pic_so2 = thumbnail_pic_so2;
        this.thumbnail_pic_so3 = thumbnail_pic_so3;
        this.url = url;
        this.type = type;
        this.realtype = realtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getThumbnail_pic_so2() {
        return thumbnail_pic_so2;
    }

    public void setThumbnail_pic_so2(String thumbnail_pic_so2) {
        this.thumbnail_pic_so2 = thumbnail_pic_so2;
    }

    public String getThumbnail_pic_so3() {
        return thumbnail_pic_so3;
    }

    public void setThumbnail_pic_so3(String thumbnail_pic_so3) {
        this.thumbnail_pic_so3 = thumbnail_pic_so3;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRealtype() {
        return realtype;
    }

    public void setRealtype(String realtype) {
        this.realtype = realtype;
    }
}
