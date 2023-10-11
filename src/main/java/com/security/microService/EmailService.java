package com.security.microService;

import com.security.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


@Service
public class EmailService {

    @Value("${api.email.url}")
    private String api;

    @Autowired
    private final RestTemplate restTemplate;

    public EmailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void sendMail(EmailDto emailDto, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            HttpEntity<EmailDto> createEntity = new HttpEntity<>(emailDto, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, createEntity, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
