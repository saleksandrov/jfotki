/*
 * $Id: PictureBean.java,v 1.5 2005/04/19 15:02:16 asv Exp $
 */

package com.asv.jfotki.web.bean;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.ApplicationRuntimeException;
import com.asv.jfotki.model.Album;
import com.asv.jfotki.model.Picture;
import com.asv.jfotki.model.PictureInfo;
import com.asv.jfotki.web.builder.PictureBuilder;
import com.asv.jfotki.web.util.FacesUtils;
import com.asv.jfotki.web.util.NavigationResults;
import com.asv.jfotki.web.util.RedirectHelper;
import com.asv.jfotki.web.util.ServiceLocator;
import com.asv.jfotki.util.ConfigUtil;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;


/**
 * UI representation of {@link com.asv.jfotki.model.Picture} object.
 * Contains web tier methods with work with {@link com.asv.jfotki.model.Picture}.
 *
 * @author Sergey Aleksandrov
 */
public class PictureBean {

    public static final String CONFIG_NAME = "config";

    private int id;
    private String name;
    private String description;
    private String note;
    private String albumName;
    private int albumId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    /**
     * Returns all pictures by albumId. Used in dataTable
     * UI Component.
     *
     * @return List
     */
    public List getPictures(int albumId) {
        return ServiceLocator.getInstance().getPictureService().getPictures(albumId);
    }

    /**
     * Load {@link Picture}.
     *
     * @return Navigation Result
     * @throws Exception
     */
    public String viewPicture() throws Exception {
        String idString = FacesUtils.getRequestParameter("id");
        int id = Integer.parseInt(idString);
        PictureInfo picture = ServiceLocator.getInstance().getPictureService().getPicture(id);
        PictureBuilder.populateProductBean(this, picture);
        return NavigationResults.SUCCESS;
    }

    public String deletePicture() throws Exception {
        String idString = FacesUtils.getRequestParameter("id");
        int id = Integer.parseInt(idString);
        ServiceLocator.getInstance().getPictureService().deletePicture(id);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String albumId = FacesUtils.getRequestParameter("albumId");
        String username = FacesUtils.getRequestParameter("user");
        externalContext.redirect(new RedirectHelper(albumId, username).getRedirectUrl());
        FacesContext.getCurrentInstance().responseComplete();
        return NavigationResults.SUCCESS;
    }

    public void nextPicture(ActionEvent event) {
        String idString = FacesUtils.getRequestParameter("id");
        String albumIdString = FacesUtils.getRequestParameter("albumId");

        int id = Integer.parseInt(idString);
        int albumId = Integer.parseInt(albumIdString);

        PictureInfo picture = ServiceLocator.getInstance().getPictureService().nextPicture(id, albumId);

        try {
            PictureBuilder.populateProductBean(this, picture);
        } catch (Exception e) {
            LogFactory.web.error("Error during populating bean. ", e);
            throw new ApplicationRuntimeException("Error during populating bean. ", e);
        }

    }

    public UIComponent getDynamicData() {
        String albumIdString = FacesUtils.getRequestParameter("albumId");
        int albumId;
        if (NumberUtils.isNumber(albumIdString)) {
            albumId = Integer.parseInt(albumIdString);
            return getDynamicData(albumId);
        } else {
            return getRootGrid();
        }
    }

    /**
     * Binding method witch constructs an UIComponent Tree for JSF page.
     * Used for dynamic dataTable facility.
     *
     * @return UIComponent
     */
    public UIComponent getDynamicData(int albumId) {
        HtmlPanelGrid rootGrid = getRootGrid();

        boolean deleteLinkRendered = ((SecurityBean)FacesUtils.getManagedBean("securityBean")).isAlbumOwner();

        Album album = ServiceLocator.getInstance().getPictureService().getAlbum(albumId);
        this.albumName = album.getName();

        for (Iterator it = getPictures(albumId).iterator();it.hasNext();) {
            PictureInfo picture = (PictureInfo) it.next();

            HtmlPanelGrid panelGrid = new HtmlPanelGrid();
            panelGrid.setColumns(1);

            HtmlGraphicImage graphicImage = createGraphicImage(picture);

            MethodBinding viewMethodBinding =
                                FacesContext.getCurrentInstance().getApplication().createMethodBinding(
                                        "#{pictureBean.viewPicture}",
                                        null);

            HtmlCommandLink imageCommandLink = createPictureViewImageCommandLink(picture, graphicImage, viewMethodBinding);
            HtmlCommandLink commandLink = createPictureViewCommandLink(picture, viewMethodBinding);
            String user = FacesUtils.getRequestParameter("user");
            if (StringUtils.isNotEmpty(user)) {
                imageCommandLink.getChildren().add(getUserParam(user));
                commandLink.getChildren().add(getUserParam(user));
            }

            boolean isDeleteEnable = new ConfigUtil().getBooleanConfigProperty("deletePicture.enable");
            if (isDeleteEnable && deleteLinkRendered) {
                HtmlCommandLink deleteCommand = createDeleteCommand(picture);
                if (StringUtils.isNotEmpty(user)) {
                    deleteCommand.getChildren().add(getUserParam(user));
                }
                panelGrid.getChildren().add(deleteCommand);
            }

            panelGrid.getChildren().add(imageCommandLink);
            panelGrid.getChildren().add(commandLink);

            rootGrid.getChildren().add(panelGrid);
        }
        System.out.println("**** ROOT GRID Size: " + rootGrid.getChildren().size());
        return rootGrid;
    }

