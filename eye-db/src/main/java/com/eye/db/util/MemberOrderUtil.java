package com.eye.db.util;

import com.eye.db.domain.EyeMemberOrder;

import java.util.ArrayList;
import java.util.List;

/*
*  101 订单生成，未支付
 * 201 支付完成
* */
public class MemberOrderUtil {

    public static final Short STATUS_CREATE = 101;
    public static final Short STATUS_AUTO_CANCEL = 103;
    public static final Short STATUS_CANCEL = 102;
    public static final Short STATUS_PAY = 201;

    public static String orderStatusText(EyeMemberOrder order) {
        int status = order.getStatus().intValue();
        if (status == 101) {
            return "未付款";
        }

        if (status == 102) {
            return "已取消";
        }

        if (status == 103) {
            return "已取消(系统)";
        }

        if (status == 201) {
            return "已付款";
        }
        throw new IllegalStateException("orderStatus不支持");
    }

    public static OrderHandleOption build(EyeMemberOrder order) {
        int status = order.getStatus().intValue();
        OrderHandleOption handleOption = new OrderHandleOption();

        if (status == 101) {
            // 如果订单没有被取消，且没有支付，则可支付，可取消
            handleOption.setCancel(true);
            handleOption.setPay(true);
        } else if (status == 102 || status == 103) {
            // 如果订单已经取消或是已完成，则可删除
            handleOption.setDelete(true);
        } else {
            throw new IllegalStateException("status不支持");
        }

        return handleOption;
    }

    public static List<Short> orderStatus(Integer showType) {
        // 全部订单
        if (showType == 0) {
            return null;
        }

        List<Short> status = new ArrayList<Short>(2);

        if (showType.equals(1)) {
            // 待付款订单
            status.add((short) 101);
        } else if (showType.equals(2)) {
            // 以付款订单
            status.add((short) 201);
        } else {
            return null;
        }

        return status;
    }

    public static boolean isCreateStatus(EyeMemberOrder order) {
        return MemberOrderUtil.STATUS_CREATE == order.getStatus().shortValue();
    }

    public static boolean hasPayed(EyeMemberOrder order) {
        return MemberOrderUtil.STATUS_CREATE != order.getStatus().shortValue()
                && MemberOrderUtil.STATUS_CANCEL != order.getStatus().shortValue()
                && MemberOrderUtil.STATUS_AUTO_CANCEL != order.getStatus().shortValue();
    }

    public static boolean isPayStatus(EyeMemberOrder litemallOrder) {
        return MemberOrderUtil.STATUS_PAY == litemallOrder.getStatus().shortValue();
    }

    public static boolean isAutoCancelStatus(EyeMemberOrder order) {
        return MemberOrderUtil.STATUS_AUTO_CANCEL == order.getStatus().shortValue();
    }

    public static boolean isCancelStatus(EyeMemberOrder order) {
        return MemberOrderUtil.STATUS_CANCEL == order.getStatus().shortValue();
    }

}
