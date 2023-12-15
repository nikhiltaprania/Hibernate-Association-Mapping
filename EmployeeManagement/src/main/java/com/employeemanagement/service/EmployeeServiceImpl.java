package com.employeemanagement.service;

import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.dao.ProjectDAO;
import com.employeemanagement.model.Address;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Phone;
import com.employeemanagement.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeServiceImpl implements EmployeeService {
    private final Scanner sc;
    private final EmployeeDAO employeeDAO;
    private final ProjectDAO projectDAO;

    public EmployeeServiceImpl(Scanner sc, EmployeeDAO employeeDAO, ProjectDAO projectDAO) {
        this.sc = sc;
        this.employeeDAO = employeeDAO;
        this.projectDAO = projectDAO;
    }

    @Override
    public void createEmployee() {
        System.out.println("Enter Employee's Details");
        System.out.print("Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        System.out.print("City: ");
        String city = sc.nextLine();
        System.out.print("State: ");
        String state = sc.nextLine();
        System.out.print("Pin Code: ");
        int pinCode = sc.nextInt();

        Employee employee = new Employee(name);
        Address address = new Address(city, state, pinCode);
        employee.setAddress(address);

        employeeDAO.createEmployee(employee, addNewNumbers());
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeDAO.getEmployeeById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public void updateEmployee() {
        System.out.println("Enter the ID of the employee to update:");
        int id = sc.nextInt();
        Employee existingEmployee = employeeDAO.getEmployeeById(id);

        if (existingEmployee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }

        boolean flag = true;

        while (flag) {
            System.out.println("\nWhat Do You Wanna Do ?");
            System.out.println("1. Update Your Name\n2. Update Your Address Details\n3. Take A Project");
            System.out.print("4. Remove Project\n5. Your Phone Numbers\n0. Back\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> {
                    System.out.print("\nEnter the new name: ");
                    sc.nextLine();
                    String newName = sc.nextLine();
                    existingEmployee.setEmployeeName(newName);

                    employeeDAO.updateEmployee(existingEmployee);
                    System.out.println("Employee renamed successfully.");
                }
                case 2 -> {
                    System.out.println("\nEnter new address details");
                    System.out.print("City: ");
                    sc.nextLine();
                    String city = sc.nextLine();
                    System.out.print("State: ");
                    String state = sc.nextLine();
                    System.out.print("Pin Code: ");
                    int pinCode = sc.nextInt();

                    Address address = new Address(city, state, pinCode);
                    existingEmployee.setAddress(address);
                    System.out.println("Address Updated Successfully");
                }

                case 3 -> assignProjectToEmployee(existingEmployee);

                case 4 -> removeProjectFromEmployee(existingEmployee);

                case 5 -> phoneOperation(existingEmployee);

                case 0 -> flag = false;

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    @Override
    public void deleteEmployee() {
        System.out.println("Enter the ID of the employee to delete:");
        int id = sc.nextInt();
        Employee existingEmployee = employeeDAO.getEmployeeById(id);

        if (existingEmployee == null) {
            System.out.println("Employee not found with ID: " + id);
            return;
        }

        employeeDAO.deleteEmployee(id);
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return employeeDAO.getEmployeesByName(name);
    }

    @Override
    public List<Employee> getEmployeesByCity(String city) {
        return employeeDAO.getEmployeesByCity(city);
    }

    @Override
    public List<Employee> getEmployeesByProject(Project project) {
        return employeeDAO.getEmployeesByProject(project);
    }

    private List<String> addNewNumbers() {
        System.out.println("\nHow Many Phone Numbers do want to add");
        int limit = sc.nextInt();
        System.out.format("Enter %d phone numbers of 10 digits\n", limit);

        List<String> phones = new ArrayList<>();
        for (int i = 1; i <= limit; ++i) {
            System.out.format("Phone %d: ", i);
            String phoneNumber = sc.next();

            if (phoneNumber.length() == 10) {
                phones.add(phoneNumber);
            } else {
                System.out.println("Number should be of 10 digits");
                --i;
            }
        }

        return phones;
    }

    private void phoneOperation(Employee employee) {
        List<Phone> phones = employee.getPhones();
        if (phones.isEmpty()) {
            System.out.println("No phone numbers are available");
            return;
        }

        while (true) {
            System.out.println("\n1. View All Phone numbers\n2. Add new phone numbers");
            System.out.print("3. Delete a phone number\n0. Back\nEnter: ");

            switch (sc.nextInt()) {
                case 1 ->
                        phones.forEach(phone -> System.out.format("ID: %d, Number: %s\n", phone.getPhoneId(), phone.getPhoneNumber()));

                case 2 -> employeeDAO.addPhoneNumbersToEmployee(employee, addNewNumbers());

                case 3 -> {
                    System.out.println("All Phone numbers are");
                    phones.forEach(phone -> System.out.format("ID: %d, Number: %s\n", phone.getPhoneId(), phone.getPhoneNumber()));
                    System.out.println("Enter IDs of phone numbers to delete separated by space");
                    sc.nextLine();
                    String idString = sc.nextLine();

                    System.out.println("Entered Id String");
                    employeeDAO.deletePhoneNumbersFromEmployee(employee, convertStringToList(idString));
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid Input ! Try Again");
            }
        }
    }

    private List<Integer> convertStringToList(String idString) {
        // Split the string into individual ID strings
        String[] idArray = idString.split("\\s+");

        // Convert the array of ID strings to a List of Integers
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            try {
                Integer parsedId = Integer.parseInt(id);
                idList.add(parsedId);

            } catch (NumberFormatException e) {
                System.err.println("Invalid ID: " + id);
            }
        }

        return idList;
    }

    private void assignProjectToEmployee(Employee employee) {
        // Display available projects
        List<Project> projects = projectDAO.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("Sorry! No Projects Available For Now");
            return;
        }

        System.out.println("Available Projects:");
        projects.forEach(project -> System.out.println("ID: " + project.getProjectId() + ", Name: " + project.getProjectName()));

        // Select a project
        System.out.println("Enter the ID of the project to assign:");
        int projectId = sc.nextInt();
        Project selectedProject = projectDAO.getProjectById(projectId);
        if (selectedProject == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Assign the selected project to the selected employee
        employee.getProjects().add(selectedProject);
        employeeDAO.updateEmployee(employee);
        System.out.println("Project assigned to " + employee.getEmployeeName() + " successfully.");
    }

    private void removeProjectFromEmployee(Employee employee) {
        // Display projects assigned to the selected employee
        List<Project> assignedProjects = employee.getProjects();
        if (assignedProjects.isEmpty()) {
            System.out.println("No Projects Assigned To You " + employee.getEmployeeName());
            return;
        }

        System.out.println("Projects assigned to " + employee.getEmployeeName() + " are:");
        assignedProjects.forEach(project -> System.out.println("ID: " + project.getProjectId() + ", Name: " + project.getProjectName()));

        // Select a project to remove
        System.out.println("Enter the ID of the project to remove from the selected employee:");
        int projectId = sc.nextInt();
        Project projectToRemove = projectDAO.getProjectById(projectId);
        if (projectToRemove == null) {
            System.out.println("Project not found with ID: " + projectId);
            return;
        }

        // Remove the selected project from the selected employee
        employee.getProjects().remove(projectToRemove);
        employeeDAO.updateEmployee(employee);
        System.out.println("Project removed from " + employee.getEmployeeName() + " successfully.");
    }
}