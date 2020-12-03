package com.eye.common.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeBrand;
import com.eye.db.service.EyeBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 专题服务
 */
@RestController
@RequestMapping("/wx/brand")
@Validated
@Api(description = "品牌管理")
public class CommonBrandController {
    private final Log logger = LogFactory.getLog(CommonBrandController.class);

    @Autowired
    private EyeBrandService brandService;

    /**
     * 品牌列表
     *
     * @param page 分页页数
     * @param limit 分页大小
     * @return 品牌列表
     */
    @GetMapping("list")
    @ApiOperation("品牌列表")
    public Object list(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeBrand> brandList = brandService.query(page, limit, sort, order);
        return ResponseUtil.okList(brandList);
    }

    /**
     * 品牌详情
     *
     * @param id 品牌ID
     * @return 品牌详情
     */
    @GetMapping("detail")
    @ApiOperation("品牌详情")
    @ApiImplicitParam(name="id",value ="品牌id",required=true,paramType = "path",dataType="int")
    public Object detail(@NotNull Integer id) {
        EyeBrand entity = brandService.findById(id);
        if (entity == null) {
            return ResponseUtil.badArgumentValue();
        }

        return ResponseUtil.ok(entity);
    }
}