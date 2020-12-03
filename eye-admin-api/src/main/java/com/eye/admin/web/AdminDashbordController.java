package com.eye.admin.web;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eye.core.utils.ResponseUtil;
import com.eye.db.service.EyeArticleService;
import com.eye.db.service.EyeGoodsProductService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeOrderService;
import com.eye.db.service.EyeUserService;

import java.util.HashMap;
import java.util.Map;

@Api(description = "首页管理")
@RestController
@RequestMapping("/admin/dashboard")
@Validated
public class AdminDashbordController {
    private final Log logger = LogFactory.getLog(AdminDashbordController.class);

    @Autowired
    private EyeUserService userService;
    @Autowired
    private EyeGoodsService goodsService;
    @Autowired
    private EyeGoodsProductService productService;
    @Autowired
    private EyeOrderService orderService;
    @Autowired
    private EyeArticleService articleService;

    @ApiOperation(value = "首页管理")
    @GetMapping("")
    public Object info() {
        int userTotal = userService.count();
        int goodsTotal = goodsService.count();
        int productTotal = productService.count();
        int orderTotal = orderService.count();
        int articleTotal = articleService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("productTotal", productTotal);
        data.put("orderTotal", orderTotal);
        data.put("articleTotal", articleTotal);

        return ResponseUtil.ok(data);
    }

}
