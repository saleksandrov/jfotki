/*
 * $Id: SecurityBean.java,v 1.4 2005/06/22 16:01:18 asv Exp $
 */

package com.asv.jfotki.web.bean;

import com.asv.jfotki.web.util.FacesUtils;
import com.asv.jfotki.web.util.ServiceLocator;
import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.security.ApplicationSecurityManager;
import org.apache.commons.lang.NumberUtils;

/**
 * @author Sergey Aleksandrov
 */
public class SecurityBean {

    private boolean result;
    private boolean isCalculated;

    private String currentPrincipalName;

    public SecurityBean() {
        setCurrentPrincipalName(ServiceLocator.getInstance().getSecurityManager().getCurrentPrincipalName());
    }

    public String getCurrentPrincipalName() {
        return currentPrincipalName;
    }

    public void setCurrentPrincipalName(String currentPrincipalName) {
        this.currentPrincipalName = currentPrincipalName;
    }

    public boolean isAlbumOwner() {
        if (isCalculated) {
            return result;
        } else {
            LogFactory.web.debug("Calculating isAlbumOwner() ...");
            String albumIdString = FacesUtils.getRequestParameter("albumId");
            if (NumberUtils.isNumber(albumIdString)) {
                int albumId = Integer.parseInt(albumIdString);
                ApplicationSecurityManager securityManager = ServiceLocator.getInstance().getSecurityManager();
                isCalculated = true;
                result = securityManager.isAlbumOwner(albumId);
                return result;
            }
        }
        return false;
    }
}
