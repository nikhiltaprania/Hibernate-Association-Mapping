package com.employeemanagement.dao;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Project;
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
    public Project getProjectById(int projectId) {
        Project project = null;

        try {
            project = em.find(Project.class, projectId);

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
    public void deleteProject(int projectId) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            Project project = em.find(Project.class, projectId);
            if (project != null) {
                // Use native SQL to delete records from person_project table
                em.createNativeQuery("DELETE FROM employee_project AS ep WHERE ep.project_id = :projectId").setParameter("projectId", projectId).executeUpdate();
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
    public List<Project> getProjectsByEmployee(Employee employee) {
        List<Project> projects = null;

        try {
            TypedQuery<Project> query = em.createQuery("SELECT p FROM Project AS p JOIN p.employees AS e WHERE e = :employee", Project.class);
            query.setParameter("employee", employee);
            projects = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projects;
    }
}