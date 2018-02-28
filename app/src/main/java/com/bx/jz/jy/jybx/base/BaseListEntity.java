package com.bx.jz.jy.jybx.base;

import java.io.Serializable;
import java.util.List;

public class BaseListEntity<T> implements Serializable {

    private String code;

    private String mag;
    private List<T> lists;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return mag;
    }

    public void setMessage(String message) {
        this.mag = message;
    }

    public List<T> getList() {
        return lists;
    }

    public void setList(List<T> list) {
        this.lists = list;
    }


}
