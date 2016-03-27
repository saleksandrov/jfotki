package com.asv.jfotki.common.exception;

/**
 * $Id: ApplicationException.java,v 1.1 2005/01/24 10:14:39 asv Exp $
 */



/**
 * @author Sergey Aleksandrov
 */
public class ApplicationException extends Exception {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

}
