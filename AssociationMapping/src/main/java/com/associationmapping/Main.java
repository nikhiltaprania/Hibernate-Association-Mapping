package com.associationmapping;

import com.associationmapping.dao.PersonDAO;
import com.associationmapping.dao.PersonDAOImpl;
import com.associationmapping.dao.ProjectDAO;
import com.associationmapping.dao.ProjectDAOImpl;
import com.associationmapping.service.PersonService;
import com.associationmapping.service.PersonServiceImpl;
import com.associationmapping.service.ProjectService;
import com.associationmapping.service.ProjectServiceImpl;
import com.associationmapping.utility.ConnectionManager;
import com.associationmapping.utility.RunApp;
import jakarta.persistence.EntityManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EntityManager em = ConnectionManager.getEntityManager();

        PersonDAO personDAO = new PersonDAOImpl(em);
        ProjectDAO projectDAO = new ProjectDAOImpl(em);

        PersonService personService = new PersonServiceImpl(sc, personDAO, projectDAO);
        ProjectService projectService = new ProjectServiceImpl(sc, projectDAO, personDAO);

        RunApp runApp = new RunApp(sc, personService, projectService);

        while (true) {
            System.out.println("\nPerform Operation On");
            System.out.println("1. Person\n2. Project");
            System.out.print("0. Exit\nEnter: ");

            switch (sc.nextInt()) {
                case 1 -> runApp.managePerson();

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