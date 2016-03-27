/*
 * $Id: LobSelector.java,v 1.1 2005/04/10 14:02:46 asv Exp $
 */

package com.asv.jfotki.util;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

import com.asv.jfotki.common.exception.ApplicationRuntimeException;

/**
 * @author Sergey Aleksandrov
 */
public class LobSelector extends AbstractFactoryBean implements ApplicationContextAware {

    private static final String ORACLE_DIALECT = "Oracle";

    private ApplicationContext applicationContext;
    private String hibernateDialect;

    public Class getObjectType() {
        return LobHandler.class;
    }

    protected Object createInstance() throws Exception {
        Collection beans;
        if (hibernateDialect != null && hibernateDialect.indexOf(ORACLE_DIALECT) != -1) {
            beans = applicationContext.getBeansOfType(org.springframework.jdbc.support.lob.OracleLobHandler.class).values();
        } else {
            beans = applicationContext.getBeansOfType(org.springframework.jdbc.support.lob.DefaultLobHandler.class).values();
        }
        Object bean = null;
        try {
            bean = CollectionUtils.get(beans, 0);
        } catch (Exception e) {
            new ApplicationRuntimeException("Cannot get LobHandler bean from ApplicationContext!");
        }
        return bean;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

}
