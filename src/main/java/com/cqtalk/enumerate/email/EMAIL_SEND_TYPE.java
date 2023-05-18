package com.cqtalk.enumerate.email;

public enum EMAIL_SEND_TYPE {

    REGISTER(1),

    MODIFY(2),
    ;

    private int type;

    EMAIL_SEND_TYPE(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
