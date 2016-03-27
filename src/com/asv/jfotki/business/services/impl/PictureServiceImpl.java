/*
 * $Id: PictureServiceImpl.java,v 1.3 2005/01/24 10:14:36 asv Exp $
 */

package com.asv.jfotki.business.services.impl;

import com.asv.jfotki.business.dao.AlbumDAO;
import com.asv.jfotki.business.dao.PictureDAO;
import com.asv.jfotki.business.dao.UserDAO;
import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.common.exception.ApplicationRuntimeException;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import com.asv.jfotki.model.User;
import com.asv.jpictures.ImageManager;

import java.io.IOException;
import java.util.List;

/**
 * An impementation of {@link PictureService}
 *
 * @author Sergey Aleksandrov
 */
public class PictureServiceImpl implements PictureService {

    private PictureDAO pictureDAO;
    private AlbumDAO albumDAO;
    private UserDAO userDAO;

    public void setPictureDAO(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    public void setAlbumDAO(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createAlbum(Album album, String username) {
        User user = userDAO.getUser(username);
        if (user == null)
            throw new ApplicationRuntimeException("User not found!");
        album.setUser(user);
        albumDAO.create(album);
    }

    public List getAllAlbums() {
        return albumDAO.getAllAlbums();
    }

     public List getAlbumsByUserName(String username) {
         return albumDAO.getAlbumsByUserName(username);
     }

    public void createPicture(Picture picture) {
        ImageManager imageManager = new ImageManager();
        byte[] smallPictureBytes;
        try {
            smallPictureBytes = imageManager.resizeImage(picture.getBigPicture());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        picture.setSmallPicture(smallPictureBytes);
        pictureDAO.create(picture);

    }

    public PictureInfo getPicture(int pictureId) {
        return pictureDAO.getPicture(pictureId);
    }

    public PictureInfo nextPicture(int pictureId, int albumId) {
        return pictureDAO.nextPicture(pictureId, albumId);
    }

    public List getPictures(int albumId) {
        return pictureDAO.getPictures(albumId);
    }

    public List getAllPictures() {
        return pictureDAO.getAllPictures();
    }

    public byte[] getBigImage(int pictureId) {
        return pictureDAO.getBigImage(pictureId);
    }

    public byte[] getSmallImage(int pictureId) {
        return pictureDAO.getSmallImage(pictureId);
    }

    public void deletePicture(int pictureId) {
        pictureDAO.delete(pictureId);
    }

    public Album getAlbum(int id) {
        return albumDAO.getAlbum(id);
    }
}
