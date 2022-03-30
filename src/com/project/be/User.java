package com.project.be;

import java.time.LocalDate;
import java.util.Date;

public abstract class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private String email;
    private Date birthDate;

    private int phoneNumber;

    public User(int id, String firstName, String lastName,String userName,String passWord, String email,Date birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName=userName;
        this.passWord=passWord;
        this.email=email;
        this.birthDate = birthDate;
    }

    public User(int id, String firstName, String lastName, String email,int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.phoneNumber = phoneNumber;
    }
    public User(int id, String firstName, String lastName,String userName,String passWord, String email,int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName=userName;
        this.passWord=passWord;
        this.email=email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
