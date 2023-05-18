package com.cqtalk.enumerate.post;

public enum POST_STATE_ENUM {

    NORMAL(1),

    DELETED(2),

    BANNED(3)
    ;

    private int type;

    POST_STATE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
