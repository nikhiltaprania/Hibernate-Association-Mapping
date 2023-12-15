package com.associationmapping.service;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.List;

public interface ProjectService {
    // Create
    void createProject();

    // Read
    Project getProjectById(int id);

    List<Project> getAllProjects();

    // Update
    void updateProject();

    // Delete
    void deleteProject();

    // Additional Methods
    List<Project> getProjectsByMember(Person person);
}