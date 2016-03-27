/*
 * $Id: ErrorBean.java,v 1.2 2005/01/24 10:14:40 asv Exp $
 */

package com.asv.jfotki.web.bean;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * Contains Error info.
 *
 * @author Sergey Aleksandrov
 */
public class ErrorBean {

    private Throwable ex;

    public ErrorBean() {
    }

    public void setException(Throwable ex) {
        this.ex = ex;
    }

    public Throwable getException() {
        return ex;
    }

    public String getStackTrace() {
        return stackTrace(this.ex);
    }

    public String stackTrace(Throwable th) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            th.printStackTrace(new PrintWriter(buf, true));
            return buf.toString();
        } catch (Exception e) {
            return "Runtime error: " + e.getMessage();
        }
    }

}
