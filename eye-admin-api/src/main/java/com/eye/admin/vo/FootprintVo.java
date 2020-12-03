package com.eye.admin.vo;

import com.eye.db.domain.EyeFootprint;

public class FootprintVo extends EyeFootprint {
    private String username;
    private String goodsName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
