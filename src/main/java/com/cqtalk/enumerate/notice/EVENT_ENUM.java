package com.cqtalk.enumerate.notice;

public enum EVENT_ENUM {

    SYSTEM_POST("post"),

    SYSTEM_FILE("file"),
    ;

    private String type;

    EVENT_ENUM(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
