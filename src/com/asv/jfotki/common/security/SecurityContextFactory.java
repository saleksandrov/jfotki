/*
 * securityContextFactory$
 */
 
/**
 * 
 * @author Sergey Aleksandrov 
 */
package com.asv.jfotki.common.security;

import com.asv.jfotki.model.User;

public class SecurityContextFactory {

    private static ThreadLocal contextHolder = new ThreadLocal();

    public static void createContext(User user, SecurityContextCreator securityContextCreator) {
        securityContextCreator.setUser(user);
        contextHolder.set(securityContextCreator);
    }

    public static SecurityContext getContext() {
        return (SecurityContext) contextHolder.get();
    }

}

