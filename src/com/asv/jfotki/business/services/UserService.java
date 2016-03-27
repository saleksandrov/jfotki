/*
 * $Id: UserService.java,v 1.2 2005/04/10 14:02:45 asv Exp $
 */

package com.asv.jfotki.business.services;

import com.asv.jfotki.common.exception.UserExistsException;
import com.asv.jfotki.model.User;
import com.asv.jfotki.model.Role;

/**
 * @author Sergey Aleksandrov
 */
public interface UserService {

    public User getUser(String username);
    
    public void register(User user) throws UserExistsException;

    public void create(User user) throws UserExistsException;

    public void createRole(Role role);

    public Role getRoleByName(String name);
}
