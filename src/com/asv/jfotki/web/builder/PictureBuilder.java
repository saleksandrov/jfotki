package com.asv.jfotki.web.builder;

import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import com.asv.jfotki.model.User;
import com.asv.jfotki.web.bean.AlbumBean;
import com.asv.jfotki.web.bean.PictureBean;
import com.asv.jfotki.web.bean.RegistrationBean;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Copy property between UI representation and business repsentation of a model.
 *
 * @author Sergey Aleksandrov
 */
public class PictureBuilder {

    /**
     * Populates {@link PictureBean} by {@link Picture}
     *
     * @param pictureBean
     * @param picture
     * @throws Exception
     */
	public static void populateProductBean(PictureBean pictureBean, PictureInfo picture) throws Exception {
	    BeanUtils.copyProperties(pictureBean, picture);
	}

    public static void populateAlbumBean(AlbumBean albumBean, Album album) throws Exception {
	    BeanUtils.copyProperties(albumBean, album);
	}

    public static void populateRegistrationBean(RegistrationBean registrationBean, User user) throws Exception {
	    BeanUtils.copyProperties(registrationBean, user);
	}

	/**
	 * Create a new {@link Album} based on the {@link AlbumBean}
	 *
	 * @param albumBean
	 * @return the new album business object
	 * @throws Exception
	 */
	public static Album createAlbum(AlbumBean albumBean) throws Exception {
		Album album = new Album();
		BeanUtils.copyProperties(album, albumBean);
		return album;
	}

    public static User createUser(RegistrationBean registrationBean) throws Exception {
        User user = new User();
        BeanUtils.copyProperties(user, registrationBean);
        return user;
    }

}
