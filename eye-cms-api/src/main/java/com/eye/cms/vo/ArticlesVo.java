package com.eye.cms.vo;

public class ArticlesVo {
    private Integer id;
    private String title;
    private String label;
    private Integer categoryId;//二级类目id
    private String categoryName;//二级类目名
    private String picUrl;
    private String keywords;
    private String link;
    private Integer categoryL1Id;//一级类目id
    private String categoryL1Name;//一级类目名

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCategoryL1Id() {
        return categoryL1Id;
    }

    public void setCategoryL1Id(Integer categoryL1Id) {
        this.categoryL1Id = categoryL1Id;
    }

    public String getCategoryL1Name() {
        return categoryL1Name;
    }

    public void setCategoryL1Name(String categoryL1Name) {
        this.categoryL1Name = categoryL1Name;
    }
}
