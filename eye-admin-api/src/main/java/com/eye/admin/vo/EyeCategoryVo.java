package com.eye.admin.vo;

import java.util.List;
/**
 * 文章分类
 * @author HUAWEI
 *
 */
public class EyeCategoryVo {
    private Integer id;
    private String name;
    private String keywords;
    private String desc;
    private String iconUrl;
    private String picUrl;
    private String level;
    private String inLink;
    private String outLink;
    private Integer linkValue;
    private Integer pid;
    private Byte sortOrder;
    private Boolean deleted;
    private List<EyeCategoryVo> children;

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<EyeCategoryVo> getChildren() {
        return children;
    }

    public void setChildren(List<EyeCategoryVo> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
