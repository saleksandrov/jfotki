/*
 * $Id: User.java,v 1.2 2005/03/09 07:49:35 asv Exp $
 */

package com.asv.jfotki.model;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Sergey Aleksandrov
 */
public class User implements java.io.Serializable{

    private int id;
    private String username;
    private transient String password;
    private boolean enabled;
    private String email;
    private String fullName;
    private List roles = new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

}
