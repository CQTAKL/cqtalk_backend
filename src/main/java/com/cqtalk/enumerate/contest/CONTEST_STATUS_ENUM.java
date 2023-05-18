package com.cqtalk.enumerate.contest;

public enum CONTEST_STATUS_ENUM {

    NORMAL(1),

    ABNORMAL(2),

    ;

    private int type;

    CONTEST_STATUS_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
