package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDTO {

    private Integer userId;

    private String loginTicket;

    private Integer expiredTime;

    private String jwtToken;

}
