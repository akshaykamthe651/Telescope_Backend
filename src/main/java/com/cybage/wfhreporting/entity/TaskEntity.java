package com.cybage.wfhreporting.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks_master")
public class TaskEntity {

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer taskId;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "task_desc")
    private String taskDesc;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private EmployeeEntity employeeEntity;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "project_id", insertable = false, nullable = false, updatable = false)
//    private ProjectEntity projectEntity;

//    public ProjectEntity getProjectEntity() {
//        return projectEntity;
//    }
//
//    public void setProjectEntity(ProjectEntity projectEntity) {
//        this.projectEntity = projectEntity;
//    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = new EmployeeEntity();
    }

    public TaskEntity() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public TaskEntity(Integer taskId, String employeeId, String projectId, String taskDesc, LocalDate date, String status, EmployeeEntity employeeEntity, ProjectEntity projectEntity) {
//        this.taskId = taskId;
//        this.employeeId = employeeId;
//        this.projectId = projectId;
//        this.taskDesc = taskDesc;
//        this.date = date;
//        this.status = status;
//        this.employeeEntity = employeeEntity;
//        this.projectEntity = projectEntity;
//    }


    public TaskEntity(Integer taskId, String employeeId, String projectId, String taskDesc, LocalDate date, String status, EmployeeEntity employeeEntity) {
        this.taskId = taskId;
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.taskDesc = taskDesc;
        this.date = date;
        this.status = status;
        this.employeeEntity = employeeEntity;
    }
}
