package com.cqtalk.util.returnObject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zhangchuangqi
 * @param <T>
 */
public class ObjectResult<T> implements Serializable {

    public static final long serialVersionUID = -9146805371831100892L;
    public static final ObjectResult SUCCESS = new ObjectResult("200", "操作成功");
    public static final ObjectResult ERROR = new ObjectResult("400", "操作失败");
    public static final ObjectResult EXCEPTION = new ObjectResult("500", "服务器异常");

    @ApiModelProperty(
            value = "状态码",
            required = false,
            example = "200"
    )
    private String code;

    @ApiModelProperty(
            value = "错误信息",
            required = false,
            example = "操作成功"
    )
    private String msg;

    @ApiModelProperty(
            value = "返回数据",
            required = false
    )
    private T data;

    public ObjectResult(String code, String msg) {
        this(code, msg, null);
    }

    public ObjectResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static boolean isSuccess(ObjectResult<?> result) {
        return result != null && SUCCESS.code.equals(result.getCode());
    }

    public static boolean isFailed(ObjectResult<?> result) {
        return !isSuccess(result);
    }

    @Deprecated
    public ObjectResult<T> copyThis(T data) {
        return new ObjectResult(this.code, this.msg, data);
    }

    public static <T> ObjectResult<T> error(String msg) {
        return new ObjectResult(ERROR.code, msg);
    }

    public static <T> ObjectResult<T> error(String code, String msg) {
        return new ObjectResult(code, msg, (Object)null);
    }

    public static <T> ObjectResult<T> success(String msg) {
        return new ObjectResult(SUCCESS.code, msg);
    }

    public static <T> ObjectResult<T> success(T data) {
        return new ObjectResult(SUCCESS.code, SUCCESS.msg, data);
    }

    public static <T> ObjectResult<T> success(String msg, T data) {
        return new ObjectResult(SUCCESS.code, msg, data);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public ObjectResult() {
    }

    @Override
    public String toString() {
        return "ObjectResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}