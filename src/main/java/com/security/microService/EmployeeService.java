package com.security.microService;


import com.security.dto.EmployeeDto;
import com.security.dto.EmployeeFileDto;
import com.security.dto.SystemList;
import com.security.model.User;
import com.security.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class EmployeeService {

    @Value("${api.employee.url}")
    private String api;
    private final RestTemplate restTemplate;

    @Autowired
    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createEmployee(EmployeeDto employeeDto, HttpServletRequest request, EmployeeFileDto employeeFileDto
            , SystemList systemList
    ) {
        try {

            System.out.println("in the service");
//            System.out.println(employeeDto.getEmployeeId());

//            System.out.println(employeeDto.getEmployeeId());
            String url = api;
            HttpHeaders headers = new HttpHeaders();
            String authorization = request.getHeader("Authorization");
//                HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("name", new HttpEntity<>(employeeDto.getName()));
            body.add("userId", new HttpEntity<>(employeeDto.getUserId()));
            body.add("emailAddress", new HttpEntity<>(employeeDto.getEmailAddress()));
            body.add("phoneNo", new HttpEntity<>(employeeDto.getPhoneNo()));
            body.add("emergencyContactNo", new HttpEntity<>(employeeDto.getEmergencyContactNo()));
            body.add("workingWith", new HttpEntity<>(employeeDto.getWorkingWith()));
            body.add("reportTo", new HttpEntity<>(employeeDto.getReportTo()));
            body.add("experience", new HttpEntity<>(employeeDto.getExperience()));
            body.add("joiningDate", new HttpEntity<>(employeeDto.getJoiningDate()));
            body.add("availableLeaves", new HttpEntity<>(employeeDto.getAvailableLeaves()));
            body.add("salary", new HttpEntity<>(employeeDto.getSalary()));
            body.add("nextAppraisalDate", new HttpEntity<>(employeeDto.getNextAppraisalDate()));
            body.add("dateOfBirth", new HttpEntity<>(employeeDto.getDateOfBirth()));
            body.add("bloodGroup", new HttpEntity<>(employeeDto.getBloodGroup()));
            body.add("maritalStatus", new HttpEntity<>(employeeDto.getMaritalStatus()));
            body.add("gender", new HttpEntity<>(employeeDto.getGender()));
            body.add("address", new HttpEntity<>(employeeDto.getAddress()));
            body.add("city", new HttpEntity<>(employeeDto.getCity()));
            body.add("state", new HttpEntity<>(employeeDto.getState()));
            body.add("interestedTechnologies", new HttpEntity<>(employeeDto.getInterestedTechnologies()));
            body.add("bankAccountNo", new HttpEntity<>(employeeDto.getBankAccountNo()));
            body.add("ifscCode", new HttpEntity<>(employeeDto.getIfscCode()));
            body.add("role", new HttpEntity<>(employeeDto.getRole()));
            body.add("designation", new HttpEntity<>(employeeDto.getDesignation()));
            body.add("currentProjectName", new HttpEntity<>(employeeDto.getCurrentProjectName()));
            body.add("emergencyContactPerson", new HttpEntity<>(employeeDto.getEmergencyContactPerson()));

            /*System Details*/
            body.add("pcName", new HttpEntity<>(systemList.getPcName()));
            body.add("ram", new HttpEntity<>(systemList.getRam()));
            body.add("hdd", new HttpEntity<>(systemList.getHdd()));
            body.add("comment", new HttpEntity<>(systemList.getComment()));


            /*File Add in body*/
            body.add("photo", new ByteArrayResource(employeeFileDto.getPhoto().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getPhoto().getOriginalFilename();
                }
            });
            body.add("adharCardFront", new ByteArrayResource(employeeFileDto.getAdharCardFront().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getAdharCardFront().getOriginalFilename();
                }
            });

            body.add("adharCardBack", new ByteArrayResource(employeeFileDto.getAdharCardBack().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getAdharCardBack().getOriginalFilename();
                }
            });

            body.add("degree", new ByteArrayResource(employeeFileDto.getDegree().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getDegree().getOriginalFilename();
                }
            });

            body.add("panCard", new ByteArrayResource(employeeFileDto.getPanCard().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getPanCard().getOriginalFilename();
                }
            });

            body.add("std10", new ByteArrayResource(employeeFileDto.getStd10().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getStd10().getOriginalFilename();
                }
            });

            body.add("std12", new ByteArrayResource(employeeFileDto.getStd12().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getStd12().getOriginalFilename();
                }
            });

            body.add("experienceLetter", new ByteArrayResource(employeeFileDto.getExperienceLetter().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getExperienceLetter().getOriginalFilename();
                }
            });

            body.add("offerLetter", new ByteArrayResource(employeeFileDto.getOfferLetter().getBytes()) {
                @Override
                public String getFilename() {
                    return employeeFileDto.getOfferLetter().getOriginalFilename();
                }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getEmployeeData(int page, int pageSize) {
        try {
            String url = api + "?page=" + page + "&pageSize=" + pageSize;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void updateEmployee(EmployeeDto employeeDto, EmployeeFileDto employeeFileDto, String id, SystemList systemList) throws IOException {
        try {

            String url = api;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("userId", new HttpEntity<>(employeeDto.getUserId()));
            body.add("employeeId", new HttpEntity<>(employeeDto.getEmployeeId()));
            body.add("name", new HttpEntity<>(employeeDto.getName()));
            body.add("emailAddress", new HttpEntity<>(employeeDto.getEmailAddress()));
            body.add("phoneNo", new HttpEntity<>(employeeDto.getPhoneNo()));
            body.add("emergencyContactNo", new HttpEntity<>(employeeDto.getEmergencyContactNo()));
            body.add("workingWith", new HttpEntity<>(employeeDto.getWorkingWith()));
            body.add("reportTo", new HttpEntity<>(employeeDto.getReportTo()));
            body.add("experience", new HttpEntity<>(employeeDto.getExperience()));
            body.add("joiningDate", new HttpEntity<>(employeeDto.getJoiningDate()));
            body.add("availableLeaves", new HttpEntity<>(employeeDto.getAvailableLeaves()));
            body.add("salary", new HttpEntity<>(employeeDto.getSalary()));
            body.add("nextAppraisalDate", new HttpEntity<>(employeeDto.getNextAppraisalDate()));
            body.add("dateOfBirth", new HttpEntity<>(employeeDto.getDateOfBirth()));
            body.add("bloodGroup", new HttpEntity<>(employeeDto.getBloodGroup()));
            body.add("maritalStatus", new HttpEntity<>(employeeDto.getMaritalStatus()));
            body.add("gender", new HttpEntity<>(employeeDto.getGender()));
            body.add("address", new HttpEntity<>(employeeDto.getAddress()));
            body.add("city", new HttpEntity<>(employeeDto.getCity()));
            body.add("state", new HttpEntity<>(employeeDto.getState()));
            body.add("interestedTechnologies", new HttpEntity<>(employeeDto.getInterestedTechnologies()));
            body.add("bankAccountNo", new HttpEntity<>(employeeDto.getBankAccountNo()));
            body.add("ifscCode", new HttpEntity<>(employeeDto.getIfscCode()));
            body.add("role", new HttpEntity<>(employeeDto.getRole()));
            body.add("designation", new HttpEntity<>(employeeDto.getDesignation()));
            body.add("currentProjectName", new HttpEntity<>(employeeDto.getCurrentProjectName()));
            body.add("emergencyContactPerson", new HttpEntity<>(employeeDto.getEmergencyContactPerson()));


            body.add("pcName", new HttpEntity<>(systemList.getPcName()));
            body.add("ram", new HttpEntity<>(systemList.getRam()));
            body.add("hdd", new HttpEntity<>(systemList.getHdd()));
            body.add("comment", new HttpEntity<>(systemList.getComment()));


//            body.add("photo", new ByteArrayResource(employeeFileDto.getPhoto().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getPhoto().getOriginalFilename();
//                }
//            });
//            body.add("adharCartFront", new ByteArrayResource(employeeFileDto.getAdharCartFront().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getAdharCartFront().getOriginalFilename();
//                }
//            });
//
//            body.add("adharCartBack", new ByteArrayResource(employeeFileDto.getAdharCartBack().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getAdharCartBack().getOriginalFilename();
//                }
//            });
//
//            body.add("degree", new ByteArrayResource(employeeFileDto.getDegree().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getDegree().getOriginalFilename();
//                }
//            });
//
//            body.add("panCard", new ByteArrayResource(employeeFileDto.getPanCard().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getPanCard().getOriginalFilename();
//                }
//            });
//
//            body.add("std10", new ByteArrayResource(employeeFileDto.getStd10().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getStd10().getOriginalFilename();
//                }
//            });
//
//            body.add("std12", new ByteArrayResource(employeeFileDto.getStd12().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getStd12().getOriginalFilename();
//                }
//            });
//
//            body.add("exprienceLetter", new ByteArrayResource(employeeFileDto.getExprienceLetter().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getExprienceLetter().getOriginalFilename();
//                }
//            });
//
//            body.add("offerLetter", new ByteArrayResource(employeeFileDto.getOfferLetter().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return employeeFileDto.getOfferLetter().getOriginalFilename();
//                }
//            });
            System.out.println(employeeFileDto.getPhoto());
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            restTemplate.put(url, requestEntity, employeeDto);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getEmployeeById(String id) {
        try {

            String url = api + "/" + id;
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteEmployee(String id) {
        try {

            String url = api + "/" + id;
            restTemplate.delete(url, id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void reg(User user) {
        try {
            String url = api + "/registration";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set(HttpHeaders.AUTHORIZATION);
            HttpEntity<User> createEntity = new HttpEntity<>(user, headers);
            restTemplate.exchange(url, HttpMethod.POST, createEntity, String.class);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getEmployeeByName() {
        try {

            String url = api + "/findByName";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public void createRegistartionEmployee(Employee employee) {
        try {

            System.out.println("in the service");
//            System.out.println(employeeDto.getEmployeeId());

//            System.out.println(employeeDto.getEmployeeId());
            String url = api + "/registration";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("name", new HttpEntity<>(employee.getName()));
//            body.add("userId", new HttpEntity<>(employee.getUserId()));
//            body.add("emailAddress", new HttpEntity<>(employee.getEmailAddress()));
//            body.add("role", new HttpEntity<>(employee.getRole()));
            HttpEntity<Employee> createEntity = new HttpEntity<>(employee, headers);
            //HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.POST, createEntity, String.class);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
