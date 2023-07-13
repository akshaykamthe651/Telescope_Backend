package com.cybage.wfhreporting.controller;

import com.cybage.wfhreporting.model.request.ProjectRequest;
import com.cybage.wfhreporting.model.request.TaskRequest;
import com.cybage.wfhreporting.model.response.ProjectResponse;
import com.cybage.wfhreporting.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/projects")
public class ProjectController {
    private static final Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    @RequestMapping(path = "/getall", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllProjects() throws SecurityException, ParseException {
        return new ResponseEntity<Object>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> addProject(@RequestBody(required = true)ProjectRequest project) throws SecurityException, ParseException {
        System.out.println("In Add Project path");
        return new ResponseEntity<Object>(projectService.addProject(project), HttpStatus.OK);
    }

    @RequestMapping(path = "/delete/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> addProject(@PathVariable(required = true, value = "projectId") String projectId) throws SecurityException, ParseException {
        return new ResponseEntity<Object>(projectService.deleteProject(projectId), HttpStatus.OK);
    }

}