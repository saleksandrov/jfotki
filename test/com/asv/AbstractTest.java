/**
 * $Id: AbstractTest.java,v 1.8 2005/05/17 08:39:11 asv Exp $
 */
package com.asv;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.core.io.FileSystemResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

import java.io.FileInputStream;


/**
 * @author Sergey Aleksandrov
 */
public class AbstractTest extends TestCase {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PROJECT_DIR = System.getProperty("user.dir");

    protected PlatformTransactionManager transactionManager;
    protected TransactionStatus transactionStatus;

    protected Logger log = org.apache.log4j.Logger.getLogger(AbstractTest.class);

    /**
     * Bean Factory will load only once for all {@link TestCase}.
     */
    private static BeanFactory beanFactory;

    public void setTransactionManager(PlatformTransactionManager ptm) {
        this.transactionManager = ptm;
    }

    protected final void onSetUp() throws Exception {
        this.transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    protected final void onTearDown() throws Exception {
        this.transactionManager.rollback(this.transactionStatus);
    }

    protected void setUp() throws Exception {
        transactionManager = (PlatformTransactionManager) getBeanFactory().getBean("transactionManager");
        onSetUp();
    }

    public static BeanFactory getBeanFactory() throws Exception {
        if (beanFactory == null) {
            String springDir = PROJECT_DIR + FILE_SEPARATOR + "web" + FILE_SEPARATOR + "WEB-INF" + FILE_SEPARATOR + "spring" + FILE_SEPARATOR;
            ApplicationContext factory =  new FileSystemXmlApplicationContext(springDir + "test-application.xml");
            beanFactory = factory;
        }
        return beanFactory;
    }

    public void testDummy() {
    }

    protected void tearDown() throws Exception {
        onTearDown();
    }
}
