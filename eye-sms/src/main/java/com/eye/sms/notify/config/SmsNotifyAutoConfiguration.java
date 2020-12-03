package com.eye.sms.notify.config;

import com.eye.sms.notify.SmsNotifyService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.eye.sms.notify.AliyunSmsSender;
import com.eye.sms.notify.TencentSmsSender;
import com.github.qcloudsms.SmsSingleSender;

@Configuration
@EnableConfigurationProperties(SmsNotifyProperties.class)
public class SmsNotifyAutoConfiguration {

    private final SmsNotifyProperties properties;

    public SmsNotifyAutoConfiguration(SmsNotifyProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SmsNotifyService smsNotifyService() {
        SmsNotifyService smsNotifyService = new SmsNotifyService();

        SmsNotifyProperties.Sms smsConfig = properties.getSms();
        if (smsConfig.isEnable()) {
            if(smsConfig.getActive().equals("tencent")) {
                smsNotifyService.setSmsSender(tencentSmsSender());
            }
            else if(smsConfig.getActive().equals("aliyun")) {
                smsNotifyService.setSmsSender(aliyunSmsSender());
            }

            smsNotifyService.setSmsTemplate(smsConfig.getTemplate());
        }

        return smsNotifyService;
    }

    public TencentSmsSender tencentSmsSender() {
        SmsNotifyProperties.Sms smsConfig = properties.getSms();
        TencentSmsSender smsSender = new TencentSmsSender();
        SmsNotifyProperties.Sms.Tencent tencent = smsConfig.getTencent();
        smsSender.setSender(new SmsSingleSender(tencent.getAppid(), tencent.getAppkey()));
        smsSender.setSign(smsConfig.getSign());
        return smsSender;
    }

    public AliyunSmsSender aliyunSmsSender() {
        SmsNotifyProperties.Sms smsConfig = properties.getSms();
        AliyunSmsSender smsSender = new AliyunSmsSender();
        SmsNotifyProperties.Sms.Aliyun aliyun = smsConfig.getAliyun();
        smsSender.setSign(smsConfig.getSign());
        smsSender.setRegionId(aliyun.getRegionId());
        smsSender.setAccessKeyId(aliyun.getAccessKeyId());
        smsSender.setAccessKeySecret(aliyun.getAccessKeySecret());
        return smsSender;
    }
}
