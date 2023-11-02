package com.security.microServiceController;

import com.security.dto.AssignProjectDto;
import com.security.dto.TeamDetails;
import com.security.microService.TeamDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/teamDetails")
public class TeamDetailsController {


    private final TeamDetailsService teamDetailsService;


    @Autowired
    public TeamDetailsController(TeamDetailsService teamDetailsService) {
        this.teamDetailsService = teamDetailsService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTeamDetails(@RequestBody TeamDetails teamDetails, HttpServletRequest request) {
        try {

            teamDetailsService.create(teamDetails, request);
            return ResponseEntity.ok().body("\"teamDetails Created\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not created");
        }
    }

    @GetMapping("/teamDetails-data")
    public ResponseEntity<?> getAllTeamDetails(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        try {
            String projectData = teamDetailsService.getAllTeamDetails(request, page, size);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }


    @PutMapping("/teamDetails-update/{id}")
    public ResponseEntity<?> updateTeamDetails(@RequestBody TeamDetails teamDetails, @PathVariable String id, HttpServletRequest request) {
        try {

            teamDetailsService.updateTeamDetails(teamDetails, id, request);
            return ResponseEntity.ok().body("\"teamDetails Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("not update");
        }
    }

    @DeleteMapping("/teamDetails-delete/{id}")
    public ResponseEntity<?> deleteTeamDetails(@PathVariable String id, HttpServletRequest request) {
        try {

            teamDetailsService.deleteTeamDetails(id, request);
            return ResponseEntity.ok().body("project Delete");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not delete");
        }
    }

    @GetMapping("/teamDetails-id/{id}")
    public ResponseEntity<?> getTeamDetailsById(@PathVariable String id, HttpServletRequest request) {
        try {

            String projectById = teamDetailsService.getTeamDetailsById(id, request);
            if (projectById != null) {
                return ResponseEntity.ok().body(projectById);
            } else {
                return ResponseEntity.badRequest().body("\"id not found\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not found");
        }
    }
}
