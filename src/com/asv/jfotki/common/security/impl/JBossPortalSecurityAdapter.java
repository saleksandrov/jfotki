/*
 * $Id$
 */

package com.asv.jfotki.common.security.impl;

import com.asv.jfotki.common.security.SecurityAdapter;
import com.asv.jfotki.common.security.SecurityContextFactory;
import com.asv.jfotki.common.security.SecurityContext;
import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.model.Role;

/**
 * @author Sergey Aleksandrov
 */
public class JBossPortalSecurityAdapter implements SecurityAdapter {

    public String getCurrentPrincipalName() {
        SecurityContext context = SecurityContextFactory.getContext();
        if (context != null) {
            return context.getUser().getUsername();
        }
        LogFactory.web.error("Context is NULL.");
        return null;
    }

    public boolean isMemberOfRole(String roleName) {
        if (Role.ADMIN_ROLE.equals(roleName)) {
            return SecurityContextFactory.getContext().getUser().getRoles().contains(Role.ADMIN_ROLE);
        }
        return false;
    }
}
