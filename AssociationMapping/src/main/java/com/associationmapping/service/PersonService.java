package com.associationmapping.service;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.List;

public interface PersonService {
    // Create
    void createPerson();

    // Read
    Person getPersonById(int id);

    List<Person> getAllPersons();

    // Update
    void updatePerson();

    // Delete
    void deletePerson();

    // Additional Methods
    List<Person> getPersonsByName(String name);

    List<Person> getPersonsByCity(String city);

    List<Person> getPersonsByProject(Project project);
}