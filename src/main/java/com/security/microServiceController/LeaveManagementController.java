package com.security.microServiceController;

import com.security.dto.CreateLeaveDto;
import com.security.microService.LeaveManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LeaveManagementController {

    private final LeaveManageService leaveManageService;

    @Autowired
    public LeaveManagementController(LeaveManageService leaveManageService) {
        this.leaveManageService = leaveManageService;
    }

    @GetMapping("/leave-data")
    public ResponseEntity<?> getLeaveData(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int pageSize,
                                          @RequestParam(defaultValue = "createdOn,dsc") String[] sort) {
        try {

            String data = leaveManageService.getLeaveData(page, pageSize, sort);
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }

    @PostMapping("/create-leave")
    public ResponseEntity<?> createLeave(@RequestBody CreateLeaveDto createLeaveDto, String token, HttpServletRequest request) {
        try {
            ResponseEntity<?> leave = leaveManageService.createLeave(createLeaveDto, request, token);

            System.out.println(leave.getBody());

            if (leave.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok().body("\"Leave Added Successfully\"");
            } else {
                return ResponseEntity.badRequest().body("\"Sorry,Yor are not eligible for apply for this leave\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not added\"");
        }
    }

    @PutMapping("/leave-update/{id}")
    public ResponseEntity<?> updateLeaveRequest(@RequestBody CreateLeaveDto createLeaveDto, @PathVariable String id) {
        try {

            leaveManageService.updateLeave(createLeaveDto, id);
            return ResponseEntity.ok().body("\"leave Request Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not update\"");
        }
    }


    @DeleteMapping("/leave-delete/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable String id) {
        try {

            String data = leaveManageService.deleteLeave(id);
            return ResponseEntity.ok().body("\"leave delete sucess\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not delete\"");
        }
    }

    @PutMapping("/{id}/leave-approve")
    public ResponseEntity<?> approveLeave(@PathVariable String id, @RequestBody CreateLeaveDto createLeaveDto) {
        try {

            leaveManageService.approveLeave(id, createLeaveDto);
            return ResponseEntity.ok().body("\"Leave approve\"");
        } catch (Exception e) {
            System.out.println();
            return ResponseEntity.badRequest().body("\"Not approve\"");
        }
    }

    @PutMapping("/{id}/leave-reject")
    public ResponseEntity<?> rejectLeave(@PathVariable String id, @RequestBody CreateLeaveDto createLeaveDto) {
        try {

            leaveManageService.rejectLeave(id, createLeaveDto);
            return ResponseEntity.ok().body("\"Reject Leave\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Note reject\"");
        }
    }


    @GetMapping("/leave-id/{id}")
    public ResponseEntity<?> updateTimeSheet(@PathVariable String id) {
        try {

            String leaveById = leaveManageService.getLeaveById(String.valueOf(id));
            if (leaveById != null) {
                return ResponseEntity.ok().body(leaveById);
            } else {
                return ResponseEntity.badRequest().body("\"id not found\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not found\"");
        }
    }


    @GetMapping("/leave-emp/{employeeId}")
    public ResponseEntity<?> getLeaveByEmpId(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int pageSize,
                                             @PathVariable("employeeId") String employeeId) {
        try {

            String data = leaveManageService.getLeaveByEmployeeId(page, pageSize, employeeId);
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }
}
