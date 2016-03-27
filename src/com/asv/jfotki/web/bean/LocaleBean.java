package com.asv.jfotki.web.bean;

/*
 * $Id$
 */

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.web.util.FacesUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergey Aleksandrov
 */
public class LocaleBean {
    public static final Locale DEFAULT_LOCALE = new Locale("ru", "RU");

    private Map locales = null;


    public LocaleBean() {
        locales = new HashMap();
        locales.put("English", Locale.ENGLISH);
        locales.put("Russian", new Locale("ru", "RU"));
    }

    public void changeLocale(ActionEvent event) {
        String locale = FacesUtils.getRequestParameter("locale");
        LogFactory.web.debug("Change locale to " + locale + " language!");
        Locale newLocale = (Locale) locales.get(locale);
        if (newLocale == null)
            newLocale = DEFAULT_LOCALE;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(newLocale);
    }
}

