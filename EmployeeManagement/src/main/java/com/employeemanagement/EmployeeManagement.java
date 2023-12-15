package com.employeemanagement;

import com.employeemanagement.dao.EmployeeDAO;
import com.employeemanagement.dao.EmployeeDAOImpl;
import com.employeemanagement.dao.ProjectDAO;
import com.employeemanagement.dao.ProjectDAOImpl;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.EmployeeServiceImpl;
import com.employeemanagement.service.ProjectService;
import com.employeemanagement.service.ProjectServiceImpl;
import com.employeemanagement.utility.ConnectionManager;
import com.employeemanagement.utility.RunApp;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class EmployeeManagement {
    public static void main(String[] args) {
        EntityManager em = ConnectionManager.getEntityManager();
        Scanner sc = new Scanner(System.in);

        EmployeeDAO employeeDAO = new EmployeeDAOImpl(em);
        ProjectDAO projectDAO = new ProjectDAOImpl(em);

        EmployeeService employeeService = new EmployeeServiceImpl(sc, employeeDAO, projectDAO);
        ProjectService projectService = new ProjectServiceImpl(sc, projectDAO, employeeDAO);

        RunApp runApp = new RunApp(sc, employeeService, projectService);
        while (true) {
            System.out.println("\nPerform Operation On");
            System.out.println("1. Person\n2. Project");
            System.out.print("0. Exit\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> runApp.manageEmployee();

                case 2 -> runApp.manageProject();

                case 0 -> {
                    sc.close();
                    em.close();
                    ConnectionManager.closeFactory();
                    System.out.println("Existing Application...\nGood Bye !");
                    return;
                }

                default -> System.out.println("Invalid Choice ! Try Again...");
            }
        }
    }
}