/*
 * $Id$
 */

package com.asv.jfotki.common.security;

import com.asv.jfotki.model.User;

/**
 * @author Sergey Aleksandrov
 */
public interface SecurityContext {
    User getUser();
}
