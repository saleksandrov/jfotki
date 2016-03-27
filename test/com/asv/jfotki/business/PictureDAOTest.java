/*
 * $Id: PictureDAOTest.java,v 1.2 2005/01/17 15:19:07 asv Exp $
 */

package com.asv.jfotki.business;

import com.asv.AbstractTest;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import com.asv.jfotki.business.dao.PictureDAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class PictureDAOTest  extends AbstractTest {

    private PictureDAO pictureDAO;

    protected void setUp() throws Exception {
        pictureDAO = (PictureDAO) getBeanFactory().getBean("pictureDAO");
    }

    public void testCreate() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(PROJECT_DIR +  FILE_SEPARATOR + "for_test" + FILE_SEPARATOR + "test.jpg");
        byte[] pictureBytes = new byte[fileInputStream.available()];
        fileInputStream.read(pictureBytes);

        Picture picture = new Picture();
        picture.setName("Picture name2");
        picture.setNote("Picture note3");
        picture.setDescription("Picture description");
        picture.setAlbumId(61);
        picture.setBigPicture(pictureBytes);
        picture.setSmallPicture(pictureBytes);

        pictureDAO.create(picture);
    }

    public void testGetPictureByAlbumId() {
        List pictures = pictureDAO.getPictures(21);
        assertNotNull(pictures);
        assertTrue(pictures.size() > 0);
        assertTrue(pictures.get(0) instanceof PictureInfo);
    }

     public void testGetPictureById() {
        PictureInfo picture = pictureDAO.getPicture(65);
        assertNotNull(picture);
    }

    public void testImage() throws Exception {


        byte[] pictureBytes = pictureDAO.getSmallImage(81);
        assertNotNull(pictureBytes);
        assertTrue(pictureBytes.length != 0);

    }
}