package com.cqtalk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 只有超级管理员才有资格操作
@Target(ElementType.METHOD) // 注解在方法上
@Retention(RetentionPolicy.RUNTIME) // 在代码运行时起作用
public @interface SuperAdminOperate {



}
