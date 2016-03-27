/*
 * $Id: SecurityDAOImpl.java,v 1.1 2005/04/10 14:02:43 asv Exp $
 */

package com.asv.jfotki.business.dao.impl.hibernate;

import com.asv.jfotki.business.dao.SecurityDAO;
import com.asv.jfotki.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.sql.SQLException;

/**
 * @author Sergey Aleksandrov
 */
public class SecurityDAOImpl extends HibernateDaoSupport implements SecurityDAO {

    public User getAlbumOwner(final int albumId) {
        return (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery("select album.user from Album album where album.id = ?").setInteger(0, albumId).uniqueResult();
            }
        });
    }
}
