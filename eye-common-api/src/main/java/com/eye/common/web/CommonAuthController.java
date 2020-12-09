package com.eye.common.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.eye.common.annotation.LoginUser;
import com.eye.common.dto.UserInfo;
import com.eye.common.dto.WxLoginInfo;
import com.eye.common.service.CaptchaCodeManager;
import com.eye.common.service.TokenCacheManager;
import com.eye.common.service.UserTokenManager;
import com.eye.core.utils.*;
import com.eye.core.utils.bcrypt.BCryptPasswordEncoder;
import com.eye.db.domain.EyeUser;
import com.eye.db.service.CouponAssignService;
import com.eye.db.service.EyeUserService;
import com.eye.sms.notify.SmsNotifyService;
import com.eye.sms.notify.SmsNotifyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.util.UUID;

import static com.eye.common.util.CommonResponseCode.*;

/**
 * 鉴权服务
 */
@RestController
@RequestMapping("/common/auth")
@Validated
@Api(description = "鉴权服务")
public class CommonAuthController {
    private final Log logger = LogFactory.getLog(CommonAuthController.class);

    @Autowired
    private EyeUserService userService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private SmsNotifyService notifyService;

    @Autowired
    private CouponAssignService couponAssignService;

    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    @ApiOperation("用户账号登录")
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
        String refreshToken = UUID.randomUUID().toString();
        TokenCacheManager.addRefreshToken(token,refreshToken,user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("refreshToken",refreshToken);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     */
    @PostMapping("login_by_weixin")
    @ApiOperation("用户微信登录")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        String unionid = null;
        EyeUser user = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
            unionid = result.getUnionid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sessionKey == null || openId == null ){
            return ResponseUtil.fail();
        }

        if(unionid == null ){
            user = userService.queryByOid(openId);
            if (user == null) {
                user = new EyeUser();
                user.setUsername(openId);
                user.setPassword(openId);
                user.setWeixinOpenid(openId);
                user.setUnionId(unionid);
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
                user.setGender(userInfo.getGender());
                user.setUserLevel((byte) 0);
                user.setStatus((byte) 0);
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
                user.setSessionKey(sessionKey);

                userService.add(user);

                // 新用户发送注册优惠券
                couponAssignService.assignForRegister(user.getId());
            } else {
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
                user.setSessionKey(sessionKey);
                if (userService.updateById(user) == 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }
        }else{
            user = userService.queryByUnionId(unionid);
            if (user == null) {
                user = new EyeUser();
                user.setUsername(openId);
                user.setPassword(openId);
                user.setWeixinOpenid(openId);
                user.setUnionId(unionid);
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
                user.setGender(userInfo.getGender());
                user.setUserLevel((byte) 0);
                user.setStatus((byte) 0);
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
                user.setSessionKey(sessionKey);

                userService.add(user);

                // 新用户发送注册优惠券
                couponAssignService.assignForRegister(user.getId());
            } else {
                user.setWeixinOpenid(openId);
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
                user.setSessionKey(sessionKey);
                if (userService.updateById(user) == 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }
        }

        // token
        String token = UserTokenManager.generateToken(user.getId());
        String refreshToken = UUID.randomUUID().toString();
        TokenCacheManager.addRefreshToken(token,refreshToken,user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("refreshToken",refreshToken);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    @PostMapping("app_login_by_weixin")
    @ApiOperation("用户在App中使用微信登录")
    public Object appLoginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
//        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (userInfo == null) {
            return ResponseUtil.badArgument();
        }

//        String sessionKey = null;
        String openId = userInfo.getOpenId();
        String unionId = userInfo.getUnionId();
        EyeUser user = null;
        if(openId == null ){
            return ResponseUtil.fail();
        }
        if(unionId == null){
            user = userService.queryByOid(openId);
            if (user == null) {
                user = new EyeUser();
                user.setUsername(openId);
                user.setPassword(openId);
                user.setAppOpenid(userInfo.getOpenId());
                user.setUnionId(userInfo.getUnionId());
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
                user.setGender(userInfo.getGender());
                user.setUserLevel((byte) 0);
                user.setStatus((byte) 0);
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
//            user.setSessionKey(sessionKey);

                userService.add(user);

                // 新用户发送注册优惠券
                couponAssignService.assignForRegister(user.getId());
            } else {
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
//            user.setSessionKey(sessionKey);
                if (userService.updateById(user) == 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }
        }else{
            user = userService.queryByUnionId(userInfo.getUnionId());
            if (user == null) {
                user = new EyeUser();
                user.setUsername(openId);
                user.setPassword(openId);
                user.setAppOpenid(userInfo.getOpenId());
                user.setUnionId(userInfo.getUnionId());
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
                user.setGender(userInfo.getGender());
                user.setUserLevel((byte) 0);
                user.setStatus((byte) 0);
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
//            user.setSessionKey(sessionKey);

                userService.add(user);

                // 新用户发送注册优惠券
                couponAssignService.assignForRegister(user.getId());
            } else {
                user.setAppOpenid(userInfo.getOpenId());
                user.setLastLoginTime(LocalDateTime.now());
                user.setLastLoginIp(IpUtil.getIpAddr(request));
//            user.setSessionKey(sessionKey);
                if (userService.updateById(user) == 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }
        }

        // token
        String token = UserTokenManager.generateToken(user.getId());
        String refreshToken = UUID.randomUUID().toString();
        TokenCacheManager.addRefreshToken(token,refreshToken,user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("refreshToken",refreshToken);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }
    /**
     * 请求注册验证码
     *
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile }
     * @return
     */
    @PostMapping("regCaptcha")
    @ApiOperation("请求注册验证码")
    @ApiImplicitParam(name="body",value = "{ mobile(手机号):xxx }",required=true,paramType="path",dataType="string")
    public Object registerCaptcha(@RequestBody String body) {
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(phoneNumber)) {
            return ResponseUtil.badArgumentValue();
        }

        if (!notifyService.isSmsEnable()) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "小程序后台验证码服务不支持");
        }
        String code = CharUtil.getRandomNum(6);
        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
        if (!successful) {
            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
        }
        notifyService.notifySmsTemplate(phoneNumber, SmsNotifyType.CAPTCHA, new String[]{code});

        return ResponseUtil.ok();
    }

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
    @ApiImplicitParam(name="body",value = "{username(用户名): xxx, password(密码): xxx,mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="string")
    public Object register(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        // 如果是小程序注册，则必须非空
        // 其他情况，可以为空
        String wxCode = JacksonUtil.parseString(body, "wxCode");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }

        List<EyeUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "用户名已注册");
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
        }

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

