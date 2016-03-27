/*
 * $Id: RoleDAOImpl.java,v 1.2 2005/04/10 14:02:44 asv Exp $
 */

package com.asv.jfotki.business.dao.impl;

import com.asv.jfotki.business.dao.RoleDAO;
import com.asv.jfotki.model.User;
import com.asv.jfotki.model.Role;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class RoleDAOImpl extends JdbcDaoSupport implements RoleDAO {

    public static final String INSERT_ROLE_USER = "INSERT INTO security_role_user (a_user_id, a_role_id) " +
                                                         "values (?,(select a_id from security_role where a_rolename = ?))";

    public void attachRoleToUser(User user) {
        List roles = user.getRoles();
        if (roles.size() > 0) {
            Role role = (Role)roles.get(0);

            getJdbcTemplate().update(INSERT_ROLE_USER, new Object[] { new Integer(user.getId()), role.getName() });
        }
    }

    public Role getRoleByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void create(Role role) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
