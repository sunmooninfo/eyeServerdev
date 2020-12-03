package com.eye.brand.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.eye.brand.annotation.LoginUser;
import com.eye.brand.dto.UserInfo;
import com.eye.brand.service.UserTokenManager;
import com.eye.core.utils.IpUtil;
import com.eye.core.utils.JacksonUtil;
import com.eye.core.utils.RegexUtil;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.utils.bcrypt.BCryptPasswordEncoder;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.EyeUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eye.brand.util.BrandResponseCode.*;

/**
 * @author edc
 * @date 2020/7/28 14:56
 * @description
 */
@RestController
@RequestMapping("/langbo/auth")
@Validated
@Api(description = "用户登录服务")
public class EyeAuthController {
    private final Log logger = LogFactory.getLog(EyeAuthController.class);

    @Autowired
    private EyeUserService userService;

    @Autowired
    private WxMaService wxService;



    /**
     * 账号注册
     *
     * @param body    请求内容
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("register")
    @ApiOperation("账号注册")
    @ApiImplicitParam(name="body",value = "{username(用户名): xxx, password(密码): xxx,mobile(手机号): xxx}",required=true,dataType="string")
    public Object register(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
//        String code = JacksonUtil.parseString(body, "code");
        // 如果是小程序注册，则必须非空
        // 其他情况，可以为空
        String wxCode = JacksonUtil.parseString(body, "wxCode");

        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }

        List<EyeUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "用户名已注册,请重新输入");
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册，请重新输入");
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        //判断验证码是否正确
//        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
//            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
//        }

        String openId = "";
        // 非空，则是小程序注册
        // 继续验证openid
        if(!StringUtils.isEmpty(wxCode)) {
            try {
                WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
                openId = result.getOpenid();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "openid 获取失败");
            }
            userList = userService.queryByOpenid(openId);
            if (userList.size() > 1) {
                return ResponseUtil.serious();
            }
            if (userList.size() == 1) {
                EyeUser checkUser = userList.get(0);
                String checkUsername = checkUser.getUsername();
                String checkPassword = checkUser.getPassword();
                if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
                    return ResponseUtil.fail(AUTH_OPENID_BINDED, "openid已绑定账号");
                }
            }
        }

        EyeUser user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new EyeUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixinOpenid(openId);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }



    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    @ApiOperation("账号登录")
    @ApiImplicitParam(name="body",value = "{username(用户名): xxx, password(密码): xxx}",required=true,dataType="string")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<EyeUser> userList = userService.queryByUsername(username);
        EyeUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    //退出
    @PostMapping("logout")
    @ApiOperation("退出")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

}
