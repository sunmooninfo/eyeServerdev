package com.eye.mail.notify;

public enum MailNotifyType {
    PAY_SUCCEED("paySucceed"),
    SHIP("ship"),
    REFUND("refund"),
    CAPTCHA("captcha");

    private String type;

    MailNotifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
