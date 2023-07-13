package com.cybage.wfhreporting.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects_master")
public class ProjectEntity {

    @Id
    @Column(name = "project_id")
    private String projectId;

    @Column(name = "name")
    private String projectName;

    @Column(name = "manager")
    private String manager;

    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = EmployeeEntity.class)
    @Fetch(FetchMode.SELECT)
    private List<EmployeeEntity> employeeEntities = new ArrayList<>();

//    @OneToMany(mappedBy = "projectEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = TaskEntity.class)
//    @Fetch(FetchMode.SELECT)
//    private List<TaskEntity> taskEntities = new ArrayList<>();

    public List<EmployeeEntity> getEmployeeEntities() {
        return employeeEntities;
    }

    public void setEmployeeEntities(List<EmployeeEntity> employeeEntities) {
        this.employeeEntities = employeeEntities;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

//    public List<TaskEntity> getTaskEntities() {
//        return taskEntities;
//    }
//
//    public void setTaskEntities(List<TaskEntity> taskEntities) {
//        this.taskEntities = taskEntities;
//    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", manager='" + manager + '\'' +
                '}';
    }
}
