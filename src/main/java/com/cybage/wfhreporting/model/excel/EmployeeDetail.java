package com.cybage.wfhreporting.model.excel;

import com.cybage.wfhreporting.model.request.TaskRequest;

import java.time.LocalDate;
import java.util.Map;

public class EmployeeDetail {

    String projectName;
    String employeeId;
    String employeeName;
    String managerName;
    String statusPlanned;
    String location;
    Map<LocalDate, TaskRequest> taskDetails;
    Integer workPercentage;

    public EmployeeDetail() {
    }

    public EmployeeDetail(String projectName, String employeeId, String employeeName, String managerName, String statusPlanned, String location, Map<LocalDate, TaskRequest> taskDetails, Integer workPercentage) {
        this.projectName = projectName;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.managerName = managerName;
        this.statusPlanned = statusPlanned;
        this.location = location;
        this.taskDetails = taskDetails;
        this.workPercentage = workPercentage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getStatusPlanned() {
        return statusPlanned;
    }

    public void setStatusPlanned(String statusPlanned) {
        this.statusPlanned = statusPlanned;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<LocalDate, TaskRequest> getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(Map<LocalDate, TaskRequest> taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Integer getWorkPercentage() {
        return workPercentage;
    }

    public void setWorkPercentage(Integer workPercentage) {
        this.workPercentage = workPercentage;
    }

    @Override
    public String toString() {
        return "EmployeeDetail{" +
                "projectName='" + projectName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", statusPlanned='" + statusPlanned + '\'' +
                ", location='" + location + '\'' +
                ", taskDetails=" + taskDetails +
                ", workPercentage=" + workPercentage +
                '}';
    }
}
