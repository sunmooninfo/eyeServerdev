package com.eye.admin.web;

import java.time.LocalDateTime;
import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeCommission;
import com.eye.db.service.EyeCommissionService;

@Api(description ="佣金管理")
@RestController
@RequestMapping("/admin/commission")
@Validated
public class AdminCommissionController {
	private final Log logger = LogFactory.getLog(AdminBrandController.class);

	@Autowired
	private EyeCommissionService commissionService;

	/**
	 * 查询佣金信息
	 * @param
	 * @param page
	 * @param limit
	 * @return
	 */
	@ApiOperation(value = "佣金管理查询")
	@RequiresPermissions("admin:commission:list")
	@RequiresPermissionsDesc(menu={"商城管理","佣金管理"},button="查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="orderSn",value = "订单编号",required=true,paramType="path",dataType="varchar"),
			@ApiImplicitParam(name="storeName",value = "店名",required=true,paramType="path",dataType="varchar")
	})
	@GetMapping("/list")
	public Object list(String orderSn, String storeName,
					   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
					   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
					   @RequestParam(defaultValue = "1") Integer page,
					   @RequestParam(defaultValue = "10") Integer limit){
		String info = "查询佣金信息";
		logger.info(info);
		List<EyeCommission> commissions=commissionService.querySelective(orderSn,"%"+storeName+"%", start,end,page, limit);
		return ResponseUtil.okList(commissions);
	}

}
