package com.cybage.wfhreporting.controller;

import com.cybage.wfhreporting.model.request.TaskRequest;
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
@RequestMapping(path = "/api/tasks")
public class TaskController {
    private static final Logger LOGGER = LogManager.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllTasks(
            @RequestParam(name = "projectId", required = true) String projectId,
            @RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date
    ) throws SecurityException, ParseException {
        // LOGGER.info("GET '/projects/getall'");
        return new ResponseEntity<Object>(taskService.getAllTasks(projectId, date), HttpStatus.OK);
    }

    @RequestMapping(path = "/export", method = RequestMethod.GET)
    public ResponseEntity<Object> exportReport(
            @RequestParam(name = "attendanceDate", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate attendanceDate,
            @RequestParam(name = "projectId", required = true) String projectId
    ) throws SecurityException, ParseException {
        // LOGGER.info("GET '/projects/getall'");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Cybage_WFHDetails.xlsx");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return new ResponseEntity<Object>(taskService.exportReport(attendanceDate, projectId), headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/update/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateTask(
            @PathVariable(required = true, value = "taskId") Integer taskId,
            @RequestBody(required = true) TaskRequest task
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(taskService.updateTask(taskId, task.getTaskDesc(), task.getStatus()), HttpStatus.OK);
    }

    @RequestMapping(path = "/copy/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> copyPreviousTask(
            @PathVariable(required = true, value = "taskId") Integer taskId,
            @RequestParam(name = "projectId", required = true) String projectId,
            @RequestParam(name = "employeeId", required = true) String employeeId,
            @RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date
    ) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(taskService.copyPreviousTask(taskId, employeeId, projectId, date), HttpStatus.OK);
    }

}