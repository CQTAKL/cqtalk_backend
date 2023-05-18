package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMajorDO {

    private Integer id;

    private Integer major;

    private Integer school;

    private Integer graduateMajor;

    private Integer graduateSchool;

}
