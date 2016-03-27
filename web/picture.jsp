<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>


<%@ include file="header.jsp" %>

<f:view>
<f:loadBundle basename="com.asv.Messages" var="bundle"/>
    <h:outputLink id="back" value="home.jsf" title="back" >
        <c:if test="${param.user != null}">
            <f:param name="user" value="#{param.user}"/>
        </c:if>
        <f:param name="albumId" value="#{pictureBean.albumId}"/>
        <h:outputText value="#{bundle.back}"/>
    </h:outputLink>


    <h:panelGrid columns="1" width="100%" styleClass="alignCenter">
          <h:form>
            <h:commandLink actionListener="#{pictureBean.nextPicture}" >
                <h:outputText value="#{bundle.next}"/>
                <f:param value="#{pictureBean.id}" name="id"/>
                <c:if test="${param.user != null}">
                    <f:param name="user" value="#{param.user}"/>
                </c:if>
                <f:param  name="albumId" value="#{pictureBean.albumId}"/>
            </h:commandLink>
          </h:form>
          <h:outputText value="#{pictureBean.name}" styleClass="pictureHeader" />
          <h:outputText value="#{pictureBean.description}"/>
          <h:graphicImage url="image?pictureId=#{pictureBean.id}&type=big" />
    </h:panelGrid>
</f:view>

<%@ include file="footer.jsp" %>
