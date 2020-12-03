package com.eye.common.web;

import com.eye.core.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务
 */
@RestController
@RequestMapping("/common/index")
@Api(description = "测试服务")
public class CommonIndexController {
    private final Log logger = LogFactory.getLog(CommonIndexController.class);

    /**
     * 测试数据
     *
     * @return 测试数据
     */
    @GetMapping("/index")
    @ApiOperation("测试数据")
    public Object index() {
        return ResponseUtil.ok("hello world, this is common service");
    }

}