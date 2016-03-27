/*
 * $Id: PictureCacheInterceptor.java,v 1.2 2005/01/24 10:14:35 asv Exp $
 */

package com.asv.jfotki.business.interceptors;

import com.asv.jfotki.common.LogFactory;
import com.asv.jfotki.common.exception.ApplicationRuntimeException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.util.jcache.CacheAccess;
import javax.util.jcache.CacheAccessFactory;
import javax.util.jcache.CacheException;
import javax.util.jcache.ObjectNotFoundException;

/**
 * Used for cache picture files.
 *
 * @author Sergey Aleksandrov
 */
public class PictureCacheInterceptor implements MethodInterceptor {

    private boolean cacheEnable;

    private CacheAccessFactory cacheAccessFactory;

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (cacheEnable) {
            LogFactory.web.debug("Using caching interceptor");

            Integer pictureId = (Integer) methodInvocation.getArguments()[0];
            String cacheKey = getClass().getName() + pictureId;

            Object pictureObject = null;
            CacheAccess cacheAccess = null;
            try {
                cacheAccess = cacheAccessFactory.getAccess();
                pictureObject = cacheAccess.get(cacheKey);
                LogFactory.web.debug("[Cache] Getting object from cache. Key = " + cacheKey);
            } catch(ObjectNotFoundException onf) {
                LogFactory.web.debug("[Cache] Load Object from DB. Key = " + cacheKey);
                try {
                    pictureObject = methodInvocation.proceed();
                    cacheAccess.put(cacheKey, pictureObject);
                } catch (CacheException e) {
                    LogFactory.web.debug("Cannot put object into cache! ", e);
                    throw new ApplicationRuntimeException("Cannot put object into a cache! ", e);
                }
            } catch (CacheException e) {
                LogFactory.web.error("Cache error! ", e);
                throw new ApplicationRuntimeException("Cache error! ", e);
            }
            return (byte[]) pictureObject;
        } else {
            return methodInvocation.proceed();
        }
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

    public void setCacheAccessFactory(CacheAccessFactory cacheAccessFactory) {
        this.cacheAccessFactory = cacheAccessFactory;
    }
}
