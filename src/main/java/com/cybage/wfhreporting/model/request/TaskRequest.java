package com.cybage.wfhreporting.model.request;

public class TaskRequest {

    private String taskDesc;
    private String status;

    public TaskRequest(String taskDesc, String status) {
        this.taskDesc = taskDesc;
        this.status = status;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskRequest{" +
                "taskDesc='" + taskDesc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
