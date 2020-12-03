package com.eye.brand.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeStorage;
import com.eye.storage.service.EyeStorageService;
import com.eye.storage.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;

@RestController
@RequestMapping("/langbo/storage")
@Validated
@Api(description = "对象存储服务")
public class EyeStorageController {
    private final Log logger = LogFactory.getLog(EyeStorageController.class);

    @Autowired
    private StorageService storagesService;
    @Autowired
    private EyeStorageService eyeStorageService;

    //上传
    @PostMapping("/create")
    @ApiOperation("上传")
    public Object create(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        EyeStorage eyeStorage = storagesService.store(file.getInputStream(), file.getSize(),
                file.getContentType(), originalFilename);
        return ResponseUtil.ok(eyeStorage);
    }

    //下载
    @GetMapping("/download")
    @ApiOperation("下载")
    @ApiImplicitParam(name="key",value = "存储对象key",required=true,paramType="path",dataType="string")
    public ResponseEntity<Resource> download(@NotNull String key) {
        EyeStorage eyeStorage = eyeStorageService.findByKey(key);
        if (eyeStorage == null) {
            return ResponseEntity.notFound().build();
        }
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }

        String type = eyeStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storagesService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


}
