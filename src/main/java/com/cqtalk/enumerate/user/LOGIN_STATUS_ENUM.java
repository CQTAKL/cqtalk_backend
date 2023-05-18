package com.cqtalk.enumerate.user;

public enum LOGIN_STATUS_ENUM {

    LOGIN_SUCCESS(1),

    LOGIN_FAILURE(2),

    LOGOUT_SUCCESS(3),
    ;

    private int type;

    LOGIN_STATUS_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
