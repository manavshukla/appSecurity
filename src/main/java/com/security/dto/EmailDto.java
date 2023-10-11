package com.security.dto;


import lombok.Data;

@Data
public class EmailDto {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
