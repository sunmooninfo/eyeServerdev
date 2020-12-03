package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.common.service.GetRegionService;
import com.eye.core.utils.RegexUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeAddress;
import com.eye.db.service.EyeAddressService;
import com.eye.db.service.EyeRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户收货地址服务
 */
@RestController
@RequestMapping("/common/address")
@Validated
@Api(description = "用户收货地址服务")
public class CommonAddressController extends GetRegionService {
	private final Log logger = LogFactory.getLog(CommonAddressController.class);

	@Autowired
	private EyeAddressService addressService;

	@Autowired
	private EyeRegionService regionService;


	/**
	 * 用户收货地址列表
	 *
	 * @param userId 用户ID
	 * @return 收货地址列表
	 */
	@GetMapping("list")
	@ApiOperation("用户收货地址列表")
	@ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
	public Object list(@LoginUser Integer userId) {
		String info = "查询用户收获地址";
		logger.error(getClass()+"----------------------------"+info);
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		List<EyeAddress> addressList = addressService.queryByUid(userId);
		return ResponseUtil.okList(addressList);
	}

	/**
	 * 收货地址详情
	 *
	 * @param userId 用户ID
	 * @param id     收货地址ID
	 * @return 收货地址详情
	 */
	@GetMapping("detail")
	@ApiOperation("用户收货地址详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
			@ApiImplicitParam(name="id",value = "地址id",required=true,paramType="path",dataType="int")
	})
	public Object detail(@LoginUser Integer userId, @NotNull Integer id) {
		String info = "查询用户收获地址详情";
		logger.error(getClass()+"----------------------------"+info);
		if (userId == null) {
			return ResponseUtil.unlogin();
		}

		EyeAddress address = addressService.query(userId, id);
		if (address == null) {
			return ResponseUtil.badArgumentValue();
		}
		return ResponseUtil.ok(address);
	}

	private Object validate(EyeAddress address) {
		String name = address.getName();
		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.badArgument();
		}

		// 测试收货手机号码是否正确
		String mobile = address.getTel();
		if (StringUtils.isEmpty(mobile)) {
			return ResponseUtil.badArgument();
		}
		if (!RegexUtil.isMobileSimple(mobile)) {
			return ResponseUtil.badArgument();
		}

		String province = address.getProvince();
		if (StringUtils.isEmpty(province)) {
			return ResponseUtil.badArgument();
		}

		String city = address.getCity();
		if (StringUtils.isEmpty(city)) {
			return ResponseUtil.badArgument();
		}

		String county = address.getCounty();
		if (StringUtils.isEmpty(county)) {
			return ResponseUtil.badArgument();
		}


		String areaCode = address.getAreaCode();
		if (StringUtils.isEmpty(areaCode)) {
			return ResponseUtil.badArgument();
		}

		String detailedAddress = address.getAddressDetail();
		if (StringUtils.isEmpty(detailedAddress)) {
			return ResponseUtil.badArgument();
		}

		Boolean isDefault = address.getIsDefault();
		if (isDefault == null) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	/**
	 * 添加或更新收货地址
	 *
	 * @param userId  用户ID
	 * @param address 用户收货地址
	 * @return 添加或更新操作结果
	 */
	@PostMapping("save")
	@ApiOperation("用户添加或更新收货地址")
	public Object save(@LoginUser Integer userId, @RequestBody EyeAddress address) {
		String info = "添加或更新收货地址";
		logger.error(getClass()+"----------------------------"+info);
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		Object error = validate(address);
		if (error != null) {
			return error;
		}
		//判断添加用户地址id是否等于null或者0
		if (address.getId() == null || address.getId().equals(0)) {
			//判断添加地址是否为默认地址
			if (address.getIsDefault()) {
				// 重置其他收货地址的默认选项
				addressService.resetDefault(userId);
			}

			address.setId(null);
			address.setUserId(userId);
			addressService.add(address);
		} else {
			EyeAddress eyeAddress = addressService.query(userId, address.getId());
			if (eyeAddress == null) {
				return ResponseUtil.badArgumentValue();
			}

			if (address.getIsDefault()) {
				// 重置其他收货地址的默认选项
				addressService.resetDefault(userId);
			}

			address.setUserId(userId);
			addressService.update(address);
		}
		return ResponseUtil.ok(address.getId());
	}

	/**
	 * 删除收货地址
	 *
	 * @param userId  用户ID
	 * @param address 用户收货地址，{ id: xxx }
	 * @return 删除操作结果
	 */
	@PostMapping("delete")
	@ApiOperation("用户删除收货地址")
	public Object delete(@LoginUser Integer userId, @RequestBody EyeAddress address) {
		String info = "删除收获地址";
		logger.error(getClass()+"----------------------------"+info);
		if (userId == null) {
			return ResponseUtil.unlogin();
		}
		Integer id = address.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}
		EyeAddress eyeAddress = addressService.query(userId, id);
		if (eyeAddress == null) {
			return ResponseUtil.badArgumentValue();
		}

		addressService.delete(id);
		return ResponseUtil.ok();
	}

}