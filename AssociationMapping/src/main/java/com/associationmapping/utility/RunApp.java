package com.associationmapping.utility;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;
import com.associationmapping.service.PersonService;
import com.associationmapping.service.ProjectService;

import java.util.List;
import java.util.Scanner;

public class RunApp {
    private final Scanner sc;
    private final PersonService personService;
    private final ProjectService projectService;

    public RunApp(Scanner sc, PersonService personService, ProjectService projectService) {
        this.sc = sc;
        this.personService = personService;
        this.projectService = projectService;
    }

    public void managePerson() {

        while (true) {
            System.out.println("\n====Perform Person Operations====");
            System.out.println("1. Create\n2. Update\n3. Delete\n4. View By ID\n5. View All");
            System.out.println("6. View All With A Name\n7. View All From A City\n8. View All Associated With A Project");
            System.out.print("0. Back To Main Menu\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> personService.createPerson();

                case 2 -> personService.updatePerson();

                case 3 -> personService.deletePerson();

                case 4 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    Person person = personService.getPersonById(id);
                    if (person != null) {
                        System.out.println(person);
                    } else {
                        System.out.println("Person not found with ID: " + id);
                    }
                }

                case 5 -> {
                    List<Person> people = personService.getAllPersons();
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Person Found");
                    }
                }

                case 6 -> {
                    System.out.print("Enter Name: "); sc.nextLine();
                    String name = sc.nextLine();
                    List<Person> people = personService.getPersonsByName(name);
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Person Found With Name " + name);
                    }
                }

                case 7 -> {
                    System.out.print("Enter City Name: "); sc.nextLine();
                    String cityName = sc.nextLine();

                    List<Person> people = personService.getPersonsByCity(cityName);
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Person Found With Name " + cityName);
                    }
                }

                case 8 -> viewAllPeopleAssociatedWithAProject();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    private void viewAllPeopleAssociatedWithAProject() {
        // Display available projects
        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("No Projects Available");
            return;
        }
        System.out.println("Available Projects:");
        projects.forEach(project -> System.out.println("ID: " + project.getId() + ", Name: " + project.getProjectName()));

        // Select a project
        System.out.println("Enter the ID of the project to view members:");
        int projectId = sc.nextInt();
        Project selectedProject = projectService.getProjectById(projectId);
        if (selectedProject == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Display members associated with the selected project
        List<Person> projectMembers = personService.getPersonsByProject(selectedProject);
        if (projectMembers.isEmpty()) {
            System.out.println("No Members Are Associated With Project " + selectedProject.getProjectName());
            return;
        }

        System.out.println("Members of the Project:");
        projectMembers.forEach(person -> System.out.println("ID: " + person.getId() + ", Name: " + person.getName()));
    }

    public void manageProject() {

        while (true) {
            System.out.println("\n====Perform Project Operations====");
            System.out.println("1. Create\n2. Update\n3. Delete\n4. View By ID");
            System.out.println("5. View All\n6. View All Associated With A Person");
            System.out.print("0. Back To Main Menu\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> projectService.createProject();

                case 2 -> projectService.updateProject();

                case 3 -> projectService.deleteProject();

                case 4 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    Project project = projectService.getProjectById(id);
                    if (project != null) {
                        System.out.println(project);
                    } else {
                        System.out.println("Project not found with ID: " + id);
                    }
                }

                case 5 -> {
                    List<Project> projects = projectService.getAllProjects();
                    if (!projects.isEmpty()) {
                        projects.forEach(System.out::println);
                    } else {
                        System.out.println("No Project Found");
                    }
                }

                case 6 -> viewAllProjectsAssociatedWithAPerson();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    private void viewAllProjectsAssociatedWithAPerson() {
        // Display available persons
        List<Person> persons = personService.getAllPersons();
        if (persons.isEmpty()) {
            System.out.println("No People Available");
            return;
        }
        System.out.println("Available Persons:");
        persons.forEach(person -> System.out.println("ID: " + person.getId() + ", Name: " + person.getName()));

        // Select a person
        System.out.println("Enter the ID of the person to view projects:");
        int personId = sc.nextInt();
        Person selectedPerson = personService.getPersonById(personId);
        if (selectedPerson == null) {
            System.out.println("Person not found with ID: " + personId);
            return;
        }

        // Display projects associated with the selected person
        List<Project> personProjects = projectService.getProjectsByMember(selectedPerson);
        if (personProjects.isEmpty()) {
            System.out.println(selectedPerson.getName() + " has no projects");
            return;
        }

        System.out.println("Projects of the Person:");
        personProjects.forEach(project -> System.out.println("ID: " + project.getId() + ", Name: " + project.getProjectName()));
    }
}