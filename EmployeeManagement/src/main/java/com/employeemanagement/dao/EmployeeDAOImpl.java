package com.employeemanagement.dao;

import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Phone;
import com.employeemanagement.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final EntityManager em;

    public EmployeeDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createEmployee(Employee employee, List<String> phoneNumbers) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.persist(employee);
            // Save phone numbers
            for (String phoneNumber : phoneNumbers) {
                Phone phone = new Phone(phoneNumber);
                phone.setEmployee(employee);
                em.persist(phone);
                employee.getPhones().add(phone);
            }

            et.commit();
            System.out.println("Employee Created Successfully");

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addPhoneNumbersToEmployee(Employee employee, List<String> phoneNumbers) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            // Add new phone numbers
            for (String phoneNumber : phoneNumbers) {
                Phone phone = new Phone(phoneNumber);
                phone.setEmployee(employee);
                em.persist(phone);
                employee.getPhones().add(phone);
            }

            et.commit();
            System.out.format("New %d phone numbers added successfully\n", phoneNumbers.size());

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;

        try {
            employee = em.find(Employee.class, employeeId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = null;

        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e", Employee.class);
            employees = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public void updateEmployee(Employee employee) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            em.merge(employee);
            et.commit();

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            Employee employee = em.find(Employee.class, employeeId);
            if (employee != null) {
                em.remove(employee);
                et.commit();
                System.out.println(employee.getEmployeeName() + " Removed Successfully");
            }

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deletePhoneNumbersFromEmployee(Employee employee, List<Integer> phoneIds) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        try {
            // Remove specified phone numbers
            for (int phoneId : phoneIds) {
                Phone phone = em.find(Phone.class, phoneId);
                if (phone != null && employee.getPhones().contains(phone)) {
                    em.remove(phone);
                    employee.getPhones().remove(phone);
                }
            }

            et.commit();
            System.out.format("%d phone numbers deleted successfully\n", phoneIds.size());

        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        List<Employee> employees = null;

        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e WHERE e.employeeName = :name", Employee.class);
            query.setParameter("name", name);
            employees = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByCity(String city) {
        List<Employee> employees = null;

        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e WHERE e.address.city = :city", Employee.class);
            query.setParameter("city", city);
            employees = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByProject(Project project) {
        List<Employee> employees = null;

        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee AS e JOIN e.projects AS pr WHERE pr = :project", Employee.class);
            query.setParameter("project", project);
            employees = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
}