package com.cqtalk.controller.toc;

import com.cqtalk.service.toc.queue.QueueService;
import com.cqtalk.util.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// 通知
@RestController
@RequestMapping("/event")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(FileFunctionController.class);

    @Autowired
    private QueueService queueService;

    @Autowired
    private JWTUtil jwtUtil;

    /*@LoginRequired
    // @ 仅限超级管理员操作
    @ApiOperation(value = "添加系统通知", notes = "尚未测试")
    @PostMapping("/system/add")
    public ObjectResult<SystemInfoVO> addSystemNotice(AddSystemNoticeDTO addSystemNoticeDTO) {
        queueService.addSystemNotice(addSystemNoticeDTO);
        return ObjectResult.success();
    }

    // 路径上的id为用户user的id
    @LoginRequired
    @ApiOperation(value = "返回系统通知(未读)", notes = "尚未测试")
    @PostMapping("/system/noRead/{id}")
    public ObjectResult<SystemInfoVO> systemNotice1(@PathVariable int id) {
        queueService.getSystemNoticeNoRead();
        return ObjectResult.success();
    }

    @LoginRequired
    @ApiOperation(value = "返回系统通知(已读)", notes = "尚未测试")
    @PostMapping("/system/read/{id}")
    public ObjectResult<SystemInfoVO> systemNotice2(@PathVariable int id) {

        return ObjectResult.success();
    }

    @LoginRequired
    @ApiOperation(value = "返回关注的专栏的信息(未读)", notes = "尚未测试")
    @PostMapping("/column/noRead")
    public ObjectResult<ColumnNoticeVO> columnNotice1() {
        queueService.columnNoticeNoRead();
        return ObjectResult.success();
    }*/

}
