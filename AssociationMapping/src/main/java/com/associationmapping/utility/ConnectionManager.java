package com.associationmapping.utility;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionManager {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("connected");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public static void closeFactory() {
        if(emf.isOpen()) {
            emf.close();
        }
    }
}