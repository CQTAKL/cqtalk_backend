package com.cqtalk.enumerate.entityType;

public enum ENTITY_TYPE_ENUM {

    POST(1),

    FILE(2),

    COMMENT(3),

    FILE_RECOMMEND(4),

    ;

    private int type;

    ENTITY_TYPE_ENUM(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
