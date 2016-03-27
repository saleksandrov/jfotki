/*
 * $Id: UserServiceImpl.java,v 1.3 2005/04/10 14:02:44 asv Exp $
 */

package com.asv.jfotki.business.services.impl;

import com.asv.jfotki.business.dao.UserDAO;
import com.asv.jfotki.business.dao.RoleDAO;
import com.asv.jfotki.business.services.UserService;
import com.asv.jfotki.common.exception.UserExistsException;
import com.asv.jfotki.model.User;
import com.asv.jfotki.model.Role;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Sergey Aleksandrov
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private RoleDAO roleDAO;


    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    
    public User getUser(String username) {
        return userDAO.getUser(username);
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(User user) throws UserExistsException {
        Role role = roleDAO.getRoleByName(Role.REGISTERED_ROLE.getName());
        user.addRole(role);
        create(user);
    }

    public void create(User user) throws UserExistsException {
        try {
            userDAO.create(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsException("User exits", e);
        }
    }



    public void createRole(Role role) {
        this.roleDAO.create(role);
    }

    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }
}
