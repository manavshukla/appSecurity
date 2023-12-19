package com.security.microServiceController;

import com.security.dto.ProjectDto;
import com.security.microService.ProjectService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/create-project")
    public ResponseEntity<?> projectCreate(@RequestBody ProjectDto projectDto, String token, HttpServletRequest request) {
        try {

            projectService.createProject(projectDto, request, token);
            return ResponseEntity.ok().body("\"timesheet Created\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not created\"");
        }
    }

    @GetMapping("/project-data")
        public ResponseEntity<?> getProjectData(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "createdate") String sortBy) {
        try {

            String projectData = projectService.getProjectData(page, size, sortBy);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }

    @GetMapping("/project-filter")
    public ResponseEntity<?> getFilterTitleName(@RequestParam String title) {
        try {
            ProjectDto projectDto = new ProjectDto();
            projectDto.setTitle(title);


            String projectData = projectService.getFilterName(title);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }

    @PutMapping("/project-update/{id}")
    public ResponseEntity<?> updateProject(@RequestBody ProjectDto projectDto, @PathVariable String id) {
        try {

            projectService.updateProject(projectDto, (id));
            return ResponseEntity.ok().body("\"project Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"not update\"");
        }
    }

    @DeleteMapping("/project-delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id) {
        try {

            projectService.deleteProject(id);
            return ResponseEntity.ok().body("\"project Delete\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not delete\"");
        }
    }

    @GetMapping("/project-id/{id}")
    public ResponseEntity<?> updateProject(@PathVariable ObjectId id) {
        try {

            String projectById = projectService.getProjectById(String.valueOf(id));
            if (projectById != null) {
                return ResponseEntity.ok().body(projectById);
            } else {
                return ResponseEntity.badRequest().body("\"id not found\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not found\"");
        }
    }


    @GetMapping("/project-list")
    public ResponseEntity<?> getAllProjectList() {
        try {

            String allProjectList = projectService.getAllProjectList();
            return ResponseEntity.ok().body(allProjectList);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }


    @GetMapping("/project-title")
    public ResponseEntity<?> getAllProjectTitle() {
        try {
            String allProjectList = projectService.getProjectTitle();
            return ResponseEntity.ok().body(allProjectList);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data snot found\"");
        }
    }

}
