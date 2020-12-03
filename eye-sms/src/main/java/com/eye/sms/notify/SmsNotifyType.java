package com.eye.sms.notify;

public enum SmsNotifyType {
    PAY_SUCCEED("paySucceed"),
    SHIP("ship"),
    REFUND("refund"),
    CAPTCHA("captcha");

    private String type;

    SmsNotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
