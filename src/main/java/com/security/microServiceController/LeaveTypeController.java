package com.security.microServiceController;

import com.security.dto.CreateLeaveTypeDto;
import com.security.microService.LeaveTypeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/type")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    @Autowired
    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    @GetMapping("/leaveType-data")
    public ResponseEntity<?> getLeaveData() {
        try {

            String data = leaveTypeService.getLeaveTypeData();
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }

    @GetMapping("/leaveType-name")
    public ResponseEntity<?> leaveTypeByName() {
        try {

            String data = leaveTypeService.getLeaveTypeName();
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Data not found");
        }
    }

    @PostMapping("/create-leaveType")
    public ResponseEntity<?> createLeave(@RequestBody CreateLeaveTypeDto createLeaveTypeDto, String token, HttpServletRequest request) {
        try {

            leaveTypeService.createLeaveType(createLeaveTypeDto, request, token);
            return ResponseEntity.ok().body("\"Leave Type  Create\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not created");
        }
    }

    @PutMapping("/leaveType-update/{id}")
    public ResponseEntity<?> updateLeaveType(@RequestBody CreateLeaveTypeDto createLeaveTypeDto, @PathVariable ObjectId id) {
        try {

            leaveTypeService.updateLeave(createLeaveTypeDto, String.valueOf(id));
            return ResponseEntity.ok().body("\"leave Request Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not update");
        }
    }


    @DeleteMapping("/leaveType-delete/{id}")
    public ResponseEntity<?> deleteLeave(@PathVariable String id) {
        try {

            String data = leaveTypeService.deleteLeaveType(id);
            return ResponseEntity.ok().body("\"leave Type delete sucess\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not delete");
        }
    }

    @GetMapping("/leave-Type/{id}")
    public ResponseEntity<?> getLeaveTypeById(@PathVariable ObjectId id) {
        try {

            String leaveById = leaveTypeService.getLeaveTypeById(String.valueOf(id));
            if (leaveById != null) {
                return ResponseEntity.ok().body(leaveById);
            } else {
                return ResponseEntity.badRequest().body("id not found");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not found");
        }
    }
}
