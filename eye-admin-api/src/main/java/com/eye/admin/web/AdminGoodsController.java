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
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.dto.GoodsAllinone;
import com.eye.admin.service.AdminGoodsService;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeGoods;

import javax.validation.constraints.NotNull;

@Api(description = "商品管理")
@RestController
@RequestMapping("/admin/goods")
@Validated
public class AdminGoodsController {
    private final Log logger = LogFactory.getLog(AdminGoodsController.class);

    @Autowired
    private AdminGoodsService adminGoodsService;

    /**
     * 查询商品
     *
     * @param goodsId
     * @param goodsSn
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @ApiOperation(value = "商品列表查询")
    @RequiresPermissions("admin:goods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品列表"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodsId",value = "商品id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="goodsSn",value = "商品编号", required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="name",value = "商品名称", required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="categoryId",value = "类目id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/list")
    public Object list(Integer goodsId, String goodsSn, String name, Integer categoryId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminGoodsService.list(goodsId, goodsSn, name, categoryId, page, limit, sort, order);
    }

    @ApiOperation(value = "商品管理分类")
    @GetMapping("/catAndBrand")
    public Object list2() {
        return adminGoodsService.list2();
    }

    /**
     * 编辑商品
     *
     * @param goodsAllinone
     * @return
     */
    @ApiOperation(value = "商品列表编辑")
    @RequiresPermissions("admin:goods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品列表"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody GoodsAllinone goodsAllinone) {
        return adminGoodsService.update(goodsAllinone);
    }

    /**
     * 删除商品
     *
     * @param goods
     * @return
     */
    @ApiOperation(value = "商品列表删除")
    @RequiresPermissions("admin:goods:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品列表"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeGoods goods) {
        return adminGoodsService.delete(goods);
    }

    /**
     * 添加商品
     *
     * @param goodsAllinone
     * @return
     */
    @ApiOperation(value = "商品列表")
    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品列表"}, button = "上架")
    @PostMapping("/create")
    public Object create(@RequestBody GoodsAllinone goodsAllinone) {
        return adminGoodsService.create(goodsAllinone);
    }

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "商品列表详情")
    @RequiresPermissions("admin:goods:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品列表"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="goodsId",value = "商品id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminGoodsService.detail(id);

    }

}
