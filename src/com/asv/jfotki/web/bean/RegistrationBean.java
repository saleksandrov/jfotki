/*
 * $Id: RegistrationBean.java,v 1.3 2005/04/10 14:02:46 asv Exp $
 */

package com.asv.jfotki.web.bean;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.UserExistsException;
import com.asv.jfotki.model.User;
import com.asv.jfotki.web.builder.PictureBuilder;
import com.asv.jfotki.web.util.FacesUtils;
import com.asv.jfotki.web.util.NavigationResults;
import com.asv.jfotki.web.util.ServiceLocator;
import org.apache.commons.lang.StringUtils;

/**
 * @author Sergey Aleksandrov
 */
public class RegistrationBean {

    public static final String CONFIRM_PASSWORD_ERROR_KEY = "registration_confirm_password_error";
    public static final String USER_EXISTS_ERROR_KEY = "registration_user_exists_error";

    private String username = "";
    private String password = "";
    private String confirmPassword = "";
    private String email = "";
    private String fullName = "";

    public RegistrationBean() throws Exception {
        String username = FacesUtils.getRequestParameter("user");
        if (StringUtils.isNotEmpty(username)) {
            LogFactory.web.debug("Loading user....");
            User user = ServiceLocator.getInstance().getUserService().getUser(username);
            PictureBuilder.populateRegistrationBean(this, user);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String storeUser() throws Exception {
        if (!getPassword().equals(getConfirmPassword())) {
            String errorMessage = FacesUtils.getMessageByKey(CONFIRM_PASSWORD_ERROR_KEY);
            FacesUtils.addErrorMessage(errorMessage);
            return NavigationResults.ERROR;
        }
        User user = PictureBuilder.createUser(this);
        user.setEnabled(true);
        try {
            ServiceLocator.getInstance().getUserService().register(user);
        } catch (UserExistsException e) {
            String errorMessage = FacesUtils.getMessageByKey(USER_EXISTS_ERROR_KEY);
            FacesUtils.addErrorMessage(errorMessage);
            return NavigationResults.ERROR;
        }

        return NavigationResults.SUCCESS;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
