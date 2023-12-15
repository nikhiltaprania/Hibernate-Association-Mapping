package com.employeemanagement.service;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;

import java.util.List;

public interface ProjectService {
    // Create
    void createProject();

    // Read
    Project getProjectById(int projectId);

    List<Project> getAllProjects();

    // Update
    void updateProject();

    // Delete
    void deleteProject();

    // Additional Methods
    List<Project> getProjectsByEmployee(Employee employee);
}