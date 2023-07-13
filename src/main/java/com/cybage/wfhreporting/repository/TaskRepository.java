package com.cybage.wfhreporting.repository;

import com.cybage.wfhreporting.entity.ProjectEntity;
import com.cybage.wfhreporting.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends  CrudRepository<TaskEntity, Integer>, JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findByProjectIdIsAndDateIs(String projectId, LocalDate date);
    Optional<TaskEntity> findByProjectIdIsAndDateIsAndEmployeeIdIs(String projectId, LocalDate date, String employeeId);
    List<TaskEntity> findByProjectIdIsAndEmployeeIdIsAndDateBetween(String projectId, String employeeId, LocalDate startDate, LocalDate endDate);
}