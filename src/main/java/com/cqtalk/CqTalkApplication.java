package com.cqtalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

//@Slf4j
@SpringBootApplication(scanBasePackages = "com.cqtalk.*")
//@ServletComponentScan("com.cqtalk.dao")
//@ComponentScan(basePackages = {"com.google.code"})
public class CqTalkApplication {

    @PostConstruct
    public void init() {
        // 解决netty启动冲突问题
        // see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(CqTalkApplication.class, args);
//        log.info("项目启动成功");
    }

}
