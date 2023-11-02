package com.security.dto;

import lombok.Data;

@Data
public class AccessDto {

    private String token;
    private String name;
    private String userId;
    private String roleId;
    private String roleName;
    private String employeeName;
}
