/*
 * $Id: SecurityAdapter.java,v 1.1 2005/06/22 16:02:08 asv Exp $
 */

package com.asv.jfotki.common.security;

import net.sf.acegisecurity.providers.dao.User;
import net.sf.acegisecurity.context.Context;
import net.sf.acegisecurity.context.ContextHolder;
import net.sf.acegisecurity.context.security.SecureContext;
import net.sf.acegisecurity.Authentication;
import net.sf.acegisecurity.GrantedAuthority;

/**
 * @author Sergey Aleksandrov
 */
public interface SecurityAdapter {

    public String getCurrentPrincipalName();

    public boolean isMemberOfRole(String roleName);

}
