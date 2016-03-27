<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/portlet/components" prefix="p" %>

<f:view>
<%-- Embed JSF tags with in portletPage tag if you expect multiple instances
      of this portlet to exist within a portal page --%>
<p:portletPage>
<h:form id="helloForm" >
  Hi. My name is Duke.  I'm thinking of a number from


    <h:graphicImage id="waveImg" url="/wave.med.gif" />
<h:commandButton id="submit" action="success" value="Submit" />
     
 <h:message style="color: red; font-family: 'New Century Schoolbook', serif; font-style: oblique; text-decoration: overline" id="errors1" for="userNo"/>

</h:form>
</p:portletPage>
</f:view>


