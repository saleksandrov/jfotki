/*
 * $Id: PictureServiceLocatorImpl.java,v 1.3 2005/06/22 16:03:05 asv Exp $
 */

package com.asv.jfotki.web.util;

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.business.services.UserService;
import com.asv.jfotki.common.security.ApplicationSecurityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Simple implementation of {@link ServiceLocator}.
 *
 * @author Sergey Aleksandrov
 */
public class PictureServiceLocatorImpl extends ServiceLocator {

    private static final String PICTURE_SERVICE_NAME = "pictureService";
    private static final String USER_SERVICE_NAME = "userService";
    private static final String SECURITY_MANAGER = "securityManager";



    //the Spring application context
	private ApplicationContext appContext;


    protected PictureServiceLocatorImpl() {
		ServletContext context = FacesUtils.getServletContext();
		this.appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}

    protected PictureServiceLocatorImpl(ServletContext context) {
		this.appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}

	/**
	 * Get the <code>PictureService</code>
	 *
	 * @return the picture service
	 */
	public PictureService getPictureService() {
		return 	(PictureService) this.lookupService(PICTURE_SERVICE_NAME);
	}

    public UserService getUserService() {
		return 	(UserService) this.lookupService(USER_SERVICE_NAME);
	}

    public ApplicationSecurityManager getSecurityManager() {
        return 	(ApplicationSecurityManager) this.lookupService(SECURITY_MANAGER);
    }


	/**
	 * Lookup service based on service bean name.
	 *
	 * @param serviceBeanName the service bean name
	 * @return the service bean
	 */
	public Object lookupService(String serviceBeanName) {
		return appContext.getBean(serviceBeanName);
	}
}
