<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>


<%@ include file="header.jsp" %>

<f:view>
<f:loadBundle basename="com.asv.Messages" var="bundle"/>
    <h:outputLink id="back" value="home.jsf" title="back" >
        <c:if test="${param.user != null}">
            <f:param name="user" value="#{param.user}"/>
        </c:if>
        <c:if test="${param.albumId != null}">
            <f:param name="albumId" value="#{param.albumId}"/>
       </c:if>

      <h:outputText value="#{bundle.back}"/>
    </h:outputLink>
    <h:form>
      <input type="hidden" name="user" value='<h:outputText value="#{param.user}"/>'>
      <h:panelGrid columns="1">
          <h:panelGrid columns="3"  >
                  <h:outputLabel for="albumName" value="#{bundle.album_name}"/>
                  <h:message for="albumName" styleClass="errorMessage" />
                  <h:inputText id="albumName" value="#{albumBean.name}" title="#{bundle.album_name}" maxlength="80" required="true"  />
          </h:panelGrid>
         <h:commandButton action="#{albumBean.storeAlbum}" value="#{bundle.create}" />
      </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="footer.jsp" %>
