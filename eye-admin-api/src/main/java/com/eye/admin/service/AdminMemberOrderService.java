package com.eye.admin.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeMemberOrder;
import com.eye.db.domain.EyeMemberPackage;
import com.eye.db.domain.UserVo;
import com.eye.db.service.EyeMemberOrderService;
import com.eye.db.service.EyeMemberPackageService;
import com.eye.db.service.EyeUserService;
import com.eye.db.util.MemberOrderUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eye.admin.util.AdminResponseCode.ORDER_DELETE_FAILED;

@Service
public class AdminMemberOrderService {
    private final Log logger = LogFactory.getLog(AdminMemberOrderService.class);
    @Autowired
    private EyeMemberOrderService memberOrderService;
    @Autowired
    private EyeMemberPackageService memberPackageService;
    @Autowired
    private EyeUserService userService;
    @Resource
    private LogHelper logHelper;

    public Object list(Integer userId, String orderSn, LocalDateTime start, LocalDateTime end, List<Short> orderStatusArray, Integer page, Integer limit, String sort, String order) {
        List<EyeMemberOrder> memberOrderList = memberOrderService.querySelective(userId, orderSn, start, end, orderStatusArray, page, limit,
                sort, order);
        return ResponseUtil.okList(memberOrderList);
    }

    //订单详情
    public Object detail(Integer id) {
        EyeMemberOrder memberOrder = memberOrderService.findById(id);
        UserVo user = userService.findUserVoById(memberOrder.getUserId());
        EyeMemberPackage memberPackage = memberPackageService.findById(memberOrder.getPackageId());
        Map<String, Object> data = new HashMap<>();
        data.put("id", memberOrder.getId());
        data.put("orderSn", memberOrder.getOrderSn());
        data.put("addTime", memberOrder.getAddTime());
        data.put("endTime", memberOrder.getEndTime());
        data.put("consignee", memberOrder.getConsignee());
        data.put("mobile", memberOrder.getMobile());
        data.put("price", memberOrder.getPrice());
        data.put("orderStatusText", MemberOrderUtil.orderStatusText(memberOrder));

        data.put("months", memberPackage.getMonths());
        data.put("packageName", memberPackage.getName());
        data.put("avatar", user.getAvatar());


        return ResponseUtil.ok(data);
    }

    //删除订单
    public Object delete(String body) {

        Integer memberOrderId = JacksonUtil.parseInteger(body, "id");
        EyeMemberOrder memberOrder = memberOrderService.findById(memberOrderId);
        if (memberOrder == null) {
            return ResponseUtil.badArgument();
        }

        // 如果订单不是关闭状态(已取消、系统取消)，则不能删除
        Short status = memberOrder.getStatus();
        if (!status.equals(MemberOrderUtil.STATUS_CANCEL) && !status.equals(MemberOrderUtil.STATUS_AUTO_CANCEL)) {
            return ResponseUtil.fail(ORDER_DELETE_FAILED, "订单不能删除");
        }

        memberOrderService.deleteById(memberOrderId);
        //将删除订单的操作记录在操作日志中
        logHelper.logOrderSucceed("删除", "会员套餐订单编号 " + memberOrder.getOrderSn());
        return ResponseUtil.ok();
    }
}
