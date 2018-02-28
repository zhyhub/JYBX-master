package com.bx.jz.jy.jybx.bean;

import java.util.List;

/**
 * 最新4张冰箱图片
 */
public class FourPhotoBean {

    private String msg;
    private String code;
    private List<NewmgBean> newmg;

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

    public List<NewmgBean> getNewmg() {
        return newmg;
    }

    public void setNewmg(List<NewmgBean> newmg) {
        this.newmg = newmg;
    }

    public static class NewmgBean {

        private String addTime;
        private String brief;
        private String fileId;
        private int id;
        private int menuId;
        private int pid;
        private String url;
        private int place;

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
            this.place = place;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMenuId() {
            return menuId;
        }

        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
