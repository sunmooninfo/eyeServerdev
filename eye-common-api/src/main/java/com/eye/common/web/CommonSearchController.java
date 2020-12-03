package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeKeyword;
import com.eye.db.domain.EyeSearchHistory;
import com.eye.db.service.EyeKeywordService;
import com.eye.db.service.EyeSearchHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务
 * <p>
 * 注意：目前搜索功能非常简单，只是基于关键字匹配。
 */
@RestController
@RequestMapping("/common/search")
@Validated
@Api(description = "商品搜索服务")
public class CommonSearchController {
    private final Log logger = LogFactory.getLog(CommonSearchController.class);

    @Autowired
    private EyeKeywordService keywordsService;
    @Autowired
    private EyeSearchHistoryService searchHistoryService;

    /**
     * 搜索页面信息
     * <p>
     * 如果用户已登录，则给出用户历史搜索记录；
     * 如果没有登录，则给出空历史搜索记录。
     *
     * @param userId 用户ID，可选
     * @return 搜索页面信息
     */
    @GetMapping("index")
    @ApiOperation("搜索页面信息")
    @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    public Object index(@LoginUser Integer userId) {
        //取出输入框默认的关键词
        EyeKeyword defaultKeyword = keywordsService.queryDefault();
        //取出热闹关键词
        List<EyeKeyword> hotKeywordList = keywordsService.queryHots();

        List<EyeSearchHistory> historyList = null;
        if (userId != null) {
            //取出用户历史关键字
            historyList = searchHistoryService.queryByUid(userId);
        } else {
            historyList = new ArrayList<>(0);
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("defaultKeyword", defaultKeyword);
        data.put("historyKeywordList", historyList);
        data.put("hotKeywordList", hotKeywordList);
        return ResponseUtil.ok(data);
    }

    /**
     * 关键字提醒
     * <p>
     * 当用户输入关键字一部分时，可以推荐系统中合适的关键字。
     *
     * @param keyword 关键字
     * @return 合适的关键字
     */
    @GetMapping("helper")
    @ApiOperation("关键字提醒")
    @ApiImplicitParam(name="keyword",value = "关键字",required=true,paramType="path",dataType="string")
    public Object helper(@NotEmpty String keyword,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit) {
        List<EyeKeyword> keywordsList = keywordsService.queryByKeyword(keyword, page, limit);
        String[] keys = new String[keywordsList.size()];
        int index = 0;
        for (EyeKeyword key : keywordsList) {
            keys[index++] = key.getKeyword();
        }
        return ResponseUtil.ok(keys);
    }

    /**
     * 清除用户搜索历史
     *
     * @param userId 用户ID
     * @return 清理是否成功
     */
    @PostMapping("clearhistory")
    @ApiOperation("清除用户搜索历史")
    @ApiImplicitParam(name="userId",value = "用户id",required=true,paramType="path",dataType="int")
    public Object clearhistory(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        searchHistoryService.deleteByUid(userId);
        return ResponseUtil.ok();
    }
}
