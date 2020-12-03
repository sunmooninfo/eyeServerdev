package com.eye.brand.dto;

import com.eye.db.domain.EyeBrand;
import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.domain.EyeBrandWebMer;

public class BrandAllinone {
    EyeBrandWeb brand;
    EyeBrandWebMer merchants;

    public EyeBrandWeb getBrand() {
        return brand;
    }

    public void setBrand(EyeBrandWeb brand) {
        this.brand = brand;
    }

    public EyeBrandWebMer getMerchants() {
        return merchants;
    }

    public void setMerchants(EyeBrandWebMer merchants) {
        this.merchants = merchants;
    }
}
