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

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;
import org.sonatype.plexus.rest.resource.AbstractPlexusResource;
import org.sonatype.plexus.rest.resource.PathProtectionDescriptor;
import org.sonatype.plexus.rest.resource.PlexusResource;

@Component( role = PlexusResource.class, hint = "FilterTestPlexusResource" )
public class FilterTestPlexusResource
    extends AbstractPlexusResource implements Initializable
{

    @Requirement
    private FilterChainManager filterChainManager;

    @Override
    public Object getPayloadInstance()
    {
        return null;
    }

    @Override
    public PathProtectionDescriptor getResourceProtection()
    {
        // unprotected resource
        return new PathProtectionDescriptor( "/filtertest", "simple,perms[nexus:filtertest]" );
    }

    @Override
    public String getResourceUri()
    {
        return "/filtertest";
    }


    @Override
    public Object get( Context context, Request request, Response response, Variant variant )
        throws ResourceException
    {
        return "Filter Test!";
    }

    /**
     * Using this method to add the Shiro FilterChain.
     * @throws InitializationException
     */
    @Override
    public void initialize()
        throws InitializationException
    {
        // register a new filter
        filterChainManager.addFilter( "simple", new SimpleAuthenticatingFilter(), true );

        // replace the existing one!
        filterChainManager.addFilter( "authcBasic", new SimpleAuthenticatingFilter(), true );
    }
}
