package com.eye.common.service;

import com.eye.common.dto.RefreshTokenInfo;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCacheManager {
    private static Map<String, RefreshTokenInfo> refreshTokenInfoMap = new ConcurrentHashMap<>();

    public static boolean addRefreshToken(String token, String refreshToken, Integer userId){

        RefreshTokenInfo refreshTokenInfo = new RefreshTokenInfo();
        refreshTokenInfo.setRefreshToken(refreshToken);
        refreshTokenInfo.setUserId(userId);
        refreshTokenInfo.setExpireTime(LocalDateTime.now().plusDays((long)7));

        refreshTokenInfoMap.put(token,refreshTokenInfo);
        return true;
    }


    public static RefreshTokenInfo getRefreshTokenInfo(String token) {
        RefreshTokenInfo refreshTokenInfo = refreshTokenInfoMap.get(token);
        return refreshTokenInfo;
    }
}
