package com.cqtalk.util.kafka;

import com.alibaba.fastjson.JSONObject;
import com.cqtalk.entity.queue.Event;
import com.cqtalk.enumerate.notice.EVENT_ENUM;
import com.cqtalk.service.toc.queue.QueueService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private QueueService queueService;

    @KafkaListener(topics = {"post", "file"})
    public void handleMessage(ConsumerRecord record) {
        if(record == null || record.value() == null) {
            logger.error("消息的内容为空");
            return;
        }
        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if(event == null) {
            logger.error("消息格式有误");
        }
        System.out.println(record.value());
    }


}
