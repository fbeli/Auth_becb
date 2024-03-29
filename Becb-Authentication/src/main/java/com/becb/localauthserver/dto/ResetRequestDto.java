package com.becb.localauthserver.dto;

import lombok.Data;

@Data
public class ResetRequestDto {

    String code;
    String email;
    String userId;
    String name;
    String password;
    int status;
    String message;

}
