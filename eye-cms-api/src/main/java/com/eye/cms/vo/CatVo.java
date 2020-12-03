package com.eye.cms.vo;

import com.eye.db.domain.EyeArticle;

import java.util.List;

public class CatVo {
    private Integer value;
    private String label;
    private String level;
    private String inLink;
    private String outLink;
    private Integer linkValue;
    private List children;
    private List<EyeArticle> articleList;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public List<EyeArticle> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<EyeArticle> articleList) {
        this.articleList = articleList;
    }

    public String getInLink() {
        return inLink;
    }

    public void setInLink(String inLink) {
        this.inLink = inLink;
    }

    public String getOutLink() {
        return outLink;
    }

    public void setOutLink(String outLink) {
        this.outLink = outLink;
    }

    public Integer getLinkValue() {
        return linkValue;
    }

    public void setLinkValue(Integer linkValue) {
        this.linkValue = linkValue;
    }
}
