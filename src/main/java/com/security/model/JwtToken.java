package com.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "jwtToken")
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {


    @Id
    private String id;
    private String userId;

    private String token;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int status;


    public JwtToken(String token) {
        this.token = token;
    }
}
