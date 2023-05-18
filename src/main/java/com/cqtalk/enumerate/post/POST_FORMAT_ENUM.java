package com.cqtalk.enumerate.post;

public enum POST_FORMAT_ENUM {

    MARKDOWN(1),

    RICHTEXT(2),
    ;

    private int type;

    POST_FORMAT_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
