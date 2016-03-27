/*
 * $Id: AccessDeniedRuntimeException.java,v 1.1 2005/01/24 10:14:38 asv Exp $
 */

package com.asv.jfotki.common.exception;

/**
 * @author Sergey Aleksandrov
 */
public class AccessDeniedRuntimeException extends ApplicationRuntimeException {

    public AccessDeniedRuntimeException() {
        super();
    }

    public AccessDeniedRuntimeException(String message) {
        super(message);
    }

    public AccessDeniedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedRuntimeException(Throwable cause) {
        super(cause);
    }
}
