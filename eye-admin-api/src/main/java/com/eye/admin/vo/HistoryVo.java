package com.eye.admin.vo;

import com.eye.db.domain.EyeSearchHistory;

public class HistoryVo extends EyeSearchHistory {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
