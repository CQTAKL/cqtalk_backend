package com.cqtalk.service.toc.queue;

import com.cqtalk.entity.queue.AddSystemNoticeDTO;
import com.cqtalk.entity.queue.ColumnNoticeVO;
import com.cqtalk.entity.queue.SystemInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface QueueService {

    void addSystemNotice(AddSystemNoticeDTO addSystemNoticeDTO);

    SystemInfoVO getSystemNoticeNoRead(Integer userId);

    ColumnNoticeVO columnNoticeNoRead(Integer userId);


}
