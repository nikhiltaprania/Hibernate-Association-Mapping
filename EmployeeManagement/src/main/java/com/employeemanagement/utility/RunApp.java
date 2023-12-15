package com.employeemanagement.utility;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.ProjectService;

import java.util.List;
import java.util.Scanner;

public class RunApp {
    private final Scanner sc;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public RunApp(Scanner sc, EmployeeService employeeService, ProjectService projectService) {
        this.sc = sc;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    public void manageEmployee() {

        while (true) {
            System.out.println("\n====Perform Employee Operations====");
            System.out.println("1. Create\n2. Update\n3. Delete\n4. View By ID\n5. View All");
            System.out.println("6. View All With A Name\n7. View All From A City\n8. View All Associated With A Project");
            System.out.print("0. Back To Main Menu\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> employeeService.createEmployee();

                case 2 -> employeeService.updateEmployee();

                case 3 -> employeeService.deleteEmployee();

                case 4 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    Employee employee = employeeService.getEmployeeById(id);
                    if (employee != null) {
                        System.out.println(employee);
                    } else {
                        System.out.println("Employee not found with ID: " + id);
                    }
                }

                case 5 -> {
                    List<Employee> people = employeeService.getAllEmployees();
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Employee Found");
                    }
                }

                case 6 -> {
                    System.out.print("Enter Name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    List<Employee> people = employeeService.getEmployeesByName(name);
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Employee Found With Name " + name);
                    }
                }

                case 7 -> {
                    System.out.print("Enter City Name: ");
                    sc.nextLine();
                    String cityName = sc.nextLine();

                    List<Employee> people = employeeService.getEmployeesByCity(cityName);
                    if (!people.isEmpty()) {
                        people.forEach(System.out::println);
                    } else {
                        System.out.println("No Employee Found With Name " + cityName);
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
        projects.forEach(project -> System.out.println("ID: " + project.getProjectId() + ", Name: " + project.getProjectName()));

        // Select a project
        System.out.println("Enter the ID of the project to view employees:");
        int projectId = sc.nextInt();
        Project selectedProject = projectService.getProjectById(projectId);
        if (selectedProject == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Display employees associated with the selected project
        List<Employee> projectEmployees = employeeService.getEmployeesByProject(selectedProject);
        if (projectEmployees.isEmpty()) {
            System.out.println("No Employees Are Associated With Project " + selectedProject.getProjectName());
            return;
        }

        System.out.println("Employees of the Project:");
        projectEmployees.forEach(employee -> System.out.println("ID: " + employee.getEmployeeId() + ", Name: " + employee.getEmployeeName()));
    }

    public void manageProject() {

        while (true) {
            System.out.println("\n====Perform Project Operations====");
            System.out.println("1. Create\n2. Update\n3. Delete\n4. View By ID");
            System.out.println("5. View All\n6. View All Associated With A Employee");
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

                case 6 -> viewAllProjectsAssociatedWithAEmployee();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    private void viewAllProjectsAssociatedWithAEmployee() {
        // Display available employees
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No People Available");
            return;
        }
        System.out.println("Available Employees:");
        employees.forEach(employee -> System.out.println("ID: " + employee.getEmployeeId() + ", Name: " + employee.getEmployeeName()));

        // Select an employee
        System.out.println("Enter the ID of the employee to view projects:");
        int employeeId = sc.nextInt();
        Employee selectedEmployee = employeeService.getEmployeeById(employeeId);
        if (selectedEmployee == null) {
            System.out.println("Employee not found with ID: " + employeeId);
            return;
        }

        // Display projects associated with the selected employee
        List<Project> employeeProjects = projectService.getProjectsByEmployee(selectedEmployee);
        if (employeeProjects.isEmpty()) {
            System.out.println(selectedEmployee.getEmployeeName() + " has no projects");
            return;
        }

        System.out.println("Projects of the Employee:");
        employeeProjects.forEach(project -> System.out.println("ID: " + project.getProjectId() + ", Name: " + project.getProjectName()));
    }
}