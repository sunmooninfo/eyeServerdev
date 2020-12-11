package com.eye.core.config;

import com.eye.db.domain.NewEyeCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;


@Configuration
public class WxConfigVo {

    private String vipNotifyUrl;

    public WxConfigVo() {
    }

    public WxConfigVo(String vipNotifyUrl) {
        this.vipNotifyUrl = vipNotifyUrl;
    }

    public String getVipNotifyUrl() {
        return vipNotifyUrl;
    }

    public void setVipNotifyUrl(String vipNotifyUrl) {
        this.vipNotifyUrl = vipNotifyUrl;
    }

    @Override
    public String toString() {
        return "WxConfigVo{" +
                "vipNotifyUrl='" + vipNotifyUrl + '\'' +
                '}';
    }
}
