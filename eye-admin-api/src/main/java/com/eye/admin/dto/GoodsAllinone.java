package com.eye.admin.dto;

import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoodsAttribute;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.domain.EyeGoodsProduct;
import com.eye.db.domain.EyeGoodsSpecification;

public class GoodsAllinone {
    EyeGoods goods;
    EyeGoodsSpecification[] specifications;
    EyeGoodsAttribute[] attributes;
    EyeGoodsProduct[] products;
    EyeGoodsKill goodsKill;
    EyeAccessory accessory;

    public EyeAccessory getAccessory() {
        return accessory;
    }

    public void setAccessory(EyeAccessory accessory) {
        this.accessory = accessory;
    }

    public EyeGoodsKill getGoodsKill() {
		return goodsKill;
	}

	public void setGoodsKill(EyeGoodsKill goodsKill) {
		this.goodsKill = goodsKill;
	}

	public EyeGoods getGoods() {
        return goods;
    }

    public void setGoods(EyeGoods goods) {
        this.goods = goods;
    }

    public EyeGoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(EyeGoodsProduct[] products) {
        this.products = products;
    }

    public EyeGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(EyeGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public EyeGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(EyeGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

}
