/*
 * $Id$
 */

package com.asv.jfotki.common.security;

import com.asv.jfotki.model.User;

/**
 * @author Sergey Aleksandrov
 */
public interface SecurityContextCreator {
    void setUser(User user);
}
