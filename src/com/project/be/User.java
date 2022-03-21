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
    private String address;
    private int phoneNumber;
    private Date birthDate;

    public User(int id, String firstName, String lastName,String userName,String passWord, String email,String address,int phoneNumber,Date birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName=userName;
        this.passWord=passWord;
        this.email=email;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.birthDate = birthDate;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
