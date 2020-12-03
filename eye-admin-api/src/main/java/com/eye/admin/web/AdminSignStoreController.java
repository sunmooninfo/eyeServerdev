package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeSignStore;
import com.eye.db.service.EyeSignStoreService;

import java.util.List;

@Api(description = "分销管理")
@RestController
@RequestMapping("/admin/signstore")
@Validated
public class AdminSignStoreController {

	private final Log logger = LogFactory.getLog(AdminBrandController.class);
	@Autowired
	private EyeSignStoreService signstoreService;

	private Object validate(EyeSignStore signstore) {
		String storeName = signstore.getName();
		if (storeName == null) {
			return ResponseUtil.badArgument();
		}
		String manager = signstore.getManager();
		if (manager == null) {
			return ResponseUtil.badArgument();
		}
		String managerMobile = signstore.getManagerMobile();
		if (managerMobile == null) {
			return ResponseUtil.badArgument();
		}
		String province = signstore.getProvince();
		if (province == null) {
			return ResponseUtil.badArgument();
		}
		String city =signstore.getCity();
		if (city == null) {
			return ResponseUtil.badArgument();
		}
		String county = signstore.getCounty();
		if (county == null) {
			return ResponseUtil.badArgument();
		}
		String addressDeail = signstore.getAddressDetail();
		if (addressDeail == null) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	@ApiOperation(value = "分销管理添加")
	@RequiresPermissions("admin:signstore:create")
	@RequiresPermissionsDesc(menu = {"商城管理", "分销管理"}, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody EyeSignStore signstore) {
		Object error = validate(signstore);
		if (error != null) {
			return error;
		}
		signstoreService.add(signstore);
		return ResponseUtil.ok(signstore);
	}

	@ApiOperation(value = "分销管理删除")
	@RequiresPermissions("admin:signstore:delete")
	@RequiresPermissionsDesc(menu = {"商城管理", "分销管理"}, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody EyeSignStore signstore){

		Integer id = signstore.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}
		signstoreService.deleteById(id);
		return ResponseUtil.ok();
	}

	@ApiOperation(value = "分销管理编辑")
	@RequiresPermissions("admin:signstore:update")
	@RequiresPermissionsDesc(menu = {"商城管理", "分销管理"}, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody EyeSignStore signstore) {

		if (signstoreService.updateById(signstore) == 0) {
			return ResponseUtil.updatedDataFailed();
		}
		return ResponseUtil.ok(signstore);
	}

	@ApiOperation(value = "分销管理查询")
	@RequiresPermissions("admin:signstore:list")
	@RequiresPermissionsDesc(menu = {"商城管理", "分销管理"}, button = "查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="managerMobile",value = "门店管理者手机",required=true,paramType="path",dataType="varchar"),
			@ApiImplicitParam(name="name",value = "门店名称",required=true,paramType="path",dataType="varchar")
	})
	@GetMapping("/list")
	public Object list(String managerMobile, String name,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		String info  = "查询相应分销管理者，管理的分销信息";
		logger.error(getClass()+"----------------------------"+info);
		List<EyeSignStore> signstoreList = signstoreService.querySelective(managerMobile, name, page, limit, sort, order);
		return ResponseUtil.okList(signstoreList);
	}


}
