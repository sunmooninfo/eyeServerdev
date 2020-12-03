package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeStorage;
import com.eye.storage.service.EyeStorageService;
import com.eye.storage.service.StorageService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Api(description = "对象存储管理")
@RestController
@RequestMapping("/admin/storage")
@Validated
public class AdminStorageController {
    private final Log logger = LogFactory.getLog(AdminStorageController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private EyeStorageService eyeStorageService;

    @ApiOperation(value = "对象存储查询")
    @RequiresPermissions("admin:storage:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="key",value = "文件的唯一索引",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="name",value = "文件名",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String key, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeStorage> storageList = eyeStorageService.querySelective(key, name, page, limit, sort, order);
        return ResponseUtil.okList(storageList);
    }

    @ApiOperation(value = "对象存储上传")
    @RequiresPermissions("admin:storage:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "上传")
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

    @ApiOperation(value = "对象存储详情")
    @RequiresPermissions("admin:storage:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "详情")
    @PostMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeStorage storageInfo = eyeStorageService.findById(id);
        if (storageInfo == null) {
            return ResponseUtil.badArgumentValue();
        }
        return ResponseUtil.ok(storageInfo);
    }

    @ApiOperation(value = "对象存储编辑")
    @RequiresPermissions("admin:storage:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeStorage eyeStorage) {
        if (eyeStorageService.update(eyeStorage) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(eyeStorage);
    }

    @ApiOperation(value = "对象存储删除")
    @RequiresPermissions("admin:storage:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "对象存储"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeStorage eyeStorage) {
        String key = eyeStorage.getKey();
        if (StringUtils.isEmpty(key)) {
            return ResponseUtil.badArgument();
        }
        eyeStorageService.deleteByKey(key);
        storageService.delete(key);
        return ResponseUtil.ok();
    }
}
