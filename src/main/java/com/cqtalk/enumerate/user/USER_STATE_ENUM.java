package com.cqtalk.enumerate.user;

public enum USER_STATE_ENUM {

    NORMAL(1),
    BANNED(2),
    WARN(3),
    ;

    private int type;

    USER_STATE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
