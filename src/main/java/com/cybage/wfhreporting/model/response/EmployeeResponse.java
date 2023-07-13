package com.cybage.wfhreporting.model.response;

public class EmployeeResponse {
    private String employeeId;
    private String projectId;
    private String name;
    private String statusPlanned;
    private String location;

    public EmployeeResponse(String employeeId, String projectId, String name, String statusPlanned, String location) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.name = name;
        this.statusPlanned = statusPlanned;
        this.location = location;
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
        return "EmployeeResponse{" +
                "employeeId='" + employeeId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", name='" + name + '\'' +
                ", statusPlanned='" + statusPlanned + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
