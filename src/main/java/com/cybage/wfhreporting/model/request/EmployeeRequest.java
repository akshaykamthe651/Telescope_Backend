package com.cybage.wfhreporting.model.request;

public class EmployeeRequest {

    private String projectId;
    private String employeeId;
    private String name;
    private String statusPlanned;
    private String location;

    public EmployeeRequest(String projectId, String employeeId, String name, String statusPlanned, String location) {
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.name = name;
        this.statusPlanned = statusPlanned;
        this.location = location;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "projectId='" + projectId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", statusPlanned='" + statusPlanned + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
