package com.eye.common.dto;

import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeOrderGoods;

public class EyeOrderGoodsVo extends EyeOrderGoods {

    private EyeAccessory uloveAccessory;

    public EyeAccessory getEyeAccessory() {
        return uloveAccessory;
    }

    public void setEyeAccessory(EyeAccessory uloveAccessory) {
        this.uloveAccessory = uloveAccessory;
    }
}
