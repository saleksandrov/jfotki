/*
 * $Id: ErrorFilter.java,v 1.2 2005/01/24 10:14:42 asv Exp $
 */

package com.asv.jfotki.web.servlet.filter;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.AccessDeniedRuntimeException;
import com.asv.jfotki.web.bean.ErrorBean;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Sergey Aleksandrov
 */
public class ErrorFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            LogFactory.web.error("Error: ", ex);
            if (ex instanceof ServletException) {
                ServletException servletException = (ServletException) ex;
                if (servletException.getRootCause() instanceof AccessDeniedRuntimeException) {
                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/access-denied.jsp");
                    dispatcher.forward(servletRequest, servletResponse);
                    return;
                }
            }

            ErrorBean  errorBean = new ErrorBean();
            errorBean.setException(ex);
            servletRequest.setAttribute("errorBean", errorBean);
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/error.jsp");
            dispatcher.forward(servletRequest, servletResponse);

        }
    }

    public void destroy() {
    }


}
