package com.educom.restclient.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class EducomUser implements Serializable {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private boolean active;

    private Set<Role> roles = new HashSet<>();

    public EducomUser() {
    }

    public EducomUser(String userName, String email, String password, boolean active) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.active = active;

    }

    public EducomUser(String userName, String email, String password, boolean active, Set<Role> roles) {

        this.userName = userName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "EducomUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                '}';
    }
}
