<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>


<%@ include file="header.jsp" %>

<f:view>
<f:loadBundle basename="com.asv.Messages" var="bundle"/>
    <h:outputLink id="back" value="home.jsf" title="back" >
      <h:outputText value="#{bundle.back}"/>
    </h:outputLink>
    <h:form>
      <h:messages styleClass="errorMessage" globalOnly="true"/> 
      <h:panelGrid columns="1">
          <h:panelGrid columns="3"  >
                  <h:outputLabel for="username" value="#{bundle.registration_username}"/>
                  <h:message for="username" styleClass="errorMessage" />
                  <h:inputText id="username" value="#{registrationBean.username}" title="#{bundle.registration_username}" maxlength="80" required="true"  />

                  <h:outputLabel for="password" value="#{bundle.registration_password}"/>
                  <h:message for="password"  styleClass="errorMessage"/>
                  <h:inputSecret id="password" value="#{registrationBean.password}" title="#{bundle.registration_password}" maxlength="100" required="true" />

                  <h:outputLabel for="confirmPassword" value="#{bundle.registration_confirm_password}"/>
                  <h:message for="confirmPassword"  styleClass="errorMessage"/>
                  <h:inputSecret id="confirmPassword" value="#{registrationBean.confirmPassword}" title="#{bundle.registration_confirm_password}" maxlength="100" required="true" />

                  <h:outputLabel for="fullName" value="#{bundle.registration_fullname}"/>
                  <h:message for="fullName"  styleClass="errorMessage"/>
                  <h:inputText id="fullName" value="#{registrationBean.fullName}" title="#{bundle.registration_fullname}" maxlength="200" required="true" />

                  <h:outputLabel for="email" value="#{bundle.registration_email}"/>
                  <h:message for="email"  styleClass="errorMessage"/>
                  <h:inputText id="email" value="#{registrationBean.email}" title="#{bundle.registration_email}" maxlength="100" required="true" />

          </h:panelGrid>
         <h:commandButton action="#{registrationBean.storeUser}" value="#{bundle.registration_submit}" />
      </h:panelGrid>
    </h:form>
</f:view>

<%@ include file="footer.jsp" %>
