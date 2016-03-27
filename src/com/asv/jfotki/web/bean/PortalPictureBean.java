/*
 * $Id$
 */

package com.asv.jfotki.web.bean;

import com.asv.jfotki.model.PictureInfo;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.event.ActionEvent;

/**
 * @author Sergey Aleksandrov
 */
public class PortalPictureBean extends PictureBean {

    private UIComponent uiComponent;

    public void prepareDynamicData(ActionEvent event) {
          uiComponent = super.getDynamicData();
    }

    public UIComponent getDynamicData() {
        if (uiComponent == null) {
            return super.getDynamicData();
        }
        return uiComponent;
    }

    protected HtmlGraphicImage createGraphicImage(PictureInfo picture) {
        HtmlGraphicImage graphicImage = new HtmlGraphicImage();
        graphicImage.setStyle("BORDER: 0");
        graphicImage.setUrl("/JFotki/image?pictureId=" + picture.getId());
        return graphicImage;
    }


}
