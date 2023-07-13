package com.cybage.wfhreporting.model.request;

public class ProjectRequest {
    private String projectId;
    private String projectName;
    private String manager;

    public ProjectRequest(String projectId, String projectName, String manager) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.manager = manager;
    }

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", manager='" + manager + '\'' +
                '}';
    }
}
