package com.cqtalk.entity.queue.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoVO {

    private List<SystemInfoSingleVO> systemInfoList;

}
