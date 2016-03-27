package com.asv.jfotki.client;

/*
 * $Id: ImportUtil.java,v 1.4 2005/04/10 14:02:45 asv Exp $
 */

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.model.Picture;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

/**
 * @author Sergey Aleksandrov
 */
public class ImportUtil {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PROJECT_DIR = System.getProperty("user.dir");


    public BeanFactory getBeanFactory() throws Exception {
        return new XmlBeanFactory(new FileSystemResource(PROJECT_DIR + FILE_SEPARATOR + "web" + FILE_SEPARATOR + "WEB-INF" + FILE_SEPARATOR + "test-application.xml"));
    }

    public void doImport(String dir) throws Exception {
        PictureService pictureService = (PictureService) getBeanFactory().getBean("pictureService");

        FileReader pictureReader = new FileReader(dir + FILE_SEPARATOR + "picture.export");
        BufferedReader reader = new BufferedReader(pictureReader);
        String str;
        while ((str = reader.readLine()) != null) {
            String pictureAttr[] = str.split("\t");
            Picture picture = new Picture();
            picture.setId(Integer.parseInt(pictureAttr[0]));
            picture.setName(pictureAttr[1]);
            picture.setDescription(getString(pictureAttr[2]));
            //picture.setAlbumId(Integer.parseInt(pictureAttr[3]));

            FileInputStream fileInputStream = new FileInputStream(dir + FILE_SEPARATOR + pictureAttr[0] + ".jpg");
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            picture.setBigPicture(b);
            pictureService.createPicture(picture);

        }
        pictureReader.close();


    }

    private String getString(String str) {
        if (str == null || str.length() < 1 || "null".equals(str))
            return "";
        return str;

    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("You must enter the directory ");
            return;
        } else {
            System.out.println("Importing from " + args[0]);
            new ImportUtil().doImport(args[0]);
        }

    }


}
