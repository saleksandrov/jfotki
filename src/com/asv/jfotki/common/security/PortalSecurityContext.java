/*
 * securityContext$
 */
 
/**
 * 
 * @author Sergey Aleksandrov 
 */
package com.asv.jfotki.common.security;

import com.asv.jfotki.model.User;

public class PortalSecurityContext implements SecurityContext, SecurityContextCreator {

    private User user;

    public PortalSecurityContext() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

