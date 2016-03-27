/*
 * $Id: InitServlet.java,v 1.8 2005/04/19 15:02:34 asv Exp $
 */
package com.asv.jfotki.web.servlet;


import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.ApplicationRuntimeException;
import com.asv.jfotki.common.exception.UserExistsException;
import com.asv.jfotki.web.util.ServiceLocator;
import com.asv.jfotki.business.services.UserService;
import com.asv.jfotki.model.Role;
import com.asv.jfotki.model.User;
import com.asv.jfotki.util.ConfigUtil;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.util.jcache.Cache;
import javax.util.jcache.CacheAccessFactory;
import javax.util.jcache.CacheAttributes;
import javax.util.jcache.CacheException;
import java.io.File;
import java.util.Properties;

/**
 * Used for some initializing operations.
 *
 * @author Sergey Aleksandrov
 */
public class InitServlet extends HttpServlet {


    public static final int DEFAULT_MAX_CACHE_SIZE = 100;

    public InitServlet() {
    }

    public void init() throws ServletException {
        log4jInit();
        cacheInit();
        checkRole();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    }

    /**
     * Initialization of the Log4J subsystem.
     */
    private void log4jInit() {
        String prefix = getServletContext().getRealPath("/");
        String logPath = getInitParameter("log4.logs.path");
        String configFile = getInitParameter("log4j.config.file");

        if (StringUtils.isNotEmpty(logPath)) {
            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
        if (configFile != null) {
            PropertyConfigurator.configure(prefix + configFile);
        }
    }

    /**
     * Initialization of Cache system
     */
    private void cacheInit() {
        try {
            CacheAccessFactory factory = CacheAccessFactory.getInstance();
            Cache cache = factory.getCache();
            CacheAttributes attributes = cache.getAttributes();

            // property can't be null. if it take place the application works incorrect.
            String memoryCacheSizeParam = new ConfigUtil(getServletContext()).getConfigProperty("cache.memoryCacheSize");
            int memoryCacheSize = DEFAULT_MAX_CACHE_SIZE;
            if (StringUtils.isNotEmpty(memoryCacheSizeParam))
                memoryCacheSize = Integer.parseInt(memoryCacheSizeParam);
            attributes.setMemoryCacheSize(memoryCacheSize);
        } catch (CacheException e) {
            LogFactory.web.error("Error during cache configuring !", e);
            throw new ApplicationRuntimeException("Error during cache configuring !", e);
        }
        LogFactory.web.debug("Cache was initialized sucessfully!");
    }

    private void checkRole() {
        try {
            UserService userService = ServiceLocator.getInstance(getServletContext()).getUserService();
            Role registeredRole = userService.getRoleByName(Role.REGISTERED_ROLE.getName());
            if (registeredRole == null) {
                registeredRole = Role.REGISTERED_ROLE;
                userService.createRole(registeredRole);
            }

            Role adminRole = userService.getRoleByName(Role.ADMIN_ROLE.getName());
            if (adminRole == null) {
                adminRole = Role.ADMIN_ROLE;
                userService.createRole(adminRole);
            }
            Properties properties = new ConfigUtil(getServletContext()).getProperties();
            String adminUsername =  properties.getProperty("admin.username");
            String adminPassword =  properties.getProperty("admin.password");
            String adminEmail =  properties.getProperty("admin.email");

            User createdUser = userService.getUser(adminUsername);

            if (createdUser == null) {
                User admin = new User();
                admin.setEnabled(true);
                admin.setPassword(adminPassword);
                admin.setUsername(adminUsername);
                admin.setFullName(adminUsername);
                admin.setEmail(adminEmail);
                admin.addRole(adminRole);
                admin.addRole(registeredRole);

                userService.create(admin);
            }
        } catch (UserExistsException e) {
            LogFactory.web.error("Cannot create Role or User!", e);
            throw new ApplicationRuntimeException("Cannot create Role or User!", e);
        }

    }

}

