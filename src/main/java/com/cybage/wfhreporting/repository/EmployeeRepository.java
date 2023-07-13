package com.cybage.wfhreporting.repository;

import com.cybage.wfhreporting.entity.EmployeeEntity;
import com.cybage.wfhreporting.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends  CrudRepository<EmployeeEntity, String>, JpaRepository<EmployeeEntity, String> {
    List<EmployeeEntity> findByProjectIdIs(String projectId);
    Optional<EmployeeEntity> findByProjectIdIsAndEmployeeIdIs(String projectId, String employeeId);
    void deleteByProjectIdIsAndEmployeeIdIs(String projectId, String employeeId);
}