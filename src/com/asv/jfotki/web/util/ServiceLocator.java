/**
 * $Id: ServiceLocator.java,v 1.4 2005/06/22 16:03:17 asv Exp $
 */
package com.asv.jfotki.web.util;

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.business.services.UserService;
import com.asv.jfotki.common.security.ApplicationSecurityManager;

import javax.servlet.ServletContext;

/**
 * AbstractFactory for creating appropriate {@link ServiceLocator}
 * implementation.
 *
 * @author Sergey Aleksandrov
 */
public abstract class ServiceLocator {

	private static ServiceLocator serviceLocator;

    public synchronized static ServiceLocator getInstance() {
        if (serviceLocator == null) {
            serviceLocator = new PictureServiceLocatorImpl();
        }
        return serviceLocator;
    }

    public synchronized static ServiceLocator getInstance(ServletContext context) {
        if (serviceLocator == null) {
            serviceLocator = new PictureServiceLocatorImpl(context);
        }
        return serviceLocator;
    }


    public abstract PictureService getPictureService();

    public abstract UserService getUserService();

    public abstract ApplicationSecurityManager getSecurityManager();

    public abstract Object lookupService(String serviceBeanName);

}
