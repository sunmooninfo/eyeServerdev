package com.eye.tool.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeToolAdmin;
import com.eye.tool.service.EyeToolAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 附带工具管理服务Controller
 */

@RestController
@RequestMapping("/tool/admin")
@Api(description = "附带工具管理")
@Validated
public class EyeToolAdminController {

	private final Log logger = LogFactory.getLog(EyeToolAdminController.class);

	@Autowired
	private EyeToolAdminService uloveToolAdminService;

	

	@ApiOperation(value = "附带工具管理列表查询")
//	@RequiresPermissions("tool:admin:list")
//	@RequiresPermissionsDesc(menu = {"附件工具", "管理员"}, button = "查询")
	@GetMapping("/list")
	public Object list( 
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		List<EyeToolAdmin> accountList=uloveToolAdminService.querySelective(page, limit, sort, order);
		return ResponseUtil.okList(accountList);
	}


}
