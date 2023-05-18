package com.cqtalk.util.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// 对外开发的包
@Component
public class IPUtil {

    @Autowired
    private FindIPUtil findIPUtil;

    @Autowired
    private IPSearchUtil_ip2region ipSearchUtil_ip2region;

    @Autowired
    private IPSearchUtil_ip138 ipSearchUtil_ip138;

    // 满足ip_138格式查询的ip属地格式
    private static String ipJSON138;

    // 返回用户的ip地址
    public static String findIP() throws ExecutionException, InterruptedException {
        return FindIPUtil.get();
    }

    // 返回用户所在的省份
    public String findProvince() throws ExecutionException, InterruptedException {
        String ip = IPUtil.findIP();
        String province1 = ipSearchUtil_ip2region.findProvince(ip);
        if(!province1.equals("0")) {
            return province1;
        } else {
            ipJSON138 = IPSearchUtil_ip138.queryIP(ip);
            return IPSearchUtil_ip138.getProvince(ipJSON138);
        }
    }

    // 返回用户所在的城市
    public String findCity() throws ExecutionException, InterruptedException {
        String ip = IPUtil.findIP();
        String city1 = ipSearchUtil_ip2region.findCity(ip);
        if(!city1.equals("0")) {
            return city1;
        } else {
            return IPSearchUtil_ip138.getCity(ipJSON138);
        }
    }

//    public Integer getProvinceId() {
//
//    }

}
