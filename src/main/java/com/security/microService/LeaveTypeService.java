package com.security.microService;

import com.security.dto.CreateLeaveDto;
import com.security.dto.CreateLeaveTypeDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class LeaveTypeService {

    private final RestTemplate restTemplate;


    @Value("${api.leaveType.url}")
    private String api;

    @Autowired
    public LeaveTypeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getLeaveTypeData() {
        try {

            String url = api + "/getAllLeaveTypes";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String getLeaveTypeName() {
        try {

            String url = api + "/leaveTypeName";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void createLeaveType(CreateLeaveTypeDto createLeaveTypeDto, HttpServletRequest request, String token) {
        try {

            String authorization = request.getHeader("Authorization");
            String url = api + "createLeaveType";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            Integer maxDaysAllowed = createLeaveTypeDto.getMaxDaysAllowed();
            createLeaveTypeDto.setMaxDaysAllowed(maxDaysAllowed);
            HttpEntity<CreateLeaveTypeDto> createEntity = new HttpEntity<>(createLeaveTypeDto, headers);
            restTemplate.exchange(url, HttpMethod.POST, createEntity, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void updateLeave(CreateLeaveTypeDto createLeaveTypeDto, String id) {
        try {

            String url = api + "updateLeaveType/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateLeaveTypeDto> requestEntity = new HttpEntity<>(createLeaveTypeDto, headers);
            restTemplate.put(url, requestEntity);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public String deleteLeaveType(String id) {
        try {

            String url = api + "deleteLeaveType/" + id;
            restTemplate.delete(url, new ObjectId(id));
            return url;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String getLeaveTypeById(String id) {
        try {

            String url = api + "leaveType/" + id;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
