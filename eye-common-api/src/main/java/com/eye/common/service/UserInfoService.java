package com.eye.common.service;

import com.eye.common.dto.UserInfo;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserInfoService {
    @Autowired
    private EyeUserService userService;


    public UserInfo getInfo(Integer userId) {
        EyeUser user = userService.findById(userId);
        Assert.state(user != null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatarUrl(user.getAvatar());
        return userInfo;
    }
}
