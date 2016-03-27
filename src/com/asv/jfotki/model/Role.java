/*
 * $Id: Role.java,v 1.3 2005/05/18 11:39:06 asv Exp $
 */

package com.asv.jfotki.model;

/**
 * @author Sergey Aleksandrov
 */
public class Role {

    public static final Role REGISTERED_ROLE = new Role();
    public static final Role ADMIN_ROLE = new Role();

    static {
        REGISTERED_ROLE.setName("jfotki-registered");
    }
    static {
        ADMIN_ROLE.setName("jfotki-admin");
    }


    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
