package com.cqtalk.util.user.authentication;

import java.util.List;

public class IdentityVerifyUtil {

    public static boolean verify(List<Integer> types, int needType) {
        int flag = 0;
        for(int type: types) {
            if(type == needType) {
                flag = 1;
            }
        }
        return flag != 0;
    }


}
