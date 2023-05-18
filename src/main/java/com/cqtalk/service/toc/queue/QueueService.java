package com.cqtalk.service.toc.queue;

import com.cqtalk.entity.queue.dto.AddSystemNoticeDTO;
import com.cqtalk.entity.queue.vo.ColumnNoticeVO;
import com.cqtalk.entity.queue.vo.SystemInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface QueueService {

    void addSystemNotice(AddSystemNoticeDTO addSystemNoticeDTO);

    SystemInfoVO getSystemNoticeNoRead(Integer userId);

    ColumnNoticeVO columnNoticeNoRead(Integer userId);


}
