package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddColumnDO {

    private String title;

    private String briefIntro;

    private String content;

    private Date createTime;

}
