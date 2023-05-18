package com.cqtalk.enumerate.user;

public enum USER_TYPE_ENUM {

    // 管理整个竞赛部分的插入、删除等等工作
    CONTEST_ADMINISTRATOR_OVERALL(20),

    SUPER_ADMINISTRATOR(2),

    GENERAL(1),

    ;

    private int type;

    USER_TYPE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
