package com.eye.db.util;

import java.math.BigDecimal;

public class MemberConstant {

    //会员商品状态码 0正常上线,1到期自动下线,2管理员手动下线
    public static final Short GOODS_STATUS_ON = 0;
    public static final Short GOODS_STATUS_DOWN_EXPIRE = 1;
    public static final Short GOODS_STATUS_DOWN_ADMIN = 2;

    //会员信息 0普通用户,1 VIP用户,2 高级VIP用户
    public static final Byte USER_LEVEL_ON = 0;
    public static final Byte USER_LEVEL_VIP = 1;
    public static final Byte USER_LEVEL_HIGH_VIP = 2;

    //用户购买套餐状态码 0 现在是会员,1已经不是会员
    public static final Short USER_STATUS_ON = 0;
    public static final Short USER_STATUS_DOWN = 1;

    //积分商品状态码 0正常上线,1到期自动下线,2管理员手动下线
    public static final Short INTEGRAL_GOODS_STATUS_ON = 0;
    public static final Short INTEGRAL_GOODS_STATUS_DOWN_EXPIRE = 1;
    public static final Short INTEGRAL_GOODS_STATUS_DOWN_ADMIN = 2;

    //积分状态码 0正常使用 1用户会员到期不可使用
    public static final Short INTEGRAL_STATUS_ON = 0;
    public static final Short INTEGRAL_STATUS_DOWN_EXPIRE = 1;

    public static final Short ORDER_STATUS_CREATE = 0;
    public static final Short ORDER_STATUS_PAY = 1;

    //积分百分比
    public static final BigDecimal INTEGRAL_PERCENT = new BigDecimal(100);

    //商品不存在
    public static final Integer INTEGRAL_GOODS_UNKNOWN = 801;
    //积分商品已存在
    public static final Integer INTEGRAL_GOODS_EXISTED = 802;
    public static final Integer INTEGRAL_GOODS_OFFLINE = 804;//积分商品已下线
    public static final Integer INTEGRAL_OF_LACK = 811;
    public static final Integer MEMBER_GOODS_UNKNOWN = 821;
    public static final Integer MEMBER_GOODS_EXISTED = 822;
    public static final Integer MEMBER_GOODS_OFFLINE = 823;//会员商品已下线
    public static final Integer MEMBER_GOODS_EXPIRED = 824;//会员商品已下线
    public static final Integer USER_LEVEL_NO = 831;//不是会员

}
