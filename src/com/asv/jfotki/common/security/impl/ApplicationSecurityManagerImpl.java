/*
 * $Id: ApplicationSecurityManagerImpl.java,v 1.1 2005/06/22 16:02:07 asv Exp $
 */

package com.asv.jfotki.common.security.impl;

import com.asv.jfotki.business.services.SecurityService;
import com.asv.jfotki.common.security.SecurityAdapter;
import com.asv.jfotki.common.security.*;
import com.asv.jfotki.model.Role;

/**
 * @author Sergey Aleksandrov
 */
public class ApplicationSecurityManagerImpl implements com.asv.jfotki.common.security.ApplicationSecurityManager {

    private SecurityAdapter securityAdapter;
    private SecurityService securityService;

    public void setSecurityAdapter(SecurityAdapter securityAdapter) {
        this.securityAdapter = securityAdapter;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public String getCurrentPrincipalName() {
        return securityAdapter.getCurrentPrincipalName();
    }

    public boolean isMemberOfRole(String roleName) {
        return securityAdapter.isMemberOfRole(roleName);
    }

    public boolean isAlbumOwner(int albumId) {
        if (securityAdapter.isMemberOfRole(Role.ADMIN_ROLE.getName())) {
            return true;
        }
        return securityService.isAlbumOwner(getCurrentPrincipalName(), albumId);
    }
}
