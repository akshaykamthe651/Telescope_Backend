package com.cybage.wfhreporting.service;

import com.cybage.wfhreporting.entity.EmployeeEntity;
import com.cybage.wfhreporting.entity.TaskEntity;
import com.cybage.wfhreporting.exception.GlobalException;
import com.cybage.wfhreporting.model.excel.EmployeeDetail;
import com.cybage.wfhreporting.model.request.TaskRequest;
import com.cybage.wfhreporting.model.response.TaskResponse;
import com.cybage.wfhreporting.repository.EmployeeRepository;
import com.cybage.wfhreporting.repository.TaskRepository;
import com.cybage.wfhreporting.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ExcelUtil excelUtil;

    public List<TaskResponse> getAllTasks(String projectId, LocalDate date) {
        try {

            List<EmployeeEntity> employeeEntities = employeeRepository.findByProjectIdIs(projectId);
            List<TaskEntity> tasksEntities = taskRepository.findByProjectIdIsAndDateIs(projectId, date);
            System.out.println(tasksEntities.size());

            LocalDate minDate = LocalDate.now().minusDays(1);
            LocalDate maxDate = LocalDate.now().plusDays(3);
            System.out.println(LocalDate.now().getDayOfWeek().getValue());
            if(LocalDate.now().getDayOfWeek().getValue() == 4 || LocalDate.now().getDayOfWeek().getValue() == 5) {
                maxDate = LocalDate.now().plusDays(5);
            }
            if(LocalDate.now().getDayOfWeek().getValue() == 6) {
                maxDate = LocalDate.now().plusDays(4);
            }
            System.out.print("Min Date : " + minDate);
            System.out.print("Max Date : " + maxDate);
            if(date.isAfter(minDate) && date.isBefore(maxDate)) {
                if(tasksEntities.size() != employeeEntities.size()) {
                    List<String> employeeIdsTaskExists = tasksEntities.stream().map(t -> t.getEmployeeId()).collect(Collectors.toList());
                    List<EmployeeEntity> employeeToCreateTask = employeeEntities.stream().filter(e -> !employeeIdsTaskExists.contains(e.getEmployeeId())).collect(Collectors.toList());
                    createTasks(projectId, date, employeeToCreateTask);
                    tasksEntities = taskRepository.findByProjectIdIsAndDateIs(projectId, date);
                }
            }
            List<TaskResponse> tasks = new ArrayList<>();
            for(TaskEntity task : tasksEntities) {
                System.out.print(task);
                tasks.add(new TaskResponse(task.getTaskId(), task.getEmployeeEntity().getEmployeeId(), task.getProjectId(), task.getTaskDesc(), task.getDate(), task.getStatus(), task.getEmployeeEntity().getName()));
            }
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public Boolean updateTask(Integer taskId, String taskDesc, String status) {
        try {
            Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
            if(taskEntity.isPresent()) {
                LocalDate minDate = LocalDate.now().minusDays(1);
                LocalDate maxDate = LocalDate.now().plusDays(3);
                if(LocalDate.now().getDayOfWeek().getValue() == 4 || LocalDate.now().getDayOfWeek().getValue() == 5) {
                    maxDate = LocalDate.now().plusDays(5);
                }
                if(LocalDate.now().getDayOfWeek().getValue() == 6) {
                    maxDate = LocalDate.now().plusDays(4);
                }
                TaskEntity task = taskEntity.get();
                if(task.getDate().isAfter(minDate) && task.getDate().isBefore(maxDate)) {
                    task.setTaskDesc(taskDesc);
                    task.setStatus(status);
                    taskRepository.save(task);
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


    public Boolean copyPreviousTask(Integer taskId, String employeeId, String projectId, LocalDate currentDate) {
        try {
            LocalDate dateToCopyTaskFrom = currentDate.minusDays(1);
            if(currentDate.getDayOfWeek().getValue() == 1) {
                dateToCopyTaskFrom = currentDate.minusDays(3);
            }
            Optional<TaskEntity> tasksEntity = taskRepository.findByProjectIdIsAndDateIsAndEmployeeIdIs(projectId, dateToCopyTaskFrom, employeeId);
            String taskDesc = "";
            String status = "";
            if(tasksEntity.isPresent()) {
                taskDesc = tasksEntity.get().getTaskDesc();
                status = tasksEntity.get().getStatus();
            }
            updateTask(taskId, taskDesc, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    private void createTasks(String projectId, LocalDate date, List<EmployeeEntity> employeeEntities) {
        try {
            List<TaskEntity> taskEntities = new ArrayList<>();
            for(EmployeeEntity employeeEntity : employeeEntities) {
                taskEntities.add(new TaskEntity(null, employeeEntity.getEmployeeId(), employeeEntity.getProjectId(), "", date, "", employeeEntity));
            }
            taskRepository.saveAllAndFlush(taskEntities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public ByteArrayResource exportReport(LocalDate attendanceDate, String projectId) {
        try {
            Map<String, EmployeeDetail> data = new TreeMap<>();
            YearMonth yearMonth = YearMonth.from(attendanceDate);
            List<EmployeeEntity> employeeEntities = employeeRepository.findByProjectIdIs(projectId);

            for(EmployeeEntity employee: employeeEntities) {
                EmployeeDetail employeeDetail = new EmployeeDetail();

                employeeDetail.setProjectName(employee.getProjectEntity().getProjectName());
                employeeDetail.setEmployeeId(employee.getEmployeeId());
                employeeDetail.setEmployeeName(employee.getName());
                employeeDetail.setManagerName(employee.getProjectEntity().getManager());
                employeeDetail.setStatusPlanned(employee.getStatusPlanned());
                employeeDetail.setLocation(employee.getLocation());

                List<TaskEntity> tasksEntities = taskRepository.findByProjectIdIsAndEmployeeIdIsAndDateBetween(projectId, employee.getEmployeeId(), yearMonth.atDay(1), yearMonth.atEndOfMonth());
                Map<LocalDate, TaskRequest> taskDetails = new TreeMap<>();
                for(TaskEntity task : tasksEntities) {
                    taskDetails.put(task.getDate(), new TaskRequest(task.getTaskDesc(), task.getStatus()));
                }

                employeeDetail.setTaskDetails(taskDetails);

                for(LocalDate date = yearMonth.atDay(1); date.isBefore(yearMonth.atEndOfMonth().plusDays(1)); date = date.plusDays(1)) {
                    if(!taskDetails.containsKey(date) && !(date.getDayOfWeek().getValue() == 6) && !(date.getDayOfWeek().getValue() == 7)) {
                        taskDetails.put(date, new TaskRequest("", ""));
                    }
                }

                employeeDetail.setTaskDetails(taskDetails);
                data.put(employee.getEmployeeId(), employeeDetail);
            }
            ByteArrayResource output = new ByteArrayResource(excelUtil.exportToExcel(data, attendanceDate, yearMonth.atDay(1), yearMonth.atEndOfMonth()).toByteArray());
            return output;
        }  catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

}
