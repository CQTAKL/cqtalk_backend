package com.cqtalk.util.encrypt;

import java.util.UUID;

// 生成随机字符串
public class UUIDUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
