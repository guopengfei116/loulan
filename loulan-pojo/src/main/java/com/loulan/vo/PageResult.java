package com.loulan.vo;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    private long total;
    private List<?> list;

    public PageResult() {
    }

    public PageResult(long total, List<?> list) {
        this.total = total;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
