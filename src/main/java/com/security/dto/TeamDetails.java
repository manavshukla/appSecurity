package com.security.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TeamDetails {


    private String id;
    private String teamId;
    private String empId;
    private Date createDate;
    private LocalDate updatedDate;
    private Integer status;

}
