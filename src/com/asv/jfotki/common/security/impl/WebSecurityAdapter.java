/*
 * $Id: WebSecurityAdapter.java,v 1.2 2005/06/22 16:45:41 asv Exp $
 */

package com.asv.jfotki.common.security.impl;

import net.sf.acegisecurity.providers.dao.User;
import net.sf.acegisecurity.context.Context;
import net.sf.acegisecurity.context.ContextHolder;
import net.sf.acegisecurity.context.security.SecureContext;
import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.GrantedAuthority;

/**
 * @author Sergey Aleksandrov
 */
public class WebSecurityAdapter implements com.asv.jfotki.common.security.SecurityAdapter {

//    private User user;

//    public SecurityUtil() {
//        user = getCurrentPrincipal();
//    }


    public String getCurrentPrincipalName() {
        User user = getCurrentPrincipal();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    public boolean isMemberOfRole(String roleName) {
        User user = getCurrentPrincipal();
        if (user != null) {
            GrantedAuthority[] authorities = user.getAuthorities();
            for (int i = 0; i < authorities.length; i++) {
                GrantedAuthority authority = authorities[i];
                if (authority.getAuthority().equals(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private User getCurrentPrincipal() {
        Context context = ContextHolder.getContext();
        if (context != null) {
            if (context instanceof SecureContext) {
        	    SecureContext sc = (SecureContext) context;

                Authentication auth = sc.getAuthentication();
                if (auth != null) {
                    return (User) auth.getPrincipal();
                }
            }
        }
        return null;
    }
    
}
