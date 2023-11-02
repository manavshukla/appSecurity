package com.security.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
public class ProjectDto {

    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


}
