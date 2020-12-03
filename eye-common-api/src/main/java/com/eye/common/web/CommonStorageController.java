package com.eye.common.web;

import com.eye.core.utils.CharUtil;
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

import java.io.IOException;

/**
 * 对象存储服务
 */
@RestController
@RequestMapping("/common/storage")
@Validated
@Api(description = "对象存储服务")
public class CommonStorageController {
    private final Log logger = LogFactory.getLog(CommonStorageController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private EyeStorageService litemallStorageService;

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        EyeStorage storageInfo = null;

        do {
            key = CharUtil.getRandomString(20) + suffix;
            storageInfo = litemallStorageService.findByKey(key);
        }
        while (storageInfo != null);

        return key;
    }

    @PostMapping("/upload")
    @ApiOperation("上传存储对象")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        EyeStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResponseUtil.ok(litemallStorage);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    @ApiOperation("访问存储对象")
    @ApiImplicitParam(name="key",value = "存储对象key",required=true,paramType="path",dataType="string")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        EyeStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/download/{key:.+}")
    @ApiOperation("下载存储对象")
    @ApiImplicitParam(name="key",value = "存储对象key",required=true,paramType="path",dataType="string")
    public ResponseEntity<Resource> download(@PathVariable String key) {
        EyeStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }

        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
