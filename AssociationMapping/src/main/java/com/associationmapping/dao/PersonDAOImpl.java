package com.associationmapping.dao;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    private final EntityManager em;

    public PersonDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createPerson(Person person) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.persist(person);
            et.commit();
            System.out.println("Person Created Successfully");

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Person getPersonById(int id) {
        Person person = null;

        try {
            person = em.find(Person.class, id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> people = null;

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person AS p", Person.class);
            people = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    public void updatePerson(Person person) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.merge(person);
            et.commit();

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(int id) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            Person person = em.find(Person.class, id);
            if (person != null) {
                em.remove(person);
                et.commit();
                System.out.println(person.getName() + " Removed Successfully");
            }

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> getPersonsByName(String name) {
        List<Person> people = null;

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person AS p WHERE p.name = :name", Person.class);
            query.setParameter("name", name);
            people = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    public List<Person> getPersonsByCity(String city) {
        List<Person> people = null;

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person AS p WHERE p.address.city = :city", Person.class);
            query.setParameter("city", city);
            people = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    public List<Person> getPersonsByProject(Project project) {
        List<Person> people = null;

        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person AS p JOIN p.projects AS pr WHERE pr = :project", Person.class);
            query.setParameter("project", project);
            people = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return people;
    }
}