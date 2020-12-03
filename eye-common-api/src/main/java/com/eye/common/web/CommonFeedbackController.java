package com.eye.common.web;

import com.eye.common.annotation.LoginUser;
import com.eye.core.utils.RegexUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeFeedback;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeFeedbackService;
import com.eye.db.service.EyeUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈服务
 *
 * @author Yogeek
 * @date 2018/8/25 14:10
 */
@RestController
@RequestMapping("/common/feedback")
@Validated
@Api(description = "意见反馈服务")
public class CommonFeedbackController {
    private final Log logger = LogFactory.getLog(CommonFeedbackController.class);

    @Autowired
    private EyeFeedbackService feedbackService;
    @Autowired
    private EyeUserService userService;

    private Object validate(EyeFeedback feedback) {
        String content = feedback.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }

        String type = feedback.getFeedType();
        if (StringUtils.isEmpty(type)) {
            return ResponseUtil.badArgument();
        }

        Boolean hasPicture = feedback.getHasPicture();
        if (hasPicture == null || !hasPicture) {
            feedback.setPicUrls(new String[0]);
        }

        // 测试手机号码是否正确
        String mobile = feedback.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileSimple(mobile)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    /**
     * 添加意见反馈
     *
     * @param userId   用户ID
     * @param feedback 意见反馈
     * @return 操作结果
     */
    @PostMapping("submit")
    @ApiOperation("添加意见反馈")
    public Object submit(@LoginUser Integer userId, @RequestBody EyeFeedback feedback) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(feedback);
        if (error != null) {
            return error;
        }

        EyeUser user = userService.findById(userId);
        String username = user.getNickname();
        feedback.setId(null);
        feedback.setUserId(userId);
        feedback.setUsername(username);
        //状态默认是0，1表示状态已发生变化
        feedback.setStatus(1);
        feedbackService.add(feedback);

        return ResponseUtil.ok();
    }

}
