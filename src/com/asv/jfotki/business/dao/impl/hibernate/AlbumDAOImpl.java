/*
 * $Id: AlbumDAOImpl.java,v 1.1 2005/04/10 14:02:43 asv Exp $
 */

package com.asv.jfotki.business.dao.impl.hibernate;

import com.asv.jfotki.business.dao.AlbumDAO;
import com.asv.jfotki.model.Album;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * An hibernate implementation of {@link com.asv.jfotki.business.dao.AlbumDAO} with help of Spring.
 *
 * @author Sergey Aleksandrov
 */
public class AlbumDAOImpl extends HibernateDaoSupport implements AlbumDAO {

    public List getAllAlbums() {
        return getHibernateTemplate().find("from Album album order by album.id");
    }

    public List getAlbumsByUserName(String username) {
        return getHibernateTemplate().find("from Album album where album.user.username = ? order by album.id", username);
    }

    public Album getAlbum(int id) {
        return (Album) getHibernateTemplate().get(Album.class, new Integer(id));
    }

    public void create(Album album) {
        getHibernateTemplate().save(album);
    }
}
