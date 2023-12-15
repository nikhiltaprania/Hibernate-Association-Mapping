package com.employeemanagement.dao;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;

import java.util.List;

public interface ProjectDAO {
    // Create
    void createProject(Project project);

    // Read
    Project getProjectById(int projectId);

    List<Project> getAllProjects();

    // Update
    void updateProject(Project project);

    // Delete
    void deleteProject(int projectId);

    // Additional Methods
    List<Project> getProjectsByEmployee(Employee employee);
}