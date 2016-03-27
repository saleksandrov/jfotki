/*
 * $Id: PictureService.java,v 1.3 2005/01/24 10:14:37 asv Exp $
 */

package com.asv.jfotki.business.services;

import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;

import java.util.List;

/**
 * Consolidates all needed method in one PictureService interface
 *
 * @author Sergey Aleksandrov
 */
public interface PictureService {

    public void createPicture(Picture picture);

    public PictureInfo getPicture(int pictureId);

    public PictureInfo nextPicture(int pictureId, int albumId);

    public List getPictures(int albumId);

    public List getAllPictures();

    public List getAlbumsByUserName(String username);

    public void createAlbum(Album album, String username);

    public List getAllAlbums();

    public byte[] getBigImage(int pictureId);

    public byte[] getSmallImage(int pictureId);

    public void deletePicture(int pictureId);

    public Album getAlbum(int id);
}
