package com.bx.jz.jy.jybx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: Ingredients <br/>
 * Description:  <br/>
 */
public class Ingredients implements Serializable {

    private boolean isClick = false;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    private Double shelfLifeTime;
    private Long classificationId;
    private String knowledgeGraphId;
    private Integer refrigeratorId;
    private Integer ingredientsId;
    private String ingredientsUpdateTime;
    private Integer subordinatePosition;
    private Integer addWay;
    private String foodComponent;
    private Integer isDelete;
    private String imgUrl;
    private String componentUnit;
    private Long shelfLifeRemaining;
    private Long userId;
    private Integer freshness;
    private String ingredientsName;
    private String addTime;

    public Double getShelfLifeTime() {
        return shelfLifeTime;
    }

    public void setShelfLifeTime(Double shelfLifeTime) {
        this.shelfLifeTime = shelfLifeTime;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public String getKnowledgeGraphId() {
        return knowledgeGraphId;
    }

    public void setKnowledgeGraphId(String knowledgeGraphId) {
        this.knowledgeGraphId = knowledgeGraphId;
    }

    public Integer getRefrigeratorId() {
        return refrigeratorId;
    }

    public void setRefrigeratorId(Integer refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }

    public Integer getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(Integer ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public String getIngredientsUpdateTime() {
        return ingredientsUpdateTime;
    }

    public void setIngredientsUpdateTime(String ingredientsUpdateTime) {
        this.ingredientsUpdateTime = ingredientsUpdateTime;
    }

    public Integer getSubordinatePosition() {
        return subordinatePosition;
    }

    public void setSubordinatePosition(Integer subordinatePosition) {
        this.subordinatePosition = subordinatePosition;
    }

    public Integer getAddWay() {
        return addWay;
    }

    public void setAddWay(Integer addWay) {
        this.addWay = addWay;
    }

    public String getFoodComponent() {
        return foodComponent;
    }

    public void setFoodComponent(String foodComponent) {
        this.foodComponent = foodComponent;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getComponentUnit() {
        return componentUnit;
    }

    public void setComponentUnit(String componentUnit) {
        this.componentUnit = componentUnit;
    }

    public Long getShelfLifeRemaining() {
        return shelfLifeRemaining;
    }

    public void setShelfLifeRemaining(Long shelfLifeRemaining) {
        this.shelfLifeRemaining = shelfLifeRemaining;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFreshness() {
        return freshness;
    }

    public void setFreshness(Integer freshness) {
        this.freshness = freshness;
    }

    public String getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}