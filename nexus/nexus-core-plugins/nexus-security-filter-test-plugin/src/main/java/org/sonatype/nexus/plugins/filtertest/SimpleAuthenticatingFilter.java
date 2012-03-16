/**
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.filtertest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.enterprise.inject.Typed;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
public class SimpleAuthenticatingFilter extends AuthenticatingFilter
{
    @Override
    protected AuthenticationToken createToken( ServletRequest request, ServletResponse response )
        throws Exception
    {
        
        // look for header
        String header = getAuthzHeader( request );
        if( "bar".equals( header ) )
        {
            return createToken( "admin", "admin123",request, response );
        }

        
        return createToken( "", "",request, response );
    }

    @Override
    protected boolean onAccessDenied( ServletRequest request, ServletResponse response )
        throws Exception
    {
        if( isLoginRequest( request, response ))
        {
            return executeLogin( request, response );
        }

        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        return getAuthzHeader( request ) != null;
    }
    
    private String getAuthzHeader( ServletRequest request )
    {
        HttpServletRequest httpRequest = WebUtils.toHttp( request );
        return httpRequest.getHeader("foo");
    }
}
