package com.cqtalk.util.returnObject;

import lombok.Data;

@Data
public class DescribeException extends RuntimeException {

    private String code;
    private String msg;

    /**
     * 继承exception，加入错误状态值
     *
     * @param errorCode
     */
    public DescribeException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    /**
     * 自定义错误信息
     */
    public DescribeException(String code, String msg) {
        super(msg);
        this.code = code;
    }


}
