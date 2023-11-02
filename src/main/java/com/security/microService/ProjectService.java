package com.security.microService;

import com.security.dto.ProjectDto;
import com.security.dto.TimeSheetDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProjectService {
    private final RestTemplate restTemplate;

    @Value("${api.project.url}")
    private String api;

    @Autowired
    public ProjectService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void createProject(ProjectDto projectDto, HttpServletRequest request, String token) {
        try {

            String authorization = request.getHeader("Authorization");
            String url = api + "/create";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // headers.set(HttpHeaders.AUTHORIZATION, token);
            HttpEntity<ProjectDto> createProject = new HttpEntity<>(projectDto, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, createProject, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getProjectData(int page, int size, String sortBy) {
        try {
            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
            String url = api + "/getAllProjects?page=" + page + "&size=" + size + "&sortBy=" + sortBy;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public String getFilterName(String title) {
        try {
            // http://localhost:8080/project/getAllProjects?page=0&size=3&sortBy=title
            String url = api + "/filter?title=" + title;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void updateProject(ProjectDto projectDto, String id) {
        try {

            String url = api + "/update/" + id;
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ProjectDto> requestEntity = new HttpEntity<>(projectDto, headers);
            restTemplate.put(url, requestEntity);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getProjectById(String id) {
        try {

            String url = api + "/getProject/" + id;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void deleteProject(String id) {
        try {

            String url = api + "/delete/" + id;
            restTemplate.delete(url, new ObjectId(id));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getAllProjectList() {
        try {
//            http://localhost:8081/project/getAllProjectList
            String url = api + "/getAllProjectList";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String getProjectTitle() {
        try {
            String url = api + "/getAllProjectTitle";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
