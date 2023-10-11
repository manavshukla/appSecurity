package com.security.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmployeeFileDto {

    private MultipartFile photo;
    private MultipartFile adharCardFront;
    private MultipartFile adharCardBack;
    private MultipartFile panCard;
    private MultipartFile std10;
    private MultipartFile std12;
    private MultipartFile offerLetter;
    private MultipartFile degree;
    private MultipartFile experienceLetter;
}
