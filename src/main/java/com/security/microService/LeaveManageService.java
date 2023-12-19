package com.security.microService;

import com.security.dto.CreateLeaveDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class LeaveManageService {
    @Value("${api.leave.url}")
    private String api;

//    @Value("${api.leave.port}")
//    private String port;
//
//    @Value("${api.leave.api}")
//    private String api;

    //    private final EndPointEnum endPointEnum;
//    String api = "";
    private final RestTemplate restTemplate;
    //   api =EndPointEnum.(player.getPosition()).

// /   getLabel();

    @Autowired
    public LeaveManageService(RestTemplate restTemplate) {
        // this.endPointEnum = endPointEnum;
        this.restTemplate = restTemplate;

    }

    public String getLeaveData(int page, int pageSize, String sortBy) {
        try {

            String url = api + "getAllLeaves?page=" + page + "&pageSize=" + pageSize + "&sortBy=" + sortBy;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String getLeaveByEmployeeId(int page, int pageSize, String employeeId, String sortBy) {
        try {

            String url = api + "emp/" + employeeId + "?page=" + page + "&pageSize=" + pageSize + "&sortBy=" + sortBy;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ResponseEntity<?> createLeave(CreateLeaveDto createLeaveDto, HttpServletRequest request, String token) {
        try {
            String msg = "";
            String authorization = request.getHeader("Authorization");
            String url = api + "createLeave";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, token);
            HttpEntity<CreateLeaveDto> createEntity = new HttpEntity<>(createLeaveDto, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, createEntity, String.class);
            return responseEntity;
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    public void updateLeave(CreateLeaveDto createLeaveDto, String id) {
        try {

            String url = api + "updateLeave/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateLeaveDto> requestEntity = new HttpEntity<>(createLeaveDto, headers);
            restTemplate.put(url, requestEntity, CreateLeaveDto.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String deleteLeave(String id) {
        try {

            String url = api + "deleteLeave/" + id;
            restTemplate.delete(url, new ObjectId(id));
            return url;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void approveLeave(String id, CreateLeaveDto createLeaveDto) {
        try {

            String url = api + id + "/approve";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateLeaveDto> requestEntity = new HttpEntity<>(createLeaveDto, headers);
            restTemplate.put(url, requestEntity, CreateLeaveDto.class);

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void rejectLeave(String id, CreateLeaveDto createLeaveDto) {
        try {
            String url = api + id + "/reject";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateLeaveDto> requestEntity = new HttpEntity<>(createLeaveDto, headers);
            restTemplate.put(url, requestEntity, CreateLeaveDto.class);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getLeaveById(String id) {
        try {

            String url = api + "leave/" + id;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String searchByName(String name) {
        try {
            String url = api + "search";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
