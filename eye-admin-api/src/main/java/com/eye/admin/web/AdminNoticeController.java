package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAdmin;
import com.eye.db.domain.EyeNotice;
import com.eye.db.domain.EyeNoticeAdmin;
import com.eye.db.service.EyeAdminService;
import com.eye.db.service.EyeNoticeAdminService;
import com.eye.db.service.EyeNoticeService;

import javax.validation.constraints.NotNull;

import static com.eye.admin.util.AdminResponseCode.NOTICE_UPDATE_NOT_ALLOWED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "通知管理")
@RestController
@RequestMapping("/admin/notice")
@Validated
public class AdminNoticeController {
    private final Log logger = LogFactory.getLog(AdminNoticeController.class);

    @Autowired
    private EyeNoticeService noticeService;
    @Autowired
    private EyeAdminService adminService;
    @Autowired
    private EyeNoticeAdminService noticeAdminService;

    @ApiOperation(value = "通知管理查询")
    @RequiresPermissions("admin:notice:list")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="title",value = "通知标题",required=true,paramType="path",dataType="varchar"),
            @ApiImplicitParam(name="content",value = "通知内容",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String title, String content,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeNotice> noticeList = noticeService.querySelective(title, content, page, limit, sort, order);
        return ResponseUtil.okList(noticeList);
    }

    private Object validate(EyeNotice notice) {
        String title = notice.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    private Integer getAdminId(){
        Subject currentUser = SecurityUtils.getSubject();
        EyeAdmin admin = (EyeAdmin) currentUser.getPrincipal();
        return admin.getId();
    }

    @ApiOperation(value = "通知管理添加")
    @RequiresPermissions("admin:notice:create")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody EyeNotice notice) {
        Object error = validate(notice);
        if (error != null) {
            return error;
        }
        // 1. 添加通知记录
        notice.setAdminId(getAdminId());
        noticeService.add(notice);
        // 2. 添加管理员通知记录
        List<EyeAdmin> adminList = adminService.all();
        EyeNoticeAdmin noticeAdmin = new EyeNoticeAdmin();
        noticeAdmin.setNoticeId(notice.getId());
        noticeAdmin.setNoticeTitle(notice.getTitle());
        for(EyeAdmin admin : adminList){
            noticeAdmin.setAdminId(admin.getId());
            noticeAdminService.add(noticeAdmin);
        }
        return ResponseUtil.ok(notice);
    }

    @ApiOperation(value = "通知管理详情")
    @RequiresPermissions("admin:notice:read")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "通知id",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        EyeNotice notice = noticeService.findById(id);
        List<EyeNoticeAdmin> noticeAdminList = noticeAdminService.queryByNoticeId(id);
        Map<String, Object> data = new HashMap<>(2);
        data.put("notice", notice);
        data.put("noticeAdminList", noticeAdminList);
        return ResponseUtil.ok(data);
    }

    @ApiOperation(value = "通知管理编辑")
    @RequiresPermissions("admin:notice:update")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody EyeNotice notice) {
        Object error = validate(notice);
        if (error != null) {
            return error;
        }
        EyeNotice originalNotice = noticeService.findById(notice.getId());
        if (originalNotice == null) {
            return ResponseUtil.badArgument();
        }
        // 如果通知已经有人阅读过，则不支持编辑
        if(noticeAdminService.countReadByNoticeId(notice.getId()) > 0){
            return ResponseUtil.fail(NOTICE_UPDATE_NOT_ALLOWED, "通知已被阅读，不能重新编辑");
        }
        // 1. 更新通知记录
        notice.setAdminId(getAdminId());
        noticeService.updateById(notice);
        // 2. 更新管理员通知记录
        if(!originalNotice.getTitle().equals(notice.getTitle())){
            EyeNoticeAdmin noticeAdmin = new EyeNoticeAdmin();
            noticeAdmin.setNoticeTitle(notice.getTitle());
            noticeAdminService.updateByNoticeId(noticeAdmin, notice.getId());
        }
        return ResponseUtil.ok(notice);
    }

    @ApiOperation(value = "通知管理删除")
    @RequiresPermissions("admin:notice:delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody EyeNotice notice) {
        // 1. 删除通知管理员记录
        noticeAdminService.deleteByNoticeId(notice.getId());
        // 2. 删除通知记录
        noticeService.deleteById(notice.getId());
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "通知管理批量删除")
    @RequiresPermissions("admin:notice:batch-delete")
    @RequiresPermissionsDesc(menu = {"系统管理", "通知管理"}, button = "批量删除")
    @PostMapping("/batch-delete")
    public Object batchDelete(@RequestBody String body) {
        List<Integer> ids = JacksonUtil.parseIntegerList(body, "ids");
        // 1. 删除通知管理员记录
        noticeAdminService.deleteByNoticeIds(ids);
        // 2. 删除通知记录
        noticeService.deleteByIds(ids);
        return ResponseUtil.ok();
    }
}
