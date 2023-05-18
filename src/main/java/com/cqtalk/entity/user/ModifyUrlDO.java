package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUrlDO {

    private Integer id;

    private String headerUrl;

    private String lastHeaderUrl;

}
