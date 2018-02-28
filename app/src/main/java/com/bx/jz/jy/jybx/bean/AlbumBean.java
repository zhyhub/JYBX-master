package com.bx.jz.jy.jybx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class AlbumBean implements Serializable{

    private String msg;
    private String code;
    private boolean hasNext;
    private List<ListsBean> lists;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<ListsBean> getLists() {
        return lists;
    }

    public void setLists(List<ListsBean> lists) {
        this.lists = lists;
    }

    public static class ListsBean {

        private String groupTime;
        private List<String> url;

        public String getGroupTime() {
            return groupTime;
        }

        public void setGroupTime(String groupTime) {
            this.groupTime = groupTime;
        }

        public List<String> getUrl() {
            return url;
        }

        public void setUrl(List<String> url) {
            this.url = url;
        }
    }
}
