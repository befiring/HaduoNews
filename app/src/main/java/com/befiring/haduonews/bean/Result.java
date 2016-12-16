package com.befiring.haduonews.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class Result {
    private String stat;
    private List<News> data;

    public Result(String stat, List<News> data) {
        this.stat = stat;
        this.data = data;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }
}
