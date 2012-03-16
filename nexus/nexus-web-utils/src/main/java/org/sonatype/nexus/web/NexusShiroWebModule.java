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
package org.sonatype.nexus.web;

import com.google.inject.Injector;
import com.google.inject.Key;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.sonatype.nexus.security.filter.authc.NexusSecureHttpAuthenticationFilter;
import org.sonatype.nexus.security.filter.authz.FailureLoggingHttpMethodPermissionFilter;
import org.sonatype.nexus.security.filter.authz.NexusTargetMappingAuthorizationFilter;
import org.sonatype.security.web.filter.authc.LogoutAuthenticationFilter;
import org.sonatype.security.web.guice.ShiroWebGuiceModule;

import javax.servlet.ServletContext;

/**
 *
 */
public class NexusShiroWebModule extends ShiroWebGuiceModule
{
    public NexusShiroWebModule( ServletContext servletContext )
    {
        super( servletContext );
    }
    
    private Key<AccessControlFilter> bindAuthcFilter( String name, boolean fakeAuthSchem, String applicationName )
    {
        NexusSecureHttpAuthenticationFilter filter = new NexusSecureHttpAuthenticationFilter();
        filter.setApplicationName( applicationName );
        filter.setFakeAuthScheme( Boolean.toString( fakeAuthSchem ) );

        return bindAccessControlFilter( name, filter );
    }
    
    private Key<AccessControlFilter> bindTargetMappingFilter( String name, String pathPrefix, String pathReplacement )
    {
        NexusTargetMappingAuthorizationFilter filter = new NexusTargetMappingAuthorizationFilter();
        filter.setPathPrefix( pathPrefix );
        filter.setPathReplacement( pathReplacement );

        return bindAccessControlFilter( name, filter );
    }

    @Override
    protected void configureShiroWeb()
    {
        super.configureShiroWeb();

        bindAuthcFilter( "authcBasic", false, "Sonatype Nexus Repository Manager API" );
        bindAuthcFilter( "authcNxBasic", true, "Sonatype Nexus Repository Manager API (specialized auth)" );
        bindAuthcFilter( "contentAuthcBasic", false, "Sonatype Nexus Repository Manager" );
        bindAuthcFilter( "authcApiKey", false, "Sonatype Nexus Repository Manager API (X-...-ApiKey auth)" );

        bindAccessControlFilter( "logout", new LogoutAuthenticationFilter() );
        bindAccessControlFilter( "perms", new FailureLoggingHttpMethodPermissionFilter() );

        bindTargetMappingFilter( "trperms", "/service/local/repositories/(.*)/content(.*)", "/repositories/@1@2" );
        bindTargetMappingFilter( "tiperms", "/service/local/repositories/(.*)/index_content(.*)", "/repositories/@1@2" );

        bindTargetMappingFilter( "tgperms", "/service/local/repo_groups/(.*)/content(.*)", "/groups/@1@2" );
        bindTargetMappingFilter( "tgiperms", "/service/local/repo_groups/(.*)/index_content(.*)", "/groups/@1@2" );

        bindTargetMappingFilter( "contentTperms", "/content(.*)", "@1" );

        this.configureFilterChainManager();
    }

}
