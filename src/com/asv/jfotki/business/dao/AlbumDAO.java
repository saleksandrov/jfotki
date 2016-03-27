/*
 * $Id: AlbumDAO.java,v 1.2 2005/01/24 10:14:34 asv Exp $
 */

package com.asv.jfotki.business.dao;

import com.asv.jfotki.model.Album;

import java.util.List;

/**
 * DAO object for manipulating with {@link Album}.
 *
 * @author Sergey Aleksandrov
 */
public interface AlbumDAO {

    /**
     * Create an {@link Album}
     *
     * @param album
     */
    public void create(Album album);

    public List getAllAlbums();

    public List getAlbumsByUserName(String username);

    public Album getAlbum(int id);

}
