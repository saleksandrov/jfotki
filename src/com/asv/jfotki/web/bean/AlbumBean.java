/*
 * $Id: AlbumBean.java,v 1.3 2005/06/22 16:01:18 asv Exp $
 */

package com.asv.jfotki.web.bean;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.web.builder.PictureBuilder;
import com.asv.jfotki.web.util.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * UI representation of {@link Album} object.
 * Contains web tier methods with work with {@link Album}.
 *
 * @author Sergey Aleksandrov
 */
public class AlbumBean {

    private int id;
    private String name = "";



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns List of all albums. Used in dataTable component.
     *
     * @return  List
     */
    public List getAlbums() {
        String username = FacesUtils.getRequestParameter("user");
        List albums = username == null
                ? ServiceLocator.getInstance().getPictureService().getAllAlbums()
                : ServiceLocator.getInstance().getPictureService().getAlbumsByUserName(username);
        LogFactory.web.debug("Loading albums....");
        LogFactory.web.debug("Albums count = " + (albums != null ? albums.size() : 0));
        return albums;
    }

    /**
     * Returns List of all albums for UI selectItem component.
     * Each element in List is a instance of SelectItem.
     *
     * @return List
     */
    public List getAlbumSelectItems() {
        List albums = ServiceLocator.getInstance().getPictureService().getAllAlbums();
        List albumSelectItems = new ArrayList();
        for (Iterator it = albums.iterator(); it.hasNext();) {
            Album album = (Album) it.next();
            SelectItem item = new SelectItem();
            item.setLabel(album.getName());
            item.setValue(String.valueOf(album.getId()));
            albumSelectItems.add(item);
        }
        return albumSelectItems;
    }

    /**
     * Perfoms storing of Album.
     *
     * @return Navigation Result.
     * @throws Exception
     */
    public String storeAlbum() throws Exception {
        Album album = PictureBuilder.createAlbum(this);
        LogFactory.web.debug("Creates Album instance. Name =  " + album.getName());
        SecurityBean securityBean = (SecurityBean)FacesUtils.getManagedBean("securityBean");
        ServiceLocator.getInstance().getPictureService().createAlbum(album, securityBean.getCurrentPrincipalName());

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        String username = FacesUtils.getRequestParameter("user");
        externalContext.redirect(new RedirectHelper(null, username).getRedirectUrl());
        facesContext.responseComplete();
        return NavigationResults.SUCCESS;
    }


}
