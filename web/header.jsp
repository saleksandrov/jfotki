<%@ page import="com.asv.jfotki.util.ConfigUtil,
                 com.asv.jfotki.model.Role,
                 com.asv.jfotki.web.bean.SecurityBean"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ taglib prefix="auth" uri="http://acegisecurity.sf.net/authz" %>

<html>
	<head>
    	<title>Home Page</title>
       	<link rel="stylesheet" type="text/css" href="css/sight.css"/>
    </head>
    <body>
    <a href="home.jsf"><h3>FOTKI</h3></a>
    <br>
    <auth:authorize ifNotGranted="<%= Role.REGISTERED_ROLE.getName() %>">
        <a href="login.jsp">:: login</a>
        <% if (new ConfigUtil().getBooleanConfigProperty("registration.enable")) {%>
            <a href="registration.jsf">:: registration</a>
        <% } %>
    </auth:authorize>
    <auth:authorize ifAllGranted="<%= Role.REGISTERED_ROLE.getName() %>">
        <a href="home.jsf?user=<%=new SecurityBean().getCurrentPrincipalName()%>">:: my albums</a>
        <a href="logoff.jsp">:: logoff</a>
    </auth:authorize>
    <auth:authorize ifAllGranted="<%= Role.ADMIN_ROLE.getName() %>">
        <a href="debug/debug.jsp">:: debug</a>
    </auth:authorize>
    <br>



