package com.eye.admin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.vo.HistoryVo;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeSearchHistory;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeSearchHistoryService;
import com.eye.db.service.EyeUserService;

import java.util.ArrayList;
import java.util.List;

@Api(description = "搜索历史管理")
@RestController
@RequestMapping("/admin/history")
public class AdminHistoryController {
    private final Log logger = LogFactory.getLog(AdminHistoryController.class);

    @Autowired
    private EyeSearchHistoryService searchHistoryService;
    @Autowired
    private EyeUserService userService;

    @ApiOperation(value = "搜索历史查询")
    @RequiresPermissions("admin:history:list")
    @RequiresPermissionsDesc(menu = {"用户管理", "搜索历史"}, button = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="keyword",value = "关键字",required=true,paramType="path",dataType="varchar")
    })
    @GetMapping("/list")
    public Object list(String userId, String keyword,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<EyeSearchHistory> historyList = searchHistoryService.querySelective(userId, keyword, page, limit,
                sort, order);

        List<HistoryVo> historyVos = new ArrayList<>();

        for (EyeSearchHistory history : historyList) {
            EyeUser EyeUser = userService.findById(history.getUserId());
            HistoryVo historyVo = new HistoryVo();
            historyVo.setId(history.getId());
            historyVo.setUserId(history.getUserId());
            if (EyeUser != null){
                historyVo.setUsername(EyeUser.getNickname());
            }
            historyVo.setKeyword(history.getKeyword());
            historyVo.setFrom(history.getFrom());
            historyVo.setAddTime(history.getAddTime());
            historyVo.setUpdateTime(history.getUpdateTime());
            historyVo.setDeleted(history.getDeleted());
            historyVos.add(historyVo);
        }
        return ResponseUtil.okList(historyVos);
    }
}
