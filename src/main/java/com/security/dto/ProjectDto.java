package com.security.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


}
