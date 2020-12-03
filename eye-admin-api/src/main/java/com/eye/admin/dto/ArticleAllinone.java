package com.eye.admin.dto;

import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleAttribute;

public class ArticleAllinone {
    EyeArticle article;
    EyeArticleAttribute[] attributes;
    EyeAccessory accessory;

    public EyeArticle getArticle() {
        return article;
    }

    public void setArticle(EyeArticle article) {
        this.article = article;
    }

    public EyeArticleAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(EyeArticleAttribute[] attributes) {
        this.attributes = attributes;
    }

    public EyeAccessory getAccessory() {
        return accessory;
    }

    public void setAccessory(EyeAccessory accessory) {
        this.accessory = accessory;
    }
}
