/*
 * $Id: RedirectHelper.java,v 1.2 2005/01/25 09:19:36 asv Exp $
 */

package com.asv.jfotki.web.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sergey Aleksandrov
 */
public class RedirectHelper {
    private String albumId;
    private String username;

    public RedirectHelper(String albumId, String username) {
        this.albumId = albumId;
        this.username = username;
    }

    public RedirectHelper(int albumId, String username) {
        this.albumId = String.valueOf(albumId);
        this.username = username;
    }

    public String getRedirectUrl() {
        StringBuffer url = new StringBuffer("home.jsf");
        if (StringUtils.isNotEmpty(albumId) || StringUtils.isNotEmpty(username)) {
            url.append("?");
        }
        boolean isFirst = true;
        if (StringUtils.isNotEmpty(albumId)) {
            url.append("albumId=");
            url.append(albumId);
            isFirst = false;
        }
        if (StringUtils.isNotEmpty(username)) {
            if (!isFirst) {
                url.append("&");
            }
            url.append("user=");
            url.append(username);
        }

        return url.toString();
    }
}
