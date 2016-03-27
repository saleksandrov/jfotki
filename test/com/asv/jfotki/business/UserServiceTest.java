/*
 * $Id: UserServiceTest.java,v 1.3 2005/04/13 09:42:14 asv Exp $
 */

package com.asv.jfotki.business;

import com.asv.jfotki.business.services.PictureService;
import com.asv.jfotki.business.services.UserService;
import com.asv.AbstractTest;
import com.asv.jfotki.model.*;

import java.io.FileInputStream;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;

/**
 * The base test for project.
 *
 * @author Sergey Aleksandrov
 */
public class UserServiceTest extends AbstractTest {
    private UserService userService;

    protected void setUp() throws Exception {
        super.setUp();
        this.userService= (UserService) getBeanFactory().getBean("userService");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUser() throws Exception {
        userService.createRole(Role.REGISTERED_ROLE);

        User userForCreate = new User();
        userForCreate.setUsername("TEST_USERNAME");
        userForCreate.setPassword("TEST_PASSWORD");
        userForCreate.setFullName("TEST FullName");
        userForCreate.setEmail("test@server.com");
        userService.register(userForCreate);

        User user = userService.getUser("TEST_USERNAME");
        assertNotNull(user);
        assertEquals(userForCreate.getUsername(), user.getUsername());
        assertEquals(userForCreate.getPassword(), user.getPassword());
        assertEquals(userForCreate.getFullName(), user.getFullName());
        assertEquals(userForCreate.getEmail(), user.getEmail());
    }

    public void testNotFoundUser() throws Exception {
        User user = userService.getUser("1234567890qwertyu");
        assertNull(user);
    }

}
