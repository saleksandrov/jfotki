/**
 * $Id: ApplicationRuntimeException.java,v 1.1 2004/11/17 10:10:22 asv Exp $
 */
package com.asv.jfotki.common.exception;

/**
 * @author  Sergey Aleksandrov
 */
public class ApplicationRuntimeException extends RuntimeException  {

    public ApplicationRuntimeException() {
        super();
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }

    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

}
