/*
 * $Id: UserDAO.java,v 1.1 2005/01/24 10:14:35 asv Exp $
 */

package com.asv.jfotki.business.dao;

import com.asv.jfotki.model.User;

/**
 * @author Sergey Aleksandrov
 */
public interface UserDAO {

    public User getUser(String username);

    public void create(User user);


}
