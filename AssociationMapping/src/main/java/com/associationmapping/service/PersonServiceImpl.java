package com.associationmapping.service;

import com.associationmapping.dao.PersonDAO;
import com.associationmapping.dao.ProjectDAO;
import com.associationmapping.model.Address;
import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.List;
import java.util.Scanner;

public class PersonServiceImpl implements PersonService {
    private final Scanner sc;
    private final PersonDAO personDAO;
    private final ProjectDAO projectDAO;

    public PersonServiceImpl(Scanner sc, PersonDAO personDAO, ProjectDAO projectDAO) {
        this.sc = sc;
        this.personDAO = personDAO;
        this.projectDAO = projectDAO;
    }

    @Override
    public void createPerson() {
        System.out.println("Enter Person's Details");
        System.out.print("Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("Address Street: ");
        String street = sc.nextLine();
        System.out.print("Address City: ");
        String city = sc.nextLine();

        Person person = new Person();
        person.setName(name);

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);

        person.setAddress(address);
        personDAO.createPerson(person);
    }

    @Override
    public Person getPersonById(int id) {
        return personDAO.getPersonById(id);
    }

    @Override
    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    @Override
    public void updatePerson() {
        System.out.println("Enter the ID of the person to update:");
        int id = sc.nextInt();
        Person existingPerson = personDAO.getPersonById(id);
        if (existingPerson == null) {
            System.out.println("Person not found with ID: " + id);
            return;
        }

        boolean flag = true;

        while (flag) {
        System.out.println("\nWhat Do You Wanna Do ?");
            System.out.println("1. Update Your Name\n2. Take A Project\n3. Remove Project");
            System.out.print("0. Back\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.print("Enter the new name: "); sc.nextLine();
                    String newName = sc.nextLine();
                    existingPerson.setName(newName);
                    personDAO.updatePerson(existingPerson);
                    System.out.println("Person renamed successfully.");
                }

                case 2 -> assignProjectToPerson(existingPerson);

                case 3 -> removeProjectFromPerson(existingPerson);

                case 0 -> flag = false;

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    @Override
    public void deletePerson() {
        System.out.println("Enter the ID of the person to delete:");
        int id = sc.nextInt();
        Person existingPerson = personDAO.getPersonById(id);
        if (existingPerson == null) {
            System.out.println("Person not found with ID: " + id);
            return;
        }

        personDAO.deletePerson(id);
        System.out.println("Person deleted successfully.");
    }

    @Override
    public List<Person> getPersonsByName(String name) {
        return personDAO.getPersonsByName(name);
    }

    @Override
    public List<Person> getPersonsByCity(String city) {
        return personDAO.getPersonsByCity(city);
    }

    @Override
    public List<Person> getPersonsByProject(Project project) {
        return personDAO.getPersonsByProject(project);
    }

    private void assignProjectToPerson(Person person) {
        // Display available projects
        List<Project> projects = projectDAO.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("Sorry! No Projects Available For Now");
            return;
        }

        System.out.println("Available Projects:");
        projects.forEach(project -> System.out.println("ID: " + project.getId() + ", Name: " + project.getProjectName()));

        // Select a project
        System.out.println("Enter the ID of the project to assign:");
        int projectId = sc.nextInt();
        Project selectedProject = projectDAO.getProjectById(projectId);
        if (selectedProject == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Assign the selected project to the selected person
        person.getProjects().add(selectedProject);
        personDAO.updatePerson(person);
        System.out.println("Project assigned to "+person.getName()+" successfully.");
    }

    private void removeProjectFromPerson(Person person) {
        // Display projects assigned to the selected person
        List<Project> assignedProjects = person.getProjects();
        if (assignedProjects.isEmpty()) {
            System.out.println("No Projects Assigned To You " + person.getName());
            return;
        }

        System.out.println("Projects assigned to "+ person.getName()+ " are:");
        assignedProjects.forEach(project -> System.out.println("ID: " + project.getId() + ", Name: " + project.getProjectName()));

        // Select a project to remove
        System.out.println("Enter the ID of the project to remove from the selected person:");
        int projectId = sc.nextInt();
        Project projectToRemove = projectDAO.getProjectById(projectId);
        if (projectToRemove == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Remove the selected project from the selected person
        person.getProjects().remove(projectToRemove);
        personDAO.updatePerson(person);
        System.out.println("Project removed from "+person.getName()+" successfully.");
    }
}