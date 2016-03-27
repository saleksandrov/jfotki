<%@ taglib prefix="portlet" uri="/WEB-INF/portlet.tld" %>

<portlet:defineObjects/>

<center><img src="<%= renderRequest.getContextPath()%>/images/hello-world.png"/></center>

<center>HelloWorld Portlet in <b><%= renderRequest.getPortletMode().toString() %></b> Mode</center>

<center>The current window state is <b><%= renderRequest.getWindowState().toString() %></b></center>


<br>

<b>UserName: <%= renderRequest.getRemoteUser() %></b><br>
<b>IsUserInRole: <%= String.valueOf(renderRequest.isUserInRole("jfotki-admin")) %></b><br>
<b>IsUserInRole Administrator: <%= String.valueOf(renderRequest.isUserInRole("Administrator")) %></b><br>
<b>IsUserInRole Administration: <%= String.valueOf(renderRequest.isUserInRole("Administration")) %></b><br>
<b>IsUserInRole Administrators: <%= String.valueOf(renderRequest.isUserInRole("Administrators")) %></b><br>




