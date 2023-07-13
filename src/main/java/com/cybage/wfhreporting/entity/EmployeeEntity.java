package com.cybage.wfhreporting.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_master")
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "status_planned")
    private String statusPlanned;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "employeeEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = TaskEntity.class)
    @Fetch(FetchMode.SELECT)
    private List<TaskEntity> taskEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private ProjectEntity projectEntity;

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

    public List<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public EmployeeEntity() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "employeeId='" + employeeId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", name='" + name + '\'' +
                ", statusPlanned='" + statusPlanned + '\'' +
                ", location='" + location + '\'' +
                ", taskEntities=" + taskEntities +
                ", projectEntity=" + projectEntity +
                '}';
    }
}
