/*
 * $Id: SecurityServiceImpl.java,v 1.3 2005/06/22 16:03:40 asv Exp $
 */

package com.asv.jfotki.business.services.impl;

import com.asv.jfotki.business.services.SecurityService;
import com.asv.jfotki.business.dao.SecurityDAO;
import com.asv.jfotki.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * @author Sergey Aleksandrov
 */
public class SecurityServiceImpl implements SecurityService {

    private SecurityDAO securityDAO;

    public void setSecurityDAO(SecurityDAO securityDAO) {
        this.securityDAO = securityDAO;
    }

    public boolean isAlbumOwner(String username, int albumId) {
        String selectedUsername;
        try {
            User albumOwner = securityDAO.getAlbumOwner(albumId);
            selectedUsername = albumOwner != null ? albumOwner.getUsername() : null;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }

        if (selectedUsername != null && selectedUsername.equals(username)) {
            return true;
        } else {
            return false;
        }
    }
}
