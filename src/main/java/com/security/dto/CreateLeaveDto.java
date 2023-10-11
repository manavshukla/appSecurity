package com.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonFormat
public class CreateLeaveDto {

    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveTypeId;

    private String leaveType;
    private String description;
    private Integer numberOfDays;
    private boolean fullDay;
    private String leaveStatus;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Integer totalAvailableLeaves;
}