        // 给新用户发送注册优惠券
        couponAssignService.assignForRegister(user.getId());

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());
        String refreshToken = UUID.randomUUID().toString();
        TokenCacheManager.addRefreshToken(token,refreshToken,user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("refreshToken",refreshToken);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 请求验证码
     *
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile: xxx, type: xxx }
     * @return
     */
    @PostMapping("captcha")
    @ApiOperation("请求验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="body",value = "mobile(手机号): xxx,type: xxx}",required=true,dataType="string")
    })
    public Object captcha(@LoginUser Integer userId, @RequestBody String body) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String phoneNumber = JacksonUtil.parseString(body, "mobile");
        String captchaType = JacksonUtil.parseString(body, "type");
        if (StringUtils.isEmpty(phoneNumber)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(phoneNumber)) {
            return ResponseUtil.badArgumentValue();
        }
        if (StringUtils.isEmpty(captchaType)) {
            return ResponseUtil.badArgument();
        }

        if (!notifyService.isSmsEnable()) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "小程序后台验证码服务不支持");
        }
        String code = CharUtil.getRandomNum(6);
        boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
        if (!successful) {
            return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时1分钟，不能发送");
        }
        notifyService.notifySmsTemplate(phoneNumber, SmsNotifyType.CAPTCHA, new String[]{code});

        return ResponseUtil.ok();
    }

    /**
     * 账号密码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    @ApiOperation("用户账号密码重置")
    @ApiImplicitParam(name="body",value = "{password(密码): xxx,mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="string")
    public Object reset(@RequestBody String body, HttpServletRequest request) {
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<EyeUser> userList = userService.queryByMobile(mobile);
        EyeUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_MOBILE_UNREGISTERED, "手机号未注册");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号手机号码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetPhone")
    @ApiOperation("用户账号手机号码重置")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{password(密码): xxx,mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="string")
    })
    public Object resetPhone(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }

        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<EyeUser> userList = userService.queryByMobile(mobile);
        EyeUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        user = userService.findById(userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        user.setMobile(mobile);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号信息更新
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("profile")
    @ApiOperation("账号信息更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{password(密码): xxx,mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="shring")
    })
    public Object profile(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String avatar = JacksonUtil.parseString(body, "avatar");
        Byte gender = JacksonUtil.parseByte(body, "gender");
        String nickname = JacksonUtil.parseString(body, "nickname");

        EyeUser user = userService.findById(userId);
        if(!StringUtils.isEmpty(avatar)){
            user.setAvatar(avatar);
        }
        if(gender != null){
            user.setGender(gender);
        }
        if(!StringUtils.isEmpty(nickname)){
            user.setNickname(nickname);
        }

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 微信手机号码绑定
     *
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("bindPhone")
    @ApiOperation("手机号码绑定")
    public Object bindPhone(@LoginUser Integer userId, @RequestBody String body) {
    	if (userId == null) {
            return ResponseUtil.unlogin();
        }
    	EyeUser user = userService.findById(userId);
        String encryptedData = JacksonUtil.parseString(body, "encryptedData");
        String iv = JacksonUtil.parseString(body, "iv");
        WxMaPhoneNumberInfo phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(user.getSessionKey(), encryptedData, iv);
        String phone = phoneNumberInfo.getPhoneNumber();
        user.setMobile(phone);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @PostMapping("bindMobile")
    @ApiOperation("判断验证码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="shring")
    })
    public Object bindMobile(@LoginUser Integer userId,@RequestBody String body){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String code = JacksonUtil.parseString(body, "code");
        String mobile = JacksonUtil.parseString(body, "mobile");
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)){
            return ResponseUtil.badArgument();
        }
        //判断验证码是否正确
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
        if(StringUtils.isEmpty(cachedCaptcha) || !cachedCaptcha.equals(code)){
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
        }
        EyeUser user = userService.findById(userId);
        user.setMobile(mobile);
        if(userService.updateById(user) == 0){
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
    /**
     *注册账号绑定微信
     * @param userId
     * @param wxLoginInfo
     * @return
     */
    @PostMapping("bindWeiXin")
    @ApiOperation("注册账号绑定微信")
    public Object bindWeiXin(@LoginUser Integer userId,@RequestBody WxLoginInfo wxLoginInfo){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        EyeUser user = userService.findById(userId);
        if(user == null){
            return ResponseUtil.badArgument();
        }
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (userInfo == null) {
            return ResponseUtil.badArgument();
        }
        Byte gender = userInfo.getGender();
        String nickName = userInfo.getNickName();
        String openId = userInfo.getOpenId();
        if(gender == 0 ||  StringUtils.isEmpty(nickName) || StringUtils.isEmpty(openId)){
          return ResponseUtil.badArgument();
        }
        user.setGender(gender);
        user.setNickname(nickName);
        user.setWeixinOpenid(openId);
        if(userService.updateById(user) == 0){
            ResponseUtil.updatedDataFailed();
        }

        return  ResponseUtil.ok();
    }

    @PostMapping("logout")
    @ApiOperation("用户退出")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

    @GetMapping("info")
    @ApiOperation("用户信息")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object info(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        EyeUser user = userService.findById(userId);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("nickName", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());

        return ResponseUtil.ok(data);
    }

    /**
     * 检验是否绑定微信
     * @param userId
     * @return
     */
    @GetMapping("checkIsBindWx")
    @ApiOperation("检验是否绑定微信")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object checkIsBindWx(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        EyeUser user = userService.findById(userId);
        if(user.getGender() == 0){
            return false;
        }
        return true;
    }

    /**
     * 检验是否绑定手机
     * @param userId
     * @return
     */
    @GetMapping("checkIsBindMobile")
    @ApiOperation("检验是否绑定手机")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object checkIsBindMobile(@LoginUser Integer userId){
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        EyeUser user = userService.findById(userId);
        if(!StringUtils.isEmpty(user.getMobile())){
            return true;
        }
        return false;
    }

    /**
     *
     * @param userId
     * @param body { code:xxx,mobile:xxx}
     * @return
     */
    @PostMapping("unbindMobile")
    @ApiOperation("解绑手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int"),
            @ApiImplicitParam(name="body",value = "{mobile(手机号): xxx,code(验证码): xxx}",required=true,dataType="shring")
    })
    public Object unbindMobile(@LoginUser Integer userId,@RequestBody String body){
        if(userId == null){
            ResponseUtil.unlogin();
        }

        EyeUser user = userService.findById(userId);
        if(StringUtils.isEmpty(user.getMobile())){
            ResponseUtil.unbindMobile();
        }

        String code = JacksonUtil.parseString(body, "code");
        String mobile = JacksonUtil.parseString(body, "mobile");
        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)){
            return ResponseUtil.badArgument();
        }
        //判断验证码是否正确
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
        if(StringUtils.isEmpty(cachedCaptcha) || !cachedCaptcha.equals(code)){
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
        }

        if(!mobile.equals(user.getMobile())){
            ResponseUtil.mobileError();
        }
        user.setMobile("");
        if(userService.updateById(user) == 0){
            ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @GetMapping("checkToken")
    @ApiOperation("判断用户Token是否过期")
    @ApiImplicitParam(name="userId",value ="用户id",required=true,paramType = "path",dataType="int")
    public Object checkToken(@LoginUser Integer userId){
        if(userId == null){
        return false;
        }
        return true;
    }
}
