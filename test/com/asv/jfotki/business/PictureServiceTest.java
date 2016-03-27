/*
 * $Id: PictureServiceTest.java,v 1.5 2005/04/10 14:04:42 asv Exp $
 */

package com.asv.jfotki.business;

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.business.services.UserService;
import com.asv.jfotki.business.services.SecurityService;
import com.asv.AbstractTest;
import com.asv.jfotki.model.*;

import java.io.FileInputStream;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;

/**
 * The base test for project.
 *
 * @author Sergey Aleksandrov
 */
public class PictureServiceTest extends AbstractTest {

    private PictureService pictureService;
    private UserService userService;
    private SecurityService securityService;

    private static final String USER_NAME     = "TEST_USER" + System.currentTimeMillis();
    private static final String USER_PASSWORD = "TEST_PASSWORD_PICTURE";
    private static final String ALBUM_NAME    = "JUnit test album: " + System.currentTimeMillis();

    private int albumId;


    protected void setUp() throws Exception {
        super.setUp();
        this.pictureService= (PictureService) getBeanFactory().getBean("pictureService");
        this.userService = (UserService) getBeanFactory().getBean("userService");
        this.securityService = (SecurityService) getBeanFactory().getBean("securityService");

        User userForCreate = new User();
        userForCreate.setUsername(USER_NAME);
        userForCreate.setPassword(USER_PASSWORD);
        userService.register(userForCreate);

        Album album = new Album();
        album.setName(ALBUM_NAME);
        pictureService.createAlbum(album, USER_NAME);

        List albums = pictureService.getAllAlbums();
        assertNotNull(albums);;

        for (Iterator it = albums.iterator(); it.hasNext();) {
            Album currentAlbum = (Album) it.next();
            if (ALBUM_NAME.equals(currentAlbum.getName())) {
                albumId = currentAlbum.getId();
            }
        }

        FileInputStream fileInputStream = new FileInputStream(PROJECT_DIR +  FILE_SEPARATOR + "for_test" + FILE_SEPARATOR + "test.jpg");
        byte[] pictureBytes = new byte[fileInputStream.available()];
        fileInputStream.read(pictureBytes);
        Picture picture = new Picture();
        picture.setName("Picture name");
        picture.setNote("Picture note");
        picture.setDescription("Picture description");
        picture.setAlbumId(albumId);
        picture.setBigPicture(pictureBytes);
        pictureService.createPicture(picture);

        Picture picture2 = new Picture();
        picture2.setName("Picture name2");
        picture2.setNote("Picture note3");
        picture2.setDescription("Picture description3");
        picture2.setAlbumId(albumId);
        picture2.setBigPicture(pictureBytes);
        pictureService.createPicture(picture2);

    }

    protected void tearDown() throws Exception {
        super.tearDown();    
    }

    public void testPicture() throws Exception {
        Album loadedAlbum = pictureService.getAlbum(albumId);
        assertNotNull(loadedAlbum);

        List albumsByName = pictureService.getAlbumsByUserName(USER_NAME);
        assertNotNull(albumsByName);
        assertTrue(albumsByName.size() >= 1);

        List pictures = pictureService.getPictures(albumId);
        assertNotNull(pictures);
        assertTrue(pictures.size() > 0);
        assertTrue(pictures.get(0) instanceof PictureInfo);
        int pictureId = ((PictureInfo)pictures.get(0)).getId();

        PictureInfo foundPicture = pictureService.getPicture(pictureId);
        assertNotNull(foundPicture);
        assertNotNull(foundPicture.getAlbumName());
        assertTrue(foundPicture.getAlbumId() > 0);

        byte[] bigImage = pictureService.getBigImage(pictureId);
        assertNotNull(bigImage);
        assertTrue("Big Image cannot be empty", bigImage.length > 0);

        byte[] smallImage = pictureService.getSmallImage(pictureId);
        assertNotNull(smallImage);
        assertTrue("Small Image cannot be empty", smallImage.length > 0);
    }

    public void testNext() throws Exception {
        List pictures = pictureService.getPictures(albumId);
        assertNotNull("Pictures cannot be null! ", pictures);
        assertTrue(pictures.size() >= 2);
        int pictureId1 = ((PictureInfo) pictures.get(0)).getId();
        int pictureId2 = ((PictureInfo) pictures.get(1)).getId();

        int pictureId = pictureService.nextPicture(pictureId1, albumId).getId();
        assertTrue(pictureId == pictureId2);

        pictureService.deletePicture(pictureId2);
        pictureId = pictureService.nextPicture(pictureId1, albumId).getId();
        assertTrue(pictureId == pictureId1);

    }

    public void testSecurity() {
        assertTrue(securityService.isAlbumOwner(USER_NAME,  albumId));
    }

    public void testRole() {
        Role role = new Role();
        role.setName("TESTED ROLE");
        userService.createRole(role);
    }

}
