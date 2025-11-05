package com.teddykavooh.uima.model;

// User Model
public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    // Constructor
    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    //Default Constructor
    public User() {}

    // Getter methods (Data Encapsulation)
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
