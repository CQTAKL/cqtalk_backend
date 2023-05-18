package com.cqtalk.util.user.info;

import com.cqtalk.entity.user.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息, 用于代替session对象.
 */
@Component
public class HostHolder {

    private ThreadLocal<Integer> userIds = new ThreadLocal<>();

    public void setUserId(Integer id) {
        userIds.set(id);
    }

    public Integer getUserId() {
        return userIds.get();
    }

    public void clear() {
        userIds.remove();
    }

}
