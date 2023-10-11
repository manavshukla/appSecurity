package com.security.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSheetDto {

    private String timesheetId;
    private String employeeId;
    private String workDoneOn;
    private Float workTime;
    private String projectType;
    private String description;
    private LocalDateTime updated_on;
}
