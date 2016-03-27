/*
 * $Id: ApplicationSecurityManager.java,v 1.1 2005/06/22 16:02:07 asv Exp $
 */

package com.asv.jfotki.common.security;

/**
 * @author Sergey Aleksandrov
 */
public interface ApplicationSecurityManager {
    String getCurrentPrincipalName();

    boolean isMemberOfRole(String roleName);

    boolean isAlbumOwner(int albumId);
}
