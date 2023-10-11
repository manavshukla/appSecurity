package com.security.microServiceController;

import com.security.dto.AssignProjectDto;
import com.security.microService.AssignProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin("*")
@RequestMapping("/assignProject")
public class AssignProjectController {


    private final AssignProjectService assignProjectService;


    @Autowired
    public AssignProjectController(AssignProjectService assignProjectService) {
        this.assignProjectService = assignProjectService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> projectCreate(@RequestBody AssignProjectDto assignProjectDto, HttpServletRequest request) {
        try {

            assignProjectService.create(assignProjectDto, request);
            return ResponseEntity.ok().body("\"assignProject Created\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not created");
        }
    }

    @GetMapping("/assignProject-data")
    public ResponseEntity<?> getAllAssignProject(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        try {
            String projectData = assignProjectService.getAllAssignProjects(request, page, size);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }


    @PutMapping("/assignProject-update/{id}")
    public ResponseEntity<?> getAssignProjectById(@RequestBody AssignProjectDto assignProjectDto, @PathVariable String id, HttpServletRequest request) {
        try {

            assignProjectService.updateAssignProject(assignProjectDto, id, request);
            return ResponseEntity.ok().body("\"project Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("not update");
        }
    }

    @DeleteMapping("/assignProject-delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id, HttpServletRequest request) {
        try {

            assignProjectService.deleteProject(id, request);
            return ResponseEntity.ok().body("project Delete");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not delete");
        }
    }

    @GetMapping("/assignProject-id/{id}")
    public ResponseEntity<?> getAssignProjectById(@PathVariable String id, HttpServletRequest request) {
        try {

            String projectById = assignProjectService.getAssignProjectById(id, request);
            if (projectById != null) {
                return ResponseEntity.ok().body(projectById);
            } else {
                return ResponseEntity.badRequest().body("id not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not found");
        }
    }


    @GetMapping("/project-empId/{id}")
    public ResponseEntity<?> getFilterProjectByEmpId(@PathVariable String id, HttpServletRequest request) {
        try {
//            ProjectDto projectDto = new ProjectDto();
//            projectDto.setTitle(projectId);


            String projectData = assignProjectService.getFilterProjectByEmpId(id, request);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }


    @GetMapping("/project-projectId/{id}")
    public ResponseEntity<?> getFilterProjectByProjectId(@PathVariable String id, HttpServletRequest request) {
        try {
//            ProjectDto projectDto = new ProjectDto();
//            projectDto.setTitle(projectId);


            String projectData = assignProjectService.getFilterProjectByProjectId(id, request);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }
}
