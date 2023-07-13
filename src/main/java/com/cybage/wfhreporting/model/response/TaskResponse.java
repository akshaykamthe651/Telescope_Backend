package com.cybage.wfhreporting.model.response;

import javax.persistence.Column;
import java.time.LocalDate;

public class TaskResponse {

    private Integer taskId;
    private String employeeId;
    private String projectId;
    private String taskDesc;
    private LocalDate date;
    private String status;
    private String employeeName;

    public TaskResponse(Integer taskId, String employeeId, String projectId, String taskDesc, LocalDate date, String status, String employeeName) {
        this.taskId = taskId;
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.taskDesc = taskDesc;
        this.date = date;
        this.status = status;
        this.employeeName = employeeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "taskId=" + taskId +
                ", employeeId='" + employeeId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
