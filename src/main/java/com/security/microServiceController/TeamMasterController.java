package com.security.microServiceController;

import com.security.dto.TeamDetails;
import com.security.dto.TeamMaster;
import com.security.microService.TeamMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/teamMaster")
public class TeamMasterController {


    private final TeamMasterService teamMasterService;

    @Autowired
    public TeamMasterController(TeamMasterService teamMasterService) {
        this.teamMasterService = teamMasterService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> teamMasterCreate(@RequestBody TeamMaster teamMaster, HttpServletRequest request) {
        try {

            teamMasterService.create(teamMaster, request);
            return ResponseEntity.ok().body("\"teamMaster Created\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not created");
        }
    }

    @GetMapping("/teamMaster-data")
    public ResponseEntity<?> getAllTeamMaster(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        try {
            String projectData = teamMasterService.getAllTeamMaster(request, page, size);
            return ResponseEntity.ok().body(projectData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }


    @PutMapping("/teamMaster-update/{id}")
    public ResponseEntity<?> updateTeamMaster(@RequestBody TeamMaster teamMaster, @PathVariable String id, HttpServletRequest request) {
        try {

            teamMasterService.updateTeamMaster(teamMaster, id, request);
            return ResponseEntity.ok().body("\"teamDetails Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("not update");
        }
    }

    @DeleteMapping("/teamMaster-delete/{id}")
    public ResponseEntity<?> deleteTeamMaster(@PathVariable String id, HttpServletRequest request) {
        try {

            teamMasterService.deleteTeamMaster(id, request);
            return ResponseEntity.ok().body("\"teamMaster Delete\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not delete");
        }
    }

    @GetMapping("/teamMaster-id/{id}")
    public ResponseEntity<?> getTeamMasterById(@PathVariable String id, HttpServletRequest request) {
        try {

            String projectById = teamMasterService.getTeamMasterById(id, request);
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
