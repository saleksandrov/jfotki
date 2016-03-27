/*
 * $Id: AlbumOwnerSecurityInterceptor.java,v 1.2 2005/06/22 16:03:52 asv Exp $
 */

package com.asv.jfotki.business.interceptors;

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.business.dao.PictureDAO;
import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.security.ApplicationSecurityManager;
import com.asv.jfotki.common.exception.AccessDeniedRuntimeException;
import com.asv.jfotki.model.PictureInfo;
import com.asv.jfotki.model.Role;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Used for cache picture files.
 *
 * @author Sergey Aleksandrov
 */
public class AlbumOwnerSecurityInterceptor implements MethodInterceptor {

    private ApplicationSecurityManager securityManager;
    private PictureDAO pictureDAO;

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogFactory.web.debug("AlbumOwnerSecurityInterceptor is working...");
        String methodName = methodInvocation.getMethod().getName();

        if (methodName.equals("deletePicture")) {
            Integer pictureId = (Integer) methodInvocation.getArguments()[0];
            PictureInfo picture = pictureDAO.getPicture(pictureId.intValue());
            checkAccess(picture);
        } else if (methodName.equals("createPicture")) {
            PictureInfo picture = (PictureInfo) methodInvocation.getArguments()[0];
            checkAccess(picture);
        } else if (methodName.equals("createAlbum")) {
            if (!securityManager.isMemberOfRole(Role.REGISTERED_ROLE.getName())) {
                throw new AccessDeniedRuntimeException("Access Denied!");
            }
        }
        return methodInvocation.proceed();
    }

    private void checkAccess(PictureInfo picture) throws AccessDeniedRuntimeException {
        int albumId = picture.getAlbumId();
        if (!securityManager.isAlbumOwner(albumId)) {
             throw new AccessDeniedRuntimeException("Access Denied!");
        }
    }

    public void setSecurityManager(ApplicationSecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public void setPictureDAO(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
;
    }
}
