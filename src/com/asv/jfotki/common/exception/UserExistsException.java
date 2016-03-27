/*
 * $Id: UserExistsException.java,v 1.1 2005/01/24 10:14:39 asv Exp $
 */

package com.asv.jfotki.common.exception;

/**
 * @author Sergey Aleksandrov
 */
public class UserExistsException extends ApplicationException {

    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(Throwable cause) {
        super(cause);
    }
}
