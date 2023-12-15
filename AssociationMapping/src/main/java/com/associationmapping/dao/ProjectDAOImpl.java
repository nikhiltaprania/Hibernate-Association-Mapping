package com.associationmapping.dao;

import com.associationmapping.model.Person;
import com.associationmapping.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {
    private final EntityManager em;

    public ProjectDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createProject(Project project) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.persist(project);
            et.commit();
            System.out.println("Project Created Successfully");

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Project getProjectById(int id) {
        Project project = null;

        try {

            project = em.find(Project.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = null;

        try {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project p", Project.class);
            projects = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }

    @Override
    public void updateProject(Project project) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.merge(project);
            et.commit();

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(int id) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Project project = em.find(Project.class, id);
            if (project != null) {
                // Use native SQL to delete records from person_project table
                em.createNativeQuery("DELETE FROM person_project AS pp WHERE pp.project_id = :projectId").setParameter("projectId", id).executeUpdate();
                // Now you can safely remove the project
                em.remove(project);
                et.commit();
                System.out.println(project.getProjectName() + " Removed Successfully");
            }

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> getProjectsByMember(Person person) {
        List<Project> projects = null;

        try {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project AS p JOIN p.people AS m WHERE m = :person", Project.class);
            query.setParameter("person", person);
            projects = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }
}