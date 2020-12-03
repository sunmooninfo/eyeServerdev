package com.eye.cms.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeContact;
import com.eye.db.service.EyeContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/cms/contact")
@Api(description = "联系售后服务")
public class CmsContactController {
    private final Log logger = LogFactory.getLog(CmsCategoryController.class);
    @Autowired
    private EyeContactService contactService;

    @ApiOperation(value = "联系售后服务添加")
    @PostMapping("/add")
    public Object add(@RequestBody EyeContact contact) {
        contactService.add(contact);
        return ResponseUtil.ok(contact);
    }




}
