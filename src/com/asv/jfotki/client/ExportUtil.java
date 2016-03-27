package com.asv.jfotki.client;

/*
 * $Id: ExportUtil.java,v 1.4 2005/04/10 14:02:45 asv Exp $
 */

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.Picture;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sergey Aleksandrov
 */
public class ExportUtil {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PROJECT_DIR = System.getProperty("user.dir");


    public BeanFactory getBeanFactory() throws Exception {
        return new XmlBeanFactory(new FileSystemResource(PROJECT_DIR + FILE_SEPARATOR + "web" + FILE_SEPARATOR + "WEB-INF" + FILE_SEPARATOR + "test-application.xml"));
    }

    public void doExport(String dir) throws Exception {
        PictureService pictureService = (PictureService) getBeanFactory().getBean("pictureService");

        List albums = pictureService.getAllAlbums();
        FileWriter albumWriter = new FileWriter(dir + FILE_SEPARATOR + "albums.export");
        for (Iterator it = albums.iterator(); it.hasNext();) {
            Album album = (Album) it.next();
            albumWriter.write(album.getId() + "\t" + album.getName() + "\r\n");
        }
        albumWriter.close();

        List pictures = pictureService.getAllPictures();
        FileWriter pictureWriter = new FileWriter(dir + FILE_SEPARATOR + "picture.export");
        for (Iterator it = pictures.iterator(); it.hasNext();) {
            Picture picture = (Picture) it.next();
            pictureWriter.write(picture.getId() + "\t"
                    + picture.getName() + "\t"
                    + picture.getDescription() + "\t"
                    + picture.getAlbumId() + "\r\n");
            FileOutputStream pictureOutputStream = new FileOutputStream(dir + FILE_SEPARATOR + picture.getId() + ".jpg");
            pictureOutputStream.write(picture.getBigPicture());
            pictureOutputStream.close();
        }
        pictureWriter.close();


    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("You must enter the directory ");
            return;
        } else {
            System.out.println("Exporting into " + args[0]);
            new ExportUtil().doExport(args[0]);
        }

    }


}
