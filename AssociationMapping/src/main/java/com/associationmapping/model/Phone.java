package com.associationmapping.model;

import jakarta.persistence.*;

@Entity
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Phone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Phone{" + "id=" + id + ", phoneNumber='" + phoneNumber + '\'' + ", person=" + person + '}';
    }
}
