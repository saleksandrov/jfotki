/*
 * $Id: RoleDAO.java,v 1.2 2005/04/10 14:02:44 asv Exp $
 */

package com.asv.jfotki.business.dao;

import com.asv.jfotki.model.User;
import com.asv.jfotki.model.Role;

/**
 * @author Sergey Aleksandrov
 */
public interface RoleDAO {

    public void attachRoleToUser(User user);

    public Role getRoleByName(String name);

    public void create(Role role);

}
