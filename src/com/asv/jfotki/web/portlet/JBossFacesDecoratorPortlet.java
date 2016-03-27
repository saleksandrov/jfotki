/*
 * $Id$
 */

package com.asv.jfotki.web.portlet;

import com.sun.faces.portlet.FacesPortlet;
import com.asv.jfotki.common.security.PortalSecurityContext;
import com.asv.jfotki.common.security.SecurityContextFactory;
import com.asv.jfotki.model.Role;
import com.asv.jfotki.model.User;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;

/**
 * @author Sergey Aleksandrov
 */
public class JBossFacesDecoratorPortlet extends FacesPortlet {

    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        User user = new User();
        user.setUsername(renderRequest.getRemoteUser());
        if (renderRequest.isUserInRole(Role.ADMIN_ROLE.getName())) {
            user.addRole(Role.ADMIN_ROLE);
        }

        SecurityContextFactory.createContext(user, new PortalSecurityContext());

        super.render(renderRequest, renderResponse);
    }

}
