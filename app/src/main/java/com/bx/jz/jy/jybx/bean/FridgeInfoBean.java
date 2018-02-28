package com.bx.jz.jy.jybx.bean;

import java.util.List;

/**
 * author: zhy
 * email: 760982661@qq.com
 * date: 2018/1/18 0018.
 * desc:
 */

public class FridgeInfoBean {

    private String msg;
    private int code;
    private RefrigeratorsBean refrigerators;
    private int type;
    private List<FreezeBean> freeze;
    private List<RefrigerateBean> refrigerate;
    private List<HeterothermBean> heterotherm;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RefrigeratorsBean getRefrigerators() {
        return refrigerators;
    }

    public void setRefrigerators(RefrigeratorsBean refrigerators) {
        this.refrigerators = refrigerators;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<FreezeBean> getFreeze() {
        return freeze;
    }

    public void setFreeze(List<FreezeBean> freeze) {
        this.freeze = freeze;
    }

    public List<RefrigerateBean> getRefrigerate() {
        return refrigerate;
    }

    public void setRefrigerate(List<RefrigerateBean> refrigerate) {
        this.refrigerate = refrigerate;
    }

    public List<HeterothermBean> getHeterotherm() {
        return heterotherm;
    }

    public void setHeterotherm(List<HeterothermBean> heterotherm) {
        this.heterotherm = heterotherm;
    }

    public static class RefrigeratorsBean {

        private int abnormity;
        private String addTime;
        private String data;
        private int freeze;
        private int heterotherm;
        private int id;
        private int isDelete;
        private String pattern;
        private int refrigerate;
        private int refrigeratorid;
        private String updateTime;
        private boolean LECO;

        public boolean isLECO() {
            return LECO;
        }

        public void setLECO(boolean LECO) {
            this.LECO = LECO;
        }

        public int getAbnormity() {
            return abnormity;
        }

        public void setAbnormity(int abnormity) {
            this.abnormity = abnormity;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getFreeze() {
            return freeze;
        }

        public void setFreeze(int freeze) {
            this.freeze = freeze;
        }

        public int getHeterotherm() {
            return heterotherm;
        }

        public void setHeterotherm(int heterotherm) {
            this.heterotherm = heterotherm;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public int getRefrigerate() {
            return refrigerate;
        }

        public void setRefrigerate(int refrigerate) {
            this.refrigerate = refrigerate;
        }

        public int getRefrigeratorid() {
            return refrigeratorid;
        }

        public void setRefrigeratorid(int refrigeratorid) {
            this.refrigeratorid = refrigeratorid;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class FreezeBean {

        private String imgUrl;
        private double shelfLifeRemaining;
        private String ingredientsName;

        public String getIngredientsName() {
            return ingredientsName;
        }

        public void setIngredientsName(String ingredientsName) {
            this.ingredientsName = ingredientsName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getShelfLifeRemaining() {
            return shelfLifeRemaining;
        }

        public void setShelfLifeRemaining(double shelfLifeRemaining) {
            this.shelfLifeRemaining = shelfLifeRemaining;
        }
    }

    public static class RefrigerateBean {

        private String imgUrl;
        private double shelfLifeRemaining;
        private String ingredientsName;

        public String getIngredientsName() {
            return ingredientsName;
        }

        public void setIngredientsName(String ingredientsName) {
            this.ingredientsName = ingredientsName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getShelfLifeRemaining() {
            return shelfLifeRemaining;
        }

        public void setShelfLifeRemaining(double shelfLifeRemaining) {
            this.shelfLifeRemaining = shelfLifeRemaining;
        }
    }

    public static class HeterothermBean {

        private String imgUrl;
        private double shelfLifeRemaining;
        private String ingredientsName;

        public String getIngredientsName() {
            return ingredientsName;
        }

        public void setIngredientsName(String ingredientsName) {
            this.ingredientsName = ingredientsName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getShelfLifeRemaining() {
            return shelfLifeRemaining;
        }

        public void setShelfLifeRemaining(double shelfLifeRemaining) {
            this.shelfLifeRemaining = shelfLifeRemaining;
        }
    }
}
