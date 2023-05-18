package com.cqtalk.enumerate.file;

public enum FILE_RECOMMEND_ENUM {

    MAX_COUNT(5),
    ;

    private int type;

    FILE_RECOMMEND_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
