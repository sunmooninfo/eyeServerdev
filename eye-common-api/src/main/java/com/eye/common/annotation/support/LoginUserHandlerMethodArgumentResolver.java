package com.eye.common.annotation.support;

import com.eye.common.annotation.LoginUser;
import com.eye.common.dto.RefreshTokenInfo;
import com.eye.common.service.TokenCacheManager;
import com.eye.common.service.UserTokenManager;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;


public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGIN_TOKEN_KEY = "X-Eye-Token";
    public static final String REFRESH_TOKEN_KEY = "X-Eye-Refresh_Token";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Integer.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {

        HttpServletResponse response = request.getNativeResponse(HttpServletResponse.class);
//        return new Integer(1);
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        String refreshToken = request.getHeader(REFRESH_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return null;
        }
        RefreshTokenInfo refreshTokenInfo = TokenCacheManager.getRefreshTokenInfo(token);
        String refreshToken1 = null;
        if(refreshTokenInfo != null){
            refreshToken1 = refreshTokenInfo.getRefreshToken();
        }
        Integer userId = UserTokenManager.getUserId(token);
        if(userId == null ){
            //判断刷新token是否过期
          if(refreshToken == null || refreshToken.isEmpty() || refreshToken1 == null || refreshToken1.isEmpty()){
              return null;
          }
          if(!refreshToken.equals(refreshToken1)){
              return null;
          }
          if(refreshTokenInfo.getExpireTime().isBefore(LocalDateTime.now())){
             return null;
          }
          //刷新token
            String newToken = UserTokenManager.generateToken(refreshTokenInfo.getUserId());
            String newRefreshToken = UUID.randomUUID().toString();
            TokenCacheManager.addRefreshToken(newToken,newRefreshToken,refreshTokenInfo.getUserId());
            response.setHeader(LOGIN_TOKEN_KEY,newToken);
            response.setHeader(REFRESH_TOKEN_KEY,newRefreshToken);
            return UserTokenManager.getUserId(newToken);
        }
        return UserTokenManager.getUserId(token);
    }
}
