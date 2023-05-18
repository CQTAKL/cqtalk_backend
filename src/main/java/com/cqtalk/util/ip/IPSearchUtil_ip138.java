package com.cqtalk.util.ip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// 通过ip地址查找属地 备用方案
@Component
public class IPSearchUtil_ip138 {

    private static final Logger logger = LoggerFactory.getLogger(IPSearchUtil_ip138.class);

    /**
     * txt|jsonp|xml
     */
    public static String DATATYPE="jsonp";

    public static String get(String urlString, String token) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("token",token);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder builder = new StringBuilder();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                for (String s = br.readLine(); s != null; s = br
                        .readLine()) {
                    builder.append(s);
                }
                br.close();
                return builder.toString();
            } else if(responseCode == 202) {
                logger.error("可能是无效token, 余额不足, 请求格式错误");
            }
        } catch (IOException e) {
            logger.error("ip_138获取ip地址错误");
        }
        return null;
    }
    public static String queryIP(String ip){
        String url="https://api.ip138.com/ip/?ip="+ip+"&datatype="+DATATYPE;
        String token="fdacbce934db7c373d1a24c5bd897ce2";
        return get(url,token);
    }

    /*public static String JSONtoJavaString(String content) {
        String province =  IPUtil_ip138.getProvince(content);
        String city =  IPUtil_ip138.getCity(content);

    }*/

    // 获取json字符串对应的省级
    public static String getProvince(String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray data = jsonObject.getJSONArray("data");
        // String province = jsonObject.getJSONObject("data").getString("code");
        return (String) data.get(1);
    }

    // 获取json字符串对应的省级
    public static String getCity(String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray data = jsonObject.getJSONArray("data");
        // String province = jsonObject.getJSONObject("data").getString("code");
        return (String) data.get(2);
    }

    /*public static void main(String[] args) {
        IPUtil_ip138 ip = new IPUtil_ip138();
        *//*System.out.println(ip.queryIP("121.43.57.231"));*//*
        String content = "{\"ret\":\"ok\",\"ip\":\"121.43.57.231\",\"data\":[\"中国\",\"\",\"\",\"\",\"阿里云\",\"310000\",\"0571\"]}";
        System.out.println(ip.getProvince(content));
        System.out.println(ip.getCity(content));
    }*/

    // {"ret":"ok","ip":"121.43.57.231","data":["中国","浙江","杭州","","阿里云","310000","0571"]}

}

//以下是使用示例：
//QueryHelper.queryIP("8.8.8.8");
