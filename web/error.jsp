<%@ page contentType="text/html; charset=windows-1251" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ include file="header.jsp" %>

<TABLE>
   <TR class="errorHeader"><TD class="errorHeader">ERROR</TD></TR>
   <TR><TD><PRE><c:out value="${errorBean.stackTrace}"/></PRE></TD></TR>
</TABLE>


<%@ include file="footer.jsp" %>