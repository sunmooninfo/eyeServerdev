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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.vo.FootprintVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeFootprint;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeFootprintService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeUserService;

import java.util.ArrayList;
import java.util.List;

@Api(description = "用户足迹管理")
@RestController
@RequestMapping("/admin/footprint")
@Validated
public class AdminFootprintController {
    private final Log logger = LogFactory.getLog(AdminFootprintController.class);

    @Autowired
    private EyeFootprintService footprintService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeUserService userService;

    @ApiOperation(value = "会员足迹查询")
    @RequiresPermissions("admin:footprint:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "会员足迹"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="goodsId",value = "浏览商品id", required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(String userId, String goodsId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeFootprint> footprintList = footprintService.querySelective(userId, goodsId, page, limit, sort,
                order);

        List<FootprintVo> footprintVoList = new ArrayList<>();
        for (EyeFootprint footprint : footprintList) {
            EyeUser EyeUser = userService.findById(footprint.getUserId());
            EyeGoods goods = goodsService.findById(footprint.getGoodsId());

            FootprintVo footprintVo = new FootprintVo();
            footprintVo.setId(footprint.getId());
            footprintVo.setUserId(footprint.getUserId());
            if (EyeUser != null){
                footprintVo.setUsername(EyeUser.getNickname());
            }
            footprintVo.setGoodsId(footprint.getGoodsId());
            if (goods != null){
                footprintVo.setGoodsName(goods.getName());
            }
            footprintVo.setAddTime(footprint.getAddTime());
            footprintVo.setUpdateTime(footprint.getUpdateTime());
            footprintVo.setDeleted(footprint.getDeleted());
            footprintVoList.add(footprintVo);
        }

        return ResponseUtil.okList(footprintVoList);
    }
}
