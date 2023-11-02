package com.security.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TeamMaster {


    private String id;
    private String name;
    private String tlId;
    private Date createDate;
    private LocalDate updatedDate;
    private Integer status;

}
