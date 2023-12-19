package com.security.microServiceController;

import com.security.dto.TimeSheetDto;
import com.security.microService.TimesheetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TimeSheetController {

    private final TimesheetService timesheetService;

    @Autowired
    public TimeSheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }


    @PostMapping("/create-timesheet")
    public ResponseEntity<?> timeSheetGetData(@RequestBody TimeSheetDto timeSheetDto, String token, HttpServletRequest request) {
        try {

            timesheetService.createTimesheet(timeSheetDto, request, token);
            return ResponseEntity.ok().body("\"timesheet Created\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not created\"");
        }
    }

    @GetMapping("/timesheet-data")
    public ResponseEntity<?> getTimeSheetData(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int pageSize,
                                              @RequestParam(defaultValue = "title") String sortBy) {
        try {

            String leaveData = timesheetService.getLeaveData(page, pageSize, sortBy);
            return ResponseEntity.ok().body(leaveData);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Data not found\"");
        }
    }

    @PutMapping("/timesheet-update/{id}")
    public ResponseEntity<?> updateTimeSheet(@RequestBody TimeSheetDto timeSheetDto, @PathVariable String id) {
        try {

            timesheetService.updateTimesheet(timeSheetDto, (id));
            return ResponseEntity.ok().body("\"timesheet Update\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"not update\"");
        }
    }

    @DeleteMapping("/timesheet-delete/{id}")
    public ResponseEntity<?> deleteTimesheet(@PathVariable String id) {
        try {

            timesheetService.deleteLeave(id);
            return ResponseEntity.ok().body("\"TimeSheet Delete\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not delete\"");
        }
    }

    @GetMapping("/timesheet-id/{id}")
    public ResponseEntity<?> updateTimeSheet(@PathVariable ObjectId id) {
        try {

            String timesheetByID = timesheetService.getTimesheetById(String.valueOf(id));
            if (timesheetByID != null) {
                return ResponseEntity.ok().body(timesheetByID);
            } else {
                return ResponseEntity.badRequest().body("\"id not found\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Not found\"");
        }
    }


    @GetMapping("/timesheet-empId/{id}")
    public ResponseEntity<?> getTimesheetByEmpId(@PathVariable("id") String employeeId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int pageSize,
                                                 @RequestParam(defaultValue = "") String sortBy

    ) {


        System.out.println(sortBy);
        String timesheetByEMpId = timesheetService.getTimesheetByEmpId(employeeId, page, pageSize, sortBy);
        if (timesheetByEMpId != null) {
            return ResponseEntity.ok().body(timesheetByEMpId);
        } else {
            return ResponseEntity.badRequest().body("\"id not found\"");
        }


    }
}
