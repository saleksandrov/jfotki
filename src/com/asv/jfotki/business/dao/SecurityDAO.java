/*
 * $Id: SecurityDAO.java,v 1.1 2005/04/10 14:02:44 asv Exp $
 */

package com.asv.jfotki.business.dao;

import com.asv.jfotki.model.User;

/**
 * @author Sergey Aleksandrov
 */
public interface SecurityDAO {

    public User getAlbumOwner(int albumId);

}
