package com.cqtalk.enumerate.user;

public enum USER_IDENTITY_ENUM {

    NORMAL(1),
    SUPER_ADMIN(2),
    ;

    private int type;

    USER_IDENTITY_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
