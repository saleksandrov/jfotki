/*
 * $Id: PictureDAO.java,v 1.3 2005/01/24 10:14:35 asv Exp $
 */

package com.asv.jfotki.business.dao;

import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;

import java.util.List;

/**
 * DAO object for manipulating with {@link Picture}.
 *
 * @author Sergey Aleksandrov
 */
public interface PictureDAO {

    public void create(Picture picture);

    public List getPictures(int albumId);

    public List getAllPictures();

    public PictureInfo getPicture(int pictureId);

    public PictureInfo nextPicture(int pictureId, int albumId);

    public byte[] getBigImage(int pictureId);

    public byte[] getSmallImage(int pictureId);

    public void delete(int pictureId);

}
