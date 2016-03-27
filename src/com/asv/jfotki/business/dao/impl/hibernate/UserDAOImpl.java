/*
 * $Id: UserDAOImpl.java,v 1.2 2005/04/12 11:54:46 asv Exp $
 */

package com.asv.jfotki.business.dao.impl.hibernate;

import com.asv.jfotki.business.dao.UserDAO;
import com.asv.jfotki.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

    public User getUser(String username) {
        List users = getHibernateTemplate().find("from User user where user.username = ?", username);
        if (users != null && users.size() == 1)
            return (User) users.get(0);
        else
            return null;
    }

    public void create(User user) {
        getHibernateTemplate().save(user);

        // This code is needed for Oracle in order to Service Layer could catch DataIntegrityViolationException.
        // Hibernate doesn't perform really INSERT because it is possible to get an id through SEQUENCE.
        getHibernateTemplate().flush();
    }
}
