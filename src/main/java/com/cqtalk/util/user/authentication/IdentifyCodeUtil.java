package com.cqtalk.util.user.authentication;

import com.alibaba.fastjson.JSONObject;
import com.cqtalk.entity.user.vo.UserIdentifyVO;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IdentifyCodeUtil {

    public UserIdentifyVO identifyUser(String realName, String identifyCode) {
        String host = "https://idenauthen.market.alicloudapi.com";
        String path = "/idenAuthentication";
        String method = "POST";
        String appcode = "a4f7595ba2734de5a84c9ac89432efc5";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("idNo", identifyCode);
        bodys.put("name", realName);
        UserIdentifyVO u = new UserIdentifyVO();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            // System.out.println(response.toString());
            // 获取response的body
            // System.out.println(EntityUtils.toString(response.getEntity()));
            // 返回示例：
            // {"name":"张创琦","idNo":"130428200301202911","respMessage":"身份证信息匹配",
            // "respCode":"0000","province":"河北省","city":"邯郸市","county":"肥乡县","birthday":"20030120","sex":"M","age":"20"}
            String strObj = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(strObj);
            String r = jsonObject.getString("respMessage");
            u.setTrueIdentify(r.equals("身份证信息匹配")); // 满足题意为true, 否则为false.
            u.setResponseInfo(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

}
