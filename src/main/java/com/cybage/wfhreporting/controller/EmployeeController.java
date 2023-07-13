package com.cybage.wfhreporting.controller;

import com.cybage.wfhreporting.model.request.EmployeeRequest;
import com.cybage.wfhreporting.model.request.TaskRequest;
import com.cybage.wfhreporting.service.EmployeeService;
import com.cybage.wfhreporting.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/employees")
public class EmployeeController {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(path = "/get/{projectId}/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getEmployee(
            @PathVariable(required = true, value = "projectId") String projectId,
            @PathVariable(required = true, value = "employeeId") String employeeId
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(employeeService.getEmployee(projectId, employeeId), HttpStatus.OK);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addEmployee(
            @RequestBody(required = true) EmployeeRequest employeeRequest
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(employeeService.addEmployee(employeeRequest), HttpStatus.OK);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateEmployee(
            @RequestBody(required = true) EmployeeRequest employeeRequest
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(employeeService.updateEmployee(employeeRequest), HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{projectId}/{employeeId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteEmployee(
            @PathVariable(required = true, value = "projectId") String projectId,
            @PathVariable(required = true, value = "employeeId") String employeeId
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(employeeService.deleteEmployee(projectId, employeeId), HttpStatus.OK);
    }

}