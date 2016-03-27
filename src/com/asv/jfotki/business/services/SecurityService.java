/*
 * $Id: SecurityService.java,v 1.1 2005/01/24 10:14:37 asv Exp $
 */

package com.asv.jfotki.business.services;

/**
 * @author Sergey Aleksandrov
 */
public interface SecurityService {

    public boolean isAlbumOwner(String username, int albumId);
    
}
