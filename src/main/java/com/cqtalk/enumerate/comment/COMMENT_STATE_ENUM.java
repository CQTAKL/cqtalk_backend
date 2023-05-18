package com.cqtalk.enumerate.comment;

public enum COMMENT_STATE_ENUM {

    NORMAL(1),

    DELETE(2),
    ;

    private int type;

    COMMENT_STATE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
