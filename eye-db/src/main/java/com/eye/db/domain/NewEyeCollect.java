package com.eye.db.domain;


public class NewEyeCollect extends EyeCollect {
    private String goodsName;
    private String userName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public NewEyeCollect(String goodsName, String userName) {
        this.goodsName = goodsName;
        this.userName = userName;
    }

    public NewEyeCollect() {
    }

    @Override
    public String toString() {
        return "NewLitemallCollect{" +
                "goodsName='" + goodsName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
