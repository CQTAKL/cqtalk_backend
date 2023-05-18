package com.cqtalk.controller;

import com.cqtalk.entity.search.PostSearchInfo;
import com.cqtalk.entity.util.IPVO;
import com.cqtalk.util.es.ElasticSearchUtil;
import com.cqtalk.util.ip.IPUtil;
import com.cqtalk.util.returnObject.ObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private ElasticSearchUtil esUtil;

    @GetMapping("/test")
    public void t() {
        System.out.println("111测试1111");
    }

    @PostMapping("/test")
    public void test() {
        System.out.println("测试成功");
    }

    /*@GetMapping("/getIP")
    public ObjectResult<IPVO> getIP() throws ExecutionException, InterruptedException {
        String province = ipUtil.findProvince();
        String city = ipUtil.findCity();
        IPVO ipvo = new IPVO();
        ipvo.setCity(city);
        ipvo.setProvince(province);
        return ObjectResult.success(ipvo);
    }*/

    @GetMapping("/testES")
    public ObjectResult<List<PostSearchInfo>> testES() throws IOException {
        List<PostSearchInfo> postSearchInfos = esUtil.searchPosts("post", "张创琦", 0);
        return ObjectResult.success(postSearchInfos);
    }

}
