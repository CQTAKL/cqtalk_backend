package com.cqtalk.enumerate.user;

public enum REMEMBER_EXPIRED_ENUM {

    // 默认状态的登录凭证超时时间，秒
    DEFAULT_EXPIRED_SECONDS(3600 * 24 * 3), // 3天

    // 记住状态下的登录凭证超时时间，秒
    REMEMBER_EXPIRED_SECONDS(3600 * 24 * 7), // 7天

    ;


    private int type;

    REMEMBER_EXPIRED_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
