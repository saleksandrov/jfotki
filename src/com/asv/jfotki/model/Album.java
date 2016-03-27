/*
 * $Id: Album.java,v 1.2 2005/01/24 10:14:40 asv Exp $
 */

package com.asv.jfotki.model;

/**
 * Model class which contains Album information.
 *
 * @author Sergey Aleksandrov
 */
public class Album implements java.io.Serializable {

    private int id;
    //private String creator;
    private String name;

    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
