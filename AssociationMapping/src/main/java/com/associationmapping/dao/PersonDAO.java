package com.associationmapping.dao;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.List;

public interface PersonDAO {
    // Create
    void createPerson(Person person);

    // Read
    Person getPersonById(int id);

    List<Person> getAllPersons();

    // Update
    void updatePerson(Person person);

    // Delete
    void deletePerson(int id);

    // Additional Methods
    List<Person> getPersonsByName(String name);

    List<Person> getPersonsByCity(String city);

    List<Person> getPersonsByProject(Project project);
}