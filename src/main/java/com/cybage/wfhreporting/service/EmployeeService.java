package com.cybage.wfhreporting.service;

import com.cybage.wfhreporting.entity.EmployeeEntity;
import com.cybage.wfhreporting.entity.ProjectEntity;
import com.cybage.wfhreporting.exception.GlobalException;
import com.cybage.wfhreporting.model.request.EmployeeRequest;
import com.cybage.wfhreporting.model.request.ProjectRequest;
import com.cybage.wfhreporting.model.response.EmployeeResponse;
import com.cybage.wfhreporting.model.response.ProjectResponse;
import com.cybage.wfhreporting.repository.EmployeeRepository;
import com.cybage.wfhreporting.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProjectRepository projectRepository;

    public EmployeeResponse getEmployee(String projectId, String employeeId) {
        try {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findByProjectIdIsAndEmployeeIdIs(projectId, employeeId);
            if(employeeEntity.isPresent()) {
                EmployeeEntity employeeEntity1 = employeeEntity.get();
                EmployeeResponse employeeResponse = new EmployeeResponse(
                employeeEntity1.getEmployeeId(),
                employeeEntity1.getProjectId(),
                employeeEntity1.getName(),
                employeeEntity1.getStatusPlanned(),
                employeeEntity1.getLocation()
                );
                return employeeResponse;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public Boolean addEmployee(EmployeeRequest employeeRequest) {
        try {
            System.out.print("Employee to add : " + employeeRequest);
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeRequest.getEmployeeId());
            if(!employeeEntity.isPresent()) {
                EmployeeEntity employeeEntityToAdd = new EmployeeEntity();
                employeeEntityToAdd.setProjectId(employeeRequest.getProjectId());
                employeeEntityToAdd.setEmployeeId(employeeRequest.getEmployeeId());
                employeeEntityToAdd.setName(employeeRequest.getName());
                employeeEntityToAdd.setStatusPlanned(employeeRequest.getStatusPlanned());
                employeeEntityToAdd.setLocation(employeeRequest.getLocation());
                employeeRepository.save(employeeEntityToAdd);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }
    public Boolean updateEmployee(EmployeeRequest employeeRequest) {
        try {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeRequest.getEmployeeId());
            if(employeeEntity.isPresent()) {
                EmployeeEntity employeeEntityToUpdate = employeeEntity.get();
                employeeEntityToUpdate.setName(employeeRequest.getName());
                employeeEntityToUpdate.setStatusPlanned(employeeRequest.getStatusPlanned());
                employeeEntityToUpdate.setLocation(employeeRequest.getLocation());
                System.out.print("Employee To Update : " + employeeEntityToUpdate.getStatusPlanned());
                employeeRepository.save(employeeEntityToUpdate);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public Boolean deleteEmployee(String projectId, String employeeId) {
        try {
            Optional<EmployeeEntity> employeeEntity = employeeRepository.findByProjectIdIsAndEmployeeIdIs(projectId, employeeId);
            if (employeeEntity.isPresent()) {
               Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
               if(projectEntity.isPresent()) {
                   ProjectEntity projectEntity1 = projectEntity.get();
                   projectEntity1.getEmployeeEntities().remove(employeeEntity.get());
                   projectEntity1.getEmployeeEntities().stream().forEach(System.out::println);
                   projectRepository.save(projectEntity1);
                   return true;
               } else {
                   return false;
               }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }
}
