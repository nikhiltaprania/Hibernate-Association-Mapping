package com.associationmapping.service;

import com.associationmapping.dao.PersonDAO;
import com.associationmapping.dao.ProjectDAO;
import com.associationmapping.model.Person;
import com.associationmapping.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectServiceImpl implements ProjectService {
    private final Scanner sc;
    private final ProjectDAO projectDAO;
    private final PersonDAO personDAO;

    public ProjectServiceImpl(Scanner sc, ProjectDAO projectDAO, PersonDAO personDAO) {
        this.sc = sc;
        this.projectDAO = projectDAO;
        this.personDAO = personDAO;
    }

    @Override
    public void createProject() {
        System.out.print("Enter project name: ");
        sc.nextLine();
        String projectName = sc.nextLine();

        Project project = new Project();
        project.setProjectName(projectName);

        projectDAO.createProject(project);
    }

    @Override
    public Project getProjectById(int id) {
        return projectDAO.getProjectById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    @Override
    public void updateProject() {
        System.out.println("Enter the ID of the project to update:");
        int id = sc.nextInt();
        Project existingProject = projectDAO.getProjectById(id);
        if (existingProject == null) {
            System.out.println("Project not found with ID: " + id);
            return;
        }

        boolean flag = true;

        while (flag) {
        System.out.println("\nWhat Do You Wanna Do ?");
            System.out.println("1. Update Project Name\n2. Assign Members To Project\n3. Remove Members From Project");
            System.out.print("0. Back\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.print("Enter the new name: "); sc.nextLine();
                    String newName = sc.nextLine();
                    existingProject.setProjectName(newName);
                    projectDAO.updateProject(existingProject);
                    System.out.println("Project renamed successfully.");
                }

                case 2 -> assignMembersToProject(existingProject);

                case 3 -> removeMembersFromProject(existingProject);

                case 0 -> flag = false;

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    @Override
    public void deleteProject() {
        System.out.println("Enter the ID of the project to delete:");
        int id = sc.nextInt();

        Project existingProject = projectDAO.getProjectById(id);
        if (existingProject == null) {
            System.out.println("Project not found with ID: " + id);
            return;
        }

        projectDAO.deleteProject(id);
    }

    @Override
    public List<Project> getProjectsByMember(Person person) {
        return projectDAO.getProjectsByMember(person);
    }

    private void assignMembersToProject(Project project) {
        // Display available persons not already assigned to the project
        List<Person> availablePersons = getPersonsNotInProject(project);
        if (availablePersons.isEmpty()) {
            System.out.println("All Available People Are Associated With " + project.getProjectName());
            return;
        }

        System.out.println("Available Persons not already assigned to the project:");
        for (Person person : availablePersons) {
            System.out.println("ID: " + person.getId() + ", Name: " + person.getName());
        }

        // Select up to 5 persons to assign to the project
        System.out.println("Enter the IDs of up to 5 persons to assign to the project (0 to finish):");
        List<Person> selectedPersons = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            System.out.print("Person ID (" + (i + 1) + "/5): ");
            int personId = sc.nextInt();
            if (personId == 0) {
                break;  // User entered 0 to finish
            }

            Person selectedPerson = personDAO.getPersonById(personId);
            if (selectedPerson == null) {
                System.out.println("Person not found with ID: " + personId);
            } else if (selectedPersons.contains(selectedPerson)) {
                System.out.println("Person is already selected for this project.");
            } else {
                selectedPersons.add(selectedPerson);
            }
        }

        // Assign selected persons to the project
        project.getPeople().addAll(selectedPersons);
        projectDAO.updateProject(project);
        System.out.println("Members assigned to the project successfully.");
    }

    private List<Person> getPersonsNotInProject(Project project) {
        // Retrieve all persons not already assigned to the given project
        List<Person> allPersons = personDAO.getAllPersons();
        List<Person> projectMembers = project.getPeople();

        // Filter out persons already assigned to the project
        return allPersons.stream().filter(person -> !projectMembers.contains(person)).toList();
    }

    private void removeMembersFromProject(Project project) {
        // Display current members of the project
        List<Person> projectMembers = project.getPeople();
        if (projectMembers.isEmpty()) {
            System.out.println("No Members Are Assigned With " + project.getProjectName());
            return;
        }

        System.out.println("Current Members of the Project:");
        for (Person person : projectMembers) {
            System.out.println("ID: " + person.getId() + ", Name: " + person.getName());
        }

        // Enter the number of members to remove
        System.out.println("Enter the number of members to remove from the project (0 to cancel):");
        int numberOfMembersToRemove = sc.nextInt();
        if (numberOfMembersToRemove < 0 || numberOfMembersToRemove > projectMembers.size()) {
            System.out.println("Invalid number of members. Please enter a valid number.");
            return;
        }

        // Enter the IDs of members to remove
        System.out.println("Enter the IDs of members to remove (separated by spaces):");
        List<Integer> memberIdsToRemove = new ArrayList<>();
        for (int i = 0; i < numberOfMembersToRemove; i++) {
            System.out.print("Member ID (" + (i + 1) + "/" + numberOfMembersToRemove + "): ");
            int memberId = sc.nextInt();
            if (memberId == 0) {
                System.out.println("Operation canceled.");
                return;
            }
            memberIdsToRemove.add(memberId);
        }

        // Remove selected members from the project
        List<Person> updatedMembers = projectMembers.stream().filter(person -> !memberIdsToRemove.contains(person.getId())).toList();
        project.setPeople(updatedMembers);
        projectDAO.updateProject(project);
        System.out.println("Members removed from the project successfully.");
    }
}