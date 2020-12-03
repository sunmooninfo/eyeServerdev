package com.eye.tool.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeToolAccount;
import com.eye.tool.service.EyeToolService;
import com.eye.tool.util.AdminResponseCode;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 附带工具服务Controller
 */

@RestController
@RequestMapping("/tool/account")
@Api(description = "附带工具")
@Validated
public class EyeToolController {


	private final Log logger = LogFactory.getLog(EyeToolController.class);

	@Autowired
	private EyeToolService eyeToolService;

	/**
	 * 查询数据
	 * @param page   页码设置
	 * @param limit  一页显示内容设置
	 * @param sort   排序字段设置
	 * @param order  排序规则设置
	 * @return
	 */
	@ApiOperation(value = "附带工具列表查询")
	//新模块，暂时功能较少，不适合权限设置，暂时注解掉，后期根据增加功能再做设置。	
	//@RequiresPermissions("tool:account:list")
	//@RequiresPermissionsDesc(menu = {"一级菜单", "二级菜单"}, button = "查询")
	@GetMapping("/list")
	public Object list( 
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		List<EyeToolAccount> accountList=eyeToolService.querySelective(page, limit, sort, order);
		return ResponseUtil.okList(accountList);
	}

	/**
	 * 添加tool数据
	 * @param toolAccount
	 * @return
	 */
	@ApiOperation(value = "添加tool数据")
	//@RequiresPermissions("tool:account:create")
	//@RequiresPermissionsDesc(menu = {"一级菜单", "二级菜单"}, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody EyeToolAccount toolAccount) {
		EyeToolAccount mobile=eyeToolService.fingByMobile(toolAccount.getBindingMobile());
		EyeToolAccount name=eyeToolService.fingByName(toolAccount.getAccountName());
		if(mobile!=null) {
			return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "手机号已经存在");
		}
		if(name!=null) {
			return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST, "账号已经存在");
		}
		eyeToolService.add(toolAccount);
		return ResponseUtil.ok(toolAccount);
	}

	/**
	 * 更新tool数据
	 * @param toolAccount
	 * @return
	 */
	@ApiOperation(value = "tool数据编辑")
	//@RequiresPermissions("tool:account:update")
	//@RequiresPermissionsDesc(menu = {"一级菜单", "二级菜单"}, button = "更新")
	@PostMapping("/update")
	public Object update(@RequestBody EyeToolAccount toolAccount) {
		EyeToolAccount accont = eyeToolService.fingByMobile(toolAccount.getBindingMobile());
		toolAccount.setId(accont.getId());
		if (eyeToolService.updateById(toolAccount) == 0) {
			return ResponseUtil.updatedDataFailed();
		}
		return ResponseUtil.ok(toolAccount);
	}

}
