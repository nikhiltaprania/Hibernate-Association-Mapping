package com.employeemanagement.service;

import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.dao.ProjectDAO;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectServiceImpl implements ProjectService {
    private final Scanner sc;
    private final ProjectDAO projectDAO;
    private final EmployeeDAO employeeDAO;

    public ProjectServiceImpl(Scanner sc, ProjectDAO projectDAO, EmployeeDAO employeeDAO) {
        this.sc = sc;
        this.projectDAO = projectDAO;
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void createProject() {
        System.out.println("\nEnter Project Details");
        System.out.print("Project Name: ");
        sc.nextLine();
        String projectName = sc.nextLine();
        System.out.print("Tack Stacks Used: ");
        String techStack = sc.nextLine();

        Project project = new Project(projectName, techStack);

        projectDAO.createProject(project);
    }

    @Override
    public Project getProjectById(int projectId) {
        return projectDAO.getProjectById(projectId);
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
            System.out.println("1. Update Project Name\n2. Update TechStacks\n3. Assign Employees To Project");
            System.out.print("4. Remove Employees From Project\n0. Back\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.print("Enter the new name: ");
                    sc.nextLine();
                    String newName = sc.nextLine();
                    existingProject.setProjectName(newName);
                    projectDAO.updateProject(existingProject);
                    System.out.println("Project renamed successfully.");
                }
                case 2 -> {
                    System.out.println("Enter new Tech Stacks separated with ,");
                    sc.nextLine();
                    String newTechStacks = sc.nextLine();
                    existingProject.setTechStacks(newTechStacks);
                    System.out.println("New Tech Stacks added successfully in " + existingProject.getProjectName());
                }

                case 3 -> assignEmployeesToProject(existingProject);

                case 4 -> removeEmployeesFromProject(existingProject);

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
    public List<Project> getProjectsByEmployee(Employee employee) {
        return projectDAO.getProjectsByEmployee(employee);
    }

    private void assignEmployeesToProject(Project project) {
        // Display available employees not already assigned to the project
        List<Employee> availableEmployees = getEmployeesNotInProject(project);
        if (availableEmployees.isEmpty()) {
            System.out.println("All Available People Are Associated With " + project.getProjectName());
            return;
        }

        System.out.println("Available Employees not already assigned to the project:");
        for (Employee employee : availableEmployees) {
            System.out.println("ID: " + employee.getEmployeeId() + ", Name: " + employee.getEmployeeName());
        }

        // Select up to 5 employees to assign to the project
        System.out.println("Enter the IDs of up to 5 employees to assign to the project (0 to finish):");
        List<Employee> selectedEmployees = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            System.out.print("Employee ID (" + (i + 1) + "/5): ");
            int employeeId = sc.nextInt();
            if (employeeId == 0) {
                break;  // User entered 0 to finish
            }

            Employee selectedEmployee = employeeDAO.getEmployeeById(employeeId);
            if (selectedEmployee == null) {
                System.out.println("Employee not found with ID: " + employeeId);

            } else if (selectedEmployees.contains(selectedEmployee)) {
                System.out.println("Employee is already selected for this project.");

            } else {
                selectedEmployees.add(selectedEmployee);
            }
        }

        // Assign selected employees to the project
        project.getEmployees().addAll(selectedEmployees);
        projectDAO.updateProject(project);
        System.out.println("Employees assigned to the project successfully.");
    }

    private List<Employee> getEmployeesNotInProject(Project project) {
        // Retrieve all employees not already assigned to the given project
        List<Employee> allEmployees = employeeDAO.getAllEmployees();
        List<Employee> projectEmployees = project.getEmployees();

        // Filter out employees already assigned to the project
        return allEmployees.stream().filter(employee -> !projectEmployees.contains(employee)).toList();
    }

    private void removeEmployeesFromProject(Project project) {
        // Display current employees of the project
        List<Employee> projectEmployees = project.getEmployees();
        if (projectEmployees.isEmpty()) {
            System.out.println("No Employees Are Assigned With " + project.getProjectName());
            return;
        }

        System.out.println("Current Employees of the Project:");
        for (Employee employee : projectEmployees) {
            System.out.println("ID: " + employee.getEmployeeId() + ", Name: " + employee.getEmployeeName());
        }

        // Enter the number of employees to remove
        System.out.println("Enter the number of employees to remove from the project (0 to cancel):");
        int numberOfEmployeesToRemove = sc.nextInt();
        if (numberOfEmployeesToRemove < 0 || numberOfEmployeesToRemove > projectEmployees.size()) {
            System.out.println("Invalid number of employees. Please enter a valid number.");
            return;
        }

        // Enter the IDs of employees to remove
        System.out.println("Enter the IDs of employees to remove (separated by spaces):");
        List<Integer> employeeIdsToRemove = new ArrayList<>();
        for (int i = 0; i < numberOfEmployeesToRemove; i++) {
            System.out.print("Employee ID (" + (i + 1) + "/" + numberOfEmployeesToRemove + "): ");
            int employeeId = sc.nextInt();
            if (employeeId == 0) {
                System.out.println("Operation canceled.");
                return;
            }

            employeeIdsToRemove.add(employeeId);
        }

        // Remove selected employees from the project
        List<Employee> updatedEmployees = projectEmployees.stream().filter(employee -> !employeeIdsToRemove.contains(employee.getEmployeeId())).toList();
        project.setEmployees(updatedEmployees);
        projectDAO.updateProject(project);
        System.out.println("Employees removed from the project successfully.");
    }
}