package com.cqtalk.enumerate.file;

public enum FILE_TYPE_ENUM {

    FILE(1),
    PICTURE(2),

    ;

    private int type;

    FILE_TYPE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
