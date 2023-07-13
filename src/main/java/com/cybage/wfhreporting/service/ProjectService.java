package com.cybage.wfhreporting.service;

import com.cybage.wfhreporting.entity.ProjectEntity;
import com.cybage.wfhreporting.exception.GlobalException;
import com.cybage.wfhreporting.model.request.ProjectRequest;
import com.cybage.wfhreporting.model.response.ProjectResponse;
import com.cybage.wfhreporting.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public List<ProjectResponse> getAllProjects() {
        try {
            List<ProjectEntity> projectsEntity = projectRepository.findAll();
            List<ProjectResponse> projects = new ArrayList<>();
            for(ProjectEntity project : projectsEntity) {
                projects.add(new ProjectResponse(project.getProjectId(), project.getProjectName()));
            }
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public Boolean addProject(ProjectRequest project) {
        try {
            Optional<ProjectEntity> projectEntity = projectRepository.findById(project.getProjectId());
            if(!projectEntity.isPresent()) {
                ProjectEntity projectEntityToAdd = new ProjectEntity();
                projectEntityToAdd.setProjectId(project.getProjectId());
                projectEntityToAdd.setProjectName(project.getProjectName());
                projectEntityToAdd.setManager(project.getManager());
                projectRepository.save(projectEntityToAdd);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }

    public Boolean deleteProject(String projectId) {
        try {
            Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
            if(projectEntity.isPresent()) {
                projectRepository.delete(projectEntity.get());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(HttpStatus.FORBIDDEN, "Something went wrong!");
        }
    }
}
