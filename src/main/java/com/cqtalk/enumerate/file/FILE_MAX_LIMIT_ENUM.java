package com.cqtalk.enumerate.file;

public enum FILE_MAX_LIMIT_ENUM {

    MAX_SIZE(205 * 1024 * 1024)
    ;

    private int type;

    FILE_MAX_LIMIT_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
