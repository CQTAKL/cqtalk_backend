package com.cqtalk.aop;


import java.lang.annotation.*;

/**
 * 需要用户身份
 * TODO：暂时没有测试
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserRequired {

    // 具体说明见：ENTITY_TYPE_ENUM 这个实体类
    int entity() default 0;

    String name() default "id";

}
