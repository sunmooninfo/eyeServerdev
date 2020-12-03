package com.eye.core.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统设置
 */
public class SystemConfig {
    // 小程序相关配置
    public final static String EYE_WX_INDEX_NEW = "eye_wx_index_new";
    public final static String EYE_WX_INDEX_ISSHOWN = "eye_wx_index_isshown";
    public final static String EYE_WX_INDEX_HOT = "eye_wx_index_hot";
    public final static String EYE_WX_INDEX_KILL = "eye_wx_index_kill";
    public final static String EYE_WX_INDEX_BRAND = "eye_wx_index_brand";
    public final static String EYE_WX_INDEX_TOPIC = "eye_wx_index_topic";
    public final static String EYE_WX_INDEX_CATLOG_LIST = "eye_wx_catlog_list";
    public final static String EYE_WX_INDEX_CATLOG_GOODS = "eye_wx_catlog_goods";
    public final static String EYE_WX_SHARE = "eye_wx_share";
    // 运费相关配置
    public final static String EYE_EXPRESS_FREIGHT_VALUE = "eye_express_freight_value";
    public final static String EYE_EXPRESS_FREIGHT_MIN = "eye_express_freight_min";
    // 订单相关配置
    public final static String EYE_ORDER_UNPAID = "eye_order_unpaid";
    public final static String EYE_ORDER_UNCONFIRM = "eye_order_unconfirm";
    public final static String EYE_ORDER_COMMENT = "eye_order_comment";
    // 商场相关配置
    public final static String EYE_MALL_NAME = "eye_mall_name";
    public final static String EYE_MALL_ADDRESS = "eye_mall_address";
    public final static String EYE_MALL_PHONE = "eye_mall_phone";
    public final static String EYE_MALL_QQ = "eye_mall_qq";
    public final static String EYE_MALL_LONGITUDE = "eye_mall_longitude";
    public final static String EYE_MALL_Latitude = "eye_mall_latitude";
    public final static String EYE_MALL_EMAIL = "eye_mall_email";
    public final static String EYE_MALL_WX = "eye_mall_wx";
    public final static String EYE_MALL_WX_CODE = "eye_mall_wx_code";
    public final static String EYE_MALL_BIG_LOGO = "eye_mall_big_logo";
    public final static String EYE_MALL_SMALL_LOGO = "eye_mall_small_logo";
    public final static String EYE_MALL_COPYRIGHT = "eye_mall_copyright";
    public final static String EYE_MALL_RECORDS = "eye_mall_records";
    public final static String EYE_MALL_CERTIFICATE = "eye_mall_certificate";

    //所有的配置均保存在该 HashMap 中
    private static Map<String, String> SYSTEM_CONFIGS = new HashMap<>();

    private static String getConfig(String keyName) {
        return SYSTEM_CONFIGS.get(keyName);
    }

    private static Integer getConfigInt(String keyName) {
        return Integer.parseInt(SYSTEM_CONFIGS.get(keyName));
    }

    private static Boolean getConfigBoolean(String keyName) {
        return Boolean.valueOf(SYSTEM_CONFIGS.get(keyName));
    }

    private static BigDecimal getConfigBigDec(String keyName) {
        return new BigDecimal(SYSTEM_CONFIGS.get(keyName));
    }

    public static Integer getNewLimit() {
        return getConfigInt(EYE_WX_INDEX_NEW);
    }

    public static Integer getIsShownLimit() {
        return getConfigInt(EYE_WX_INDEX_ISSHOWN);
    }

    public static Integer getHotLimit() {
        return getConfigInt(EYE_WX_INDEX_HOT);
    }
    public static Integer getKillLimit() {
        return getConfigInt(EYE_WX_INDEX_KILL);
    }
    public static Integer getBrandLimit() {
        return getConfigInt(EYE_WX_INDEX_BRAND);
    }

    public static Integer getTopicLimit() {
        return getConfigInt(EYE_WX_INDEX_TOPIC);
    }

    public static Integer getCatlogListLimit() {
        return getConfigInt(EYE_WX_INDEX_CATLOG_LIST);
    }

    public static Integer getCatlogMoreLimit() {
        return getConfigInt(EYE_WX_INDEX_CATLOG_GOODS);
    }

    public static boolean isAutoCreateShareImage() {
        return getConfigBoolean(EYE_WX_SHARE);
    }

    public static BigDecimal getFreight() {
        return getConfigBigDec(EYE_EXPRESS_FREIGHT_VALUE);
    }

    public static BigDecimal getFreightLimit() {
        return getConfigBigDec(EYE_EXPRESS_FREIGHT_MIN);
    }

    public static Integer getOrderUnpaid() {
        return getConfigInt(EYE_ORDER_UNPAID);
    }

    public static Integer getOrderUnconfirm() {
        return getConfigInt(EYE_ORDER_UNCONFIRM);
    }

    public static Integer getOrderComment() {
        return getConfigInt(EYE_ORDER_COMMENT);
    }

    public static String getMallName() {
        return getConfig(EYE_MALL_NAME);
    }

    public static String getMallAddress() {
        return getConfig(EYE_MALL_ADDRESS);
    }

    public static String getMallPhone() {
        return getConfig(EYE_MALL_PHONE);
    }

    public static String getMallQQ() {
        return getConfig(EYE_MALL_QQ);
    }

    public static String getMallLongitude() {
        return getConfig(EYE_MALL_LONGITUDE);
    }

    public static String getMallLatitude() {
        return getConfig(EYE_MALL_Latitude);
    }

    public static String getMallEmail() {
        return getConfig(EYE_MALL_EMAIL);
    }

    public static String getMallWx() {
        return getConfig(EYE_MALL_WX);
    }

    public static String getMallWxCode() {
        return getConfig(EYE_MALL_WX_CODE);
    }

    public static String getMallBigLogo() {
        return getConfig(EYE_MALL_BIG_LOGO);
    }

    public static String getMallSmallLogo() {
        return getConfig(EYE_MALL_SMALL_LOGO);
    }

    public static String getMallCopyright() {
        return getConfig(EYE_MALL_COPYRIGHT);
    }

    public static String getMallRecords() {
        return getConfig(EYE_MALL_RECORDS);
    }

    public static String getMallCertificate() {
        return getConfig(EYE_MALL_CERTIFICATE);
    }


    public static void setConfigs(Map<String, String> configs) {
        SYSTEM_CONFIGS = configs;
    }

    public static void updateConfigs(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SYSTEM_CONFIGS.put(entry.getKey(), entry.getValue());
        }
    }
}