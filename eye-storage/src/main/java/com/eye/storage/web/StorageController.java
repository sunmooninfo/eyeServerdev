package com.eye.storage.web;


import com.eye.db.domain.EyeStorage;
import com.eye.storage.service.StorageService;
import com.eye.storage.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(description = "对象存储管理")
@RestController
@RequestMapping("/storage")
@Validated
public class StorageController {

    private final Log logger = LogFactory.getLog(StorageController.class);
    @Autowired
    private StorageService storageService;

    @ApiOperation(value = "对象存储上传")
    @PostMapping("/create")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if(originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg")){
            if(file.getSize() > 7340032 ) {
                return ResponseUtil.badArgument();
            }
        }
        EyeStorage eyeStorage = storageService.store(file.getInputStream(), file.getSize(),
                file.getContentType(), originalFilename);
        return ResponseUtil.ok(eyeStorage);
    }


}
