/*
 * $Id$
 */

package com.asv.jfotki.business.interceptors;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.AccessDeniedRuntimeException;
import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Sergey Aleksandrov
 */
public class PortalSecurityInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        LogFactory.web.debug("AlbumOwnerSecurityInterceptor is working...");
        String methodName = methodInvocation.getMethod().getName();

        // TODO Add code for getting username
        String usename = "";

        if (methodName.equals("deletePicture")) {
            // TODO check is user in role - jfotki_admin
        } else if (methodName.equals("createPicture")) {
            // TODO check is user in role - jfotki_admin
        } else if (methodName.equals("createAlbum")) {
            // TODO check is user in role - jfotki_admin
        }
        return methodInvocation.proceed();
    }

}
