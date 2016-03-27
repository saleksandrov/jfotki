/*
 * $Id: RoleDAOImpl.java,v 1.1 2005/04/10 14:02:43 asv Exp $
 */

package com.asv.jfotki.business.dao.impl.hibernate;

import com.asv.jfotki.business.dao.RoleDAO;
import com.asv.jfotki.model.User;
import com.asv.jfotki.model.Role;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class RoleDAOImpl extends HibernateDaoSupport implements RoleDAO {

    public static final String INSERT_ROLE_USER = "INSERT INTO security_role_user (a_user_id, a_role_id) " +
                                                         "values (?,(select a_id from security_role where a_rolename = ?))";


    public void attachRoleToUser(User user) {
//        List roles = user.getRoles();
//        if (roles.size() > 0) {
//            Role role = (Role)roles.get(0);
//
//            getJdbcTemplate().update(INSERT_ROLE_USER, new Object[] { new Integer(user.getId()), role.getName() });
//        }
    }

    public Role getRoleByName(String name) {
        List roles = getHibernateTemplate().find("from Role role where role.name =?", name);
        if (roles.size() > 0) {
            Role role = (Role)roles.get(0);
            return role;
        }
        return null;

    }

    public void create(Role role) {
        getHibernateTemplate().save(role);
    }
}
