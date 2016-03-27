/*
 * $Id: PictureDAOImpl.java,v 1.1 2005/04/10 14:02:43 asv Exp $
 */

package com.asv.jfotki.business.dao.impl.hibernate;

import com.asv.jfotki.business.dao.PictureDAO;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import java.util.List;
import java.sql.SQLException;

/**
 * An hibernate implementation of {@link com.asv.jfotki.business.dao.PictureDAO} with help of Spring.
 *
 * @author Sergey Aleksandrov
 */
public class PictureDAOImpl extends HibernateDaoSupport implements PictureDAO {

    public void create(Picture picture) {
        getHibernateTemplate().save(picture);
    }

    public List getPictures(int albumId) {
       return getHibernateTemplate().find("from PictureInfo pi where pi.album.id = ?", new Integer(albumId));
    }

    /**
     * Used for export utility.
     *
     * @return
     */
    public List getAllPictures() {
       return getHibernateTemplate().find("from Picture");
    }

    public PictureInfo getPicture(int pictureId) {
        PictureInfo pictureInfo = (PictureInfo) getHibernateTemplate().get(PictureInfo.class, new Integer(pictureId));
        getHibernateTemplate().initialize(pictureInfo.getAlbum());
        return pictureInfo;
    }

    // TODO must be optimized
    public PictureInfo nextPicture(int pictureId,  int albumId) {
        List maxIdList  = getHibernateTemplate().find("select max(pi.id) from PictureInfo pi where pi.album.id = ?", new Object[] {new Integer(albumId)});
        Integer maxId = maxIdList != null && maxIdList.size() > 0 ? (Integer) maxIdList.get(0) : new Integer(0);

        Integer pictureIdForQuery;

        if (pictureId == maxId.intValue()) {
            List minIdList = getHibernateTemplate().find("select min(pi.id) from PictureInfo pi where pi.album.id = ?", new Object[] {new Integer(albumId)});
            pictureIdForQuery = minIdList != null && minIdList.size() > 0 ? (Integer) minIdList.get(0) : new Integer(0);
        }  else {
            List idList = getHibernateTemplate().find("select pi.id from PictureInfo pi where pi.album.id = ? and pi.id > ? order by pi.id",
                    new Object[] {new Integer(albumId), new Integer(pictureId)});
            pictureIdForQuery = idList != null && idList.size() > 0 ? (Integer) idList.get(0) : new Integer(pictureId);
        }

        return getPicture(pictureIdForQuery.intValue());
    }

    public byte[] getBigImage(final int pictureId)  {
        return (byte[]) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createQuery("select picture.bigPicture from Picture picture where picture.id = ?")
                        .setInteger(0, pictureId)
                        .uniqueResult();
            }
        });
    }

    public byte[] getSmallImage(final int pictureId) {
        return (byte[]) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createQuery("select picture.smallPicture from Picture picture where picture.id = ?")
                        .setInteger(0, pictureId)
                        .uniqueResult();
            }
        });
    }

    public void delete(int pictureId) {
        PictureInfo pictureInfo = (PictureInfo) getHibernateTemplate().load(PictureInfo.class, new Integer(pictureId));
        getHibernateTemplate().delete(pictureInfo);
    }

}
