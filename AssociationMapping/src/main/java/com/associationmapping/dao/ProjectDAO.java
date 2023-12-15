package com.associationmapping.dao;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.List;

public interface ProjectDAO {
    // Create
    void createProject(Project project);

    // Read
    Project getProjectById(int id);

    List<Project> getAllProjects();

    // Update
    void updateProject(Project project);

    // Delete
    void deleteProject(int id);

    // Additional Methods
    List<Project> getProjectsByMember(Person person);
}