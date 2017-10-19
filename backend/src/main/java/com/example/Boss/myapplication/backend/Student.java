package com.example.Boss.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class Student {
    @Id
    Long id;
    String name;
    String lastName;

    public Student() {}

    public Student(Long pId, String pName, String pLastName) {
        id = pId;
        name = pName;
        lastName = pLastName;
    }

//    public Student(String pName, String pLastName) {
//        name = pName;
//        lastName = pLastName;
//    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}