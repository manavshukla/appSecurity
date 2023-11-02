package com.security.microService;


import com.security.dto.AssignProjectDto;
import com.security.dto.TeamMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class TeamMasterService {

    @Value("${api.teamMaster.url}")
    private String api;

    private final RestTemplate restTemplate;


    @Autowired
    public TeamMasterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void create(TeamMaster teamMaster, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "/create";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            HttpEntity<TeamMaster> create = new HttpEntity<>(teamMaster, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, create, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getAllTeamMaster(HttpServletRequest request, int page, int size) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "getAllTeamMaster?page=" + page + "&size=" + size;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void updateTeamMaster(TeamMaster teamMaster, String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "update/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TeamMaster> requestEntity = new HttpEntity<>(teamMaster, headers);
            restTemplate.put(url, requestEntity);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getTeamMasterById(String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");

            String url = api + "getTeamMaster/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            return restTemplate.getForObject(url, String.class, headers);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void deleteTeamMaster(String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");

            String url = api + "delete/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.delete(url, id, headers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
