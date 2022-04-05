package com.revature.proj1.users;

import java.io.Serializable;


public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fname;
    private String lname;
    private boolean is_active = false;
    private String role_ID;
    
    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String email, String fname, String lname, String role_ID) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.role_ID = role_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {return email;}

    public String getFname() {return fname;}

    public String getLname() {return lname;}

    public String getRole_ID() {return role_ID;}

    public boolean isIs_active() {return is_active;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {this.email = email;}

    public void setFname(String fname) {this.fname = fname;}

    public void setLname(String lname) {this.lname = lname;}

    public void setIs_active(boolean is_active) {this.is_active = is_active;}

    public void setRole_ID(String role_ID) {this.role_ID = role_ID;}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ",e-mail='" + email + '\'' +
                ", first name='" + fname + '\'' +
                ",last name='" + lname + '\'' +
                ", role_ID='" + role_ID + '\'' +
                '}';
    }
}


