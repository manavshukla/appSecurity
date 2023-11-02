package com.security.microServiceController;

import com.security.dto.CreateLeaveDto;
import com.security.dto.EmailDto;
import com.security.microService.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin("*")
@RequestMapping("/send")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public ResponseEntity<?> createLeave(@RequestBody EmailDto emailDto, String token, HttpServletRequest request) {
        try {
            emailService.sendMail(emailDto, request);
            return ResponseEntity.ok().body("\"Email Send Successfully\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not added\"");
        }
    }
}
