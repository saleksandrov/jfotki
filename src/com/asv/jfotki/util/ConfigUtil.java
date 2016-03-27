/*
 * $Id: ConfigUtil.java,v 1.1 2005/04/19 15:01:41 asv Exp $
 */

package com.asv.jfotki.util;

import com.asv.jfotki.web.util.ServiceLocator;

import javax.servlet.ServletContext;
import java.util.Properties;

/**
 * @author Sergey Aleksandrov
 */
public class ConfigUtil {

    public static final String CONFIG_NAME = "config";
    private ServletContext servletContext;

    public ConfigUtil() {
    }

    public ConfigUtil(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Properties getProperties() {
        if (servletContext != null)
            return (Properties) ServiceLocator.getInstance(servletContext).lookupService(CONFIG_NAME);
        else
            return (Properties) ServiceLocator.getInstance().lookupService(CONFIG_NAME);
    }



    public String getConfigProperty(String name) {
        return getProperties().getProperty(name);
    }

    public boolean getBooleanConfigProperty(String name) {
        try {
            Boolean booleanProperty = new Boolean(getConfigProperty(name));
            return booleanProperty.booleanValue();
        } catch (Exception e) {
            return false;
        }
    }


}
