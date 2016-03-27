/*
 * $Id: AlbumDAOTest.java,v 1.3 2005/04/10 14:04:41 asv Exp $
 */

package com.asv.jfotki.business;

import com.asv.AbstractTest;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.business.dao.AlbumDAO;
import com.asv.jfotki.business.dao.UserDAO;

import java.util.Calendar;
import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class AlbumDAOTest extends AbstractTest {

    private AlbumDAO albumDAO;
    private UserDAO userDAO;

    protected void setUp() throws Exception {
        super.setUp();
        albumDAO = (AlbumDAO) getBeanFactory().getBean("albumDAO");
        userDAO = (UserDAO) getBeanFactory().getBean("userDAO");
    }

    public void testCreate() {
        Calendar calendar = Calendar.getInstance();
        Album album = new Album();
        album.setName("JUnit test album: " + calendar.getTime());
        album.setUser(userDAO.getUser("admin"));
        albumDAO.create(album);
    }

    public void testGettingAllAlbums() {
        List albums = albumDAO.getAllAlbums();
        assertNotNull(albums);
        assertTrue(albums.size() > 0);
        assertTrue(albums.get(0) instanceof Album);
    }

    public void testGettingAlbumsByUserName() {
        List albums = albumDAO.getAlbumsByUserName("admin");
        assertNotNull(albums);
        assertTrue(albums.size() > 0);
        assertTrue(albums.get(0) instanceof Album);
    }

    public void testGettingAlbumsById() {
        Album album = albumDAO.getAlbum(11);
        assertNotNull(album);
    }

    protected void tearDown() throws Exception {
        transactionManager.commit(transactionStatus);
    }

}
