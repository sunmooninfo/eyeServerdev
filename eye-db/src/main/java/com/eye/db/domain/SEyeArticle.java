package com.eye.db.domain;

public class SEyeArticle extends EyeArticle {
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "SEyeArticle{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }

    public SEyeArticle(String categoryName) {
        this.categoryName = categoryName;
    }

    public SEyeArticle(){
    }
}
