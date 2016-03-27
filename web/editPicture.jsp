<%@ page contentType="text/html; charset=windows-1251" %>
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

    <form enctype="multipart/form-data" method="POST" action="upload">

        <h:inputHidden id="albumId" value="#{param.albumId}" />
        <h:inputHidden id="user" value="#{param.user}" />

        <table  class="box">
            <tr><td style="padding:20">
                <table>
                    <tr>
                         <td>
                            <span class="errorMessage" ><c:out value="${errorMessage}" /></span>
                        </td>
                    </tr>

<%--
                    <tr>
                        <td>
                           <h:outputLabel for="albumName" value="#{bundle.album_name}"/>
                        </td>
                        <td>
                            <h:selectOneMenu id="albumId" value="#{pictureBean.albumId}">
                                <f:selectItems value="#{albumBean.albumSelectItems}"/>
                            </h:selectOneMenu>

                        </td>
                    </tr>
--%>

                    <tr>
                        <td>
                           <h:outputLabel for="pictureName" value="#{bundle.picture_name}"/>
                        </td>
                        <td>
                            <h:inputText id="pictureName" value="#{pictureBean.name}" title="#{bundle.picture_name}" size="60"  maxlength="80" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                           <h:outputLabel for="pictureDescription" value="#{bundle.picture_desc}"/>
                        </td>
                        <td>
                           <h:inputTextarea  id="pictureDescription" value="#{pictureBean.description}" title="автор" rows="4" cols="60" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                           <h:outputLabel value="#{bundle.file}"/>
                        </td>
                        <td>
                            <input id="picture" type="file" name="imageFile"/>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="2">
                            <h:commandButton value="#{bundle.create}"/>
                        </td>
                    </tr>
                </table>
            </td></tr>
        </table>
        </form>

</f:view>

<%@ include file="footer.jsp" %>

