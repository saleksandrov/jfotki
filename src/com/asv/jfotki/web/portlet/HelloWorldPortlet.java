package com.asv.jfotki.web.portlet;

import java.io.IOException;

import javax.portlet.*;

public class HelloWorldPortlet extends GenericPortlet {
    
    private static final String HELLO_TEMPLATE = "/templates/html/HelloWorld.jsp";

    public void init(PortletConfig pConfig) throws PortletException {
        super.init(pConfig);
    }

    public void doView(RenderRequest pRequest, RenderResponse pResponse) throws PortletException, IOException {
        WindowState state = pRequest.getWindowState();
        pResponse.setContentType("text/html");

        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(HELLO_TEMPLATE);

        pRequest.setAttribute("UserName", pRequest.getRemoteUser());
        pRequest.setAttribute("IsUserInRole", String.valueOf(pRequest.isUserInRole("jfotki-admin")));
        

        rd.include(pRequest, pResponse);
    }

    public void doEdit(RenderRequest pRequest, RenderResponse pResponse) throws PortletException, IOException {
        WindowState state = pRequest.getWindowState();
        pResponse.setContentType("text/html");
        
        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(HELLO_TEMPLATE);
        rd.include(pRequest, pResponse);

    }

    public void doHelp(RenderRequest pRequest, RenderResponse pResponse) throws PortletException, IOException {
        WindowState state = pRequest.getWindowState();
        pResponse.setContentType("text/html");

        PortletContext context = getPortletContext();
        PortletRequestDispatcher rd = context.getRequestDispatcher(HELLO_TEMPLATE);
        rd.include(pRequest, pResponse);
    }
}
