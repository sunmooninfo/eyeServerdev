package com.eye.admin.vo;




import java.util.List;

public class TopicCategoryVo{

    private Integer id;
    private String topicName;
    private String picUrl;
    private Byte sortOrder;
    private List<TopicCategoryVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<TopicCategoryVo> getChildren() {
        return children;
    }

    public void setChildren(List<TopicCategoryVo> children) {
        this.children = children;
    }
}
