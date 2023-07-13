package com.cybage.wfhreporting.repository;

import com.cybage.wfhreporting.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProjectRepository extends  CrudRepository<ProjectEntity, String>, JpaRepository<ProjectEntity, String> {

}