package com.quintor.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

public class UserModel {
    @Id
    private int id;
    @Id
    private int role_ID;
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    private String email;
    private String password;

    public UserModel(int id, int role_ID, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.role_ID = role_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_ID() {
        return role_ID;
    }

    public void setRole_ID(int role_ID) {
        this.role_ID = role_ID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
