package com.security.microService;

import com.security.dto.AssignProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class AssignProjectService {


    private final RestTemplate restTemplate;

    @Value("${api.assignProject.url}")
    private String api;

    @Autowired
    public AssignProjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void create(AssignProjectDto assignProjectDto, HttpServletRequest request) {
        try {

            String authorization = request.getHeader("Authorization");
            String url = api + "/create";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            HttpEntity<AssignProjectDto> create = new HttpEntity<>(assignProjectDto, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, create, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getAllAssignProjects(HttpServletRequest request, int page, int size) {
        try {
            String authorization = request.getHeader("Authorization");
            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
            String url = api + "/getAllAssignProjects?page=" + page + "&size=" + size;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


//    public String getFilterProjectByProjectId(String title, HttpServletRequest request) {
//        try {
//            String authorization = request.getHeader("Authorization");
//            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
//            String url = api + "/filter?title=" + title;
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set(HttpHeaders.AUTHORIZATION, authorization);
//            return restTemplate.getForObject(url, String.class);
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }

    public void updateAssignProject(AssignProjectDto assignProjectDto, String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "/update/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AssignProjectDto> requestEntity = new HttpEntity<>(assignProjectDto, headers);
            restTemplate.put(url, requestEntity);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getAssignProjectById(String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "/getAssignProjects/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            return restTemplate.getForObject(url, String.class, headers);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void deleteProject(String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            String url = api + "/delete/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            restTemplate.delete(url, id, headers);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getFilterProjectByProjectId(String id, HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
            String url = api + "/filter-project/" + id;
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            return restTemplate.getForObject(url, String.class, headers);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public String getFilterProjectByEmpId(String id, HttpServletRequest request) {
        try {
//            String authorization = request.getHeader("Authorization");
            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
            String url = api + "/filter-emp/" + id;
            HttpHeaders headers = new HttpHeaders();
//            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            headers.setContentType(MediaType.APPLICATION_JSON);
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


}
