/*
 * $Id: SecurityUtil.java,v 1.2 2005/04/10 14:02:47 asv Exp $
 */

package com.asv.jfotki.web.util;

import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.GrantedAuthority;
import net.sf.acegisecurity.context.Context;
import net.sf.acegisecurity.context.ContextHolder;
import net.sf.acegisecurity.context.security.SecureContext;
import net.sf.acegisecurity.providers.dao.User;

/**
 * @author Sergey Aleksandrov
 */
public class SecurityUtil {

    private User user;

    public SecurityUtil() {
        user = getCurrentPrincipal();
    }

    public String getCurrentPrincipalName() {
        if (user != null) {
            return user.getUsername();
        }
        return null;
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

    public boolean isMemberOfRole(String roleName) {
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


}