    private HtmlPanelGrid getRootGrid() {
        HtmlPanelGrid rootGrid = new HtmlPanelGrid();
        rootGrid.setColumnClasses("tableBorder");
        return rootGrid;
    }

    protected HtmlGraphicImage createGraphicImage(PictureInfo picture) {
        HtmlGraphicImage graphicImage = new HtmlGraphicImage();
        graphicImage.setStyle("BORDER: 0");
        graphicImage.setUrl("image?pictureId=" + picture.getId());
        return graphicImage;
    }

    private HtmlCommandLink createPictureViewCommandLink(PictureInfo picture, MethodBinding viewMethodBinding) {
        HtmlCommandLink commandLink = new HtmlCommandLink();
        commandLink.setTitle("view picture");
        UIParameter parameter = new UIParameter();
        parameter.setName("id");
        parameter.setValue(String.valueOf(picture.getId()));
        commandLink.getChildren().add(parameter);
        commandLink.setAction(viewMethodBinding);

        HtmlOutputText outputText = new HtmlOutputText();
        outputText.setValue(picture.getName());
        commandLink.getChildren().add(outputText);

        return commandLink;
    }

    private HtmlCommandLink createPictureViewImageCommandLink(PictureInfo picture, HtmlGraphicImage graphicImage, MethodBinding viewMethodBinding) {
        HtmlCommandLink imageCommandLink = new HtmlCommandLink();
        imageCommandLink.setTitle("view picture 2");
        UIParameter imageParameter = new UIParameter();
        imageParameter.setName("id");
        imageParameter.setValue(String.valueOf(picture.getId()));
        imageCommandLink.getChildren().add(graphicImage);
        imageCommandLink.getChildren().add(imageParameter);
        imageCommandLink.setAction(viewMethodBinding);
        return imageCommandLink;
    }

    private HtmlCommandLink createDeleteCommand(PictureInfo picture) {
        UIParameter parameter = new UIParameter();
        parameter.setName("id");
        parameter.setValue(String.valueOf(picture.getId()));;
        MethodBinding deleteMethodBinding =
                            FacesContext.getCurrentInstance().getApplication().createMethodBinding(
                                    "#{pictureBean.deletePicture}",
                                    null);
        HtmlCommandLink deleteCommandLink = new HtmlCommandLink();
        deleteCommandLink.setTitle("delete picture");

        UIParameter albumParameter = new UIParameter();
        albumParameter.setName("albumId");
        albumParameter.setValue(String.valueOf(picture.getAlbumId()));

        deleteCommandLink.getChildren().add(parameter);
        deleteCommandLink.getChildren().add(albumParameter);
        deleteCommandLink.setAction(deleteMethodBinding);

        HtmlOutputText deleteLinkText = new HtmlOutputText();
        ValueBinding deleteTextValueBinding =
                            FacesContext.getCurrentInstance().getApplication().createValueBinding("#{bundle.delete}");
        deleteLinkText.setValueBinding("value", deleteTextValueBinding);
        deleteCommandLink.getChildren().add(deleteLinkText);
        deleteCommandLink.setOnmousedown("javascript:return confirmDelete(this);");
        return deleteCommandLink;
    }

    public UIParameter getUserParam(String username) {
        UIParameter param = new UIParameter();
        param.setName("user");
        param.setValue(username);
        return param;
    }

    /**
     * Used for method binding.
     *
     * @param component
     */
    public void setDynamicData(UIComponent component) {
    }

}
