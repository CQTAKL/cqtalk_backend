package com.cqtalk.enumerate.file;


public enum FILE_SIZE_ENUM {

    LEVEL_OF_B("B"),

    LEVEL_OF_KB("KB"),

    LEVEL_OF_MB("MB"),

    LEVEL_OF_GB("GB"),

    LEVEL_OD_TB("TB"),

    ;

    private String level;

    FILE_SIZE_ENUM(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


}
