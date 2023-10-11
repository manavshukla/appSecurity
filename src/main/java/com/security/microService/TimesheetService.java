package com.security.microService;

import com.security.dto.TimeSheetDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class TimesheetService {
    private final RestTemplate restTemplate;

    @Value("${api.timesheet.url}")
    private String api;

    @Autowired
    public TimesheetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createTimesheet(TimeSheetDto timeSheetDto, HttpServletRequest request, String token) {
        try {

            String authorization = request.getHeader("Authorization");
            String url = api;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, token);
            HttpEntity<TimeSheetDto> createTime = new HttpEntity<>(timeSheetDto, headers);
            ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, createTime, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getLeaveData(int page, int pageSize, String sortBy) {
        try {

            String url = api + "?page=" + page + "&pageSize=" + pageSize + "&sortBy" + sortBy;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void updateTimesheet(TimeSheetDto timeSheetDto, String id) {
        try {

            String url = api + "/" + id;
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TimeSheetDto> requestEntity = new HttpEntity<>(timeSheetDto, headers);
            restTemplate.put(url, requestEntity);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getTimesheetById(String id) {
        try {

            String url = api + "/" + id;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void deleteLeave(String id) {
        try {

            String url = api + "/" + id;
            restTemplate.delete(url, new ObjectId(id));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
