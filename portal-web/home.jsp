<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>


<%@ include file="header.jsp" %>

<f:view >
    <f:loadBundle basename="com.asv.Messages" var="bundle"/>
    <script>
    function confirmDelete(linkObj) {
        if (confirm('Delete picture?')) {
            linkObj.onclick();
         }
    }
    </script>

    <h:form>

    <h:panelGrid columns="2" width="100%" columnClasses="align-top" >
        <h:panelGrid columns="1" cellspacing="15" >
            <h:dataTable id="albums" value="#{albumBean.albums}" var="album" width="200px" >
                <h:column>
                   <f:facet name="header" >
                      <h:outputText value="#{bundle.author}:  #{registrationBean.fullName}" rendered="#{param.user != null}" />
                   </f:facet>
                   <h:commandLink actionListener="#{pictureBean.prepareDynamicData}">
                       <f:param name="albumId" value="#{album.id}"/>
                       <h:outputText value="#{album.name}"/>
                   </h:commandLink>
                </h:column>
            </h:dataTable>

           <% if (renderRequest.isUserInRole(Role.ADMIN_ROLE.getName())) { %>
               <h:commandLink id="addAlbumLink" action="addAlbum" title="#{bundle.create_album_title}">
                   <c:if test="${param.albumId != null}">
                       <f:param name="albumId" value="#{param.albumId}"/>
                   </c:if>
                   <h:outputText value="#{bundle.create_album}"/>
               </h:commandLink>
           <% } %>

       </h:panelGrid>

       <h:panelGrid columns="1">
            <h:commandLink value="editPicture.jsf"
                          title="#{bundle.upload_picture_title}"
                       rendered="#{param.albumId != null && securityBean.albumOwner}" >
                <c:if test="${param.user != null}">
                    <f:param name="user" value="#{param.user}"/>
                </c:if>
                <c:if test="${param.albumId != null}">
                    <f:param name="albumId" value="#{param.albumId}"/>
                </c:if>
               <h:outputText value="#{bundle.upload_picture}"/>
            </h:commandLink>

            <h:panelGrid    id="picturesTable"
                       columns="3"
                       binding="#{pictureBean.dynamicData}"
                         width="750px"
                    rowClasses="dtab"
                   cellspacing="0"
                   cellpadding="10">
                   <f:facet name="header" >
                        <h:outputText value="#{pictureBean.albumName}"/>
                   </f:facet>
            </h:panelGrid>
        </h:panelGrid>
        
    </h:panelGrid>
</h:form>
</f:view>
