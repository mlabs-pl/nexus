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
package org.sonatype.nexus.log.internal;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;
import org.sonatype.nexus.logging.LoggingEvent.Level;
import org.sonatype.plexus.appevents.ApplicationEventMulticaster;

import com.google.common.base.Preconditions;

/**
 * A simple wrapper for SLF4J Logger, used by components and other classes, that makes the special appender existence
 * unneeded, and hence, stops the hard binding for presence of LogBack in same class loader as Nexus is. This stops some
 * known problems with J2EE containers, but also simplifies our own "bundle", that contains Logback on "Server" level,
 * but hidden as server files (!), and also in Nexus WAR. Currently, same as event appender, we "postprocess" the WARN
 * events only.
 * 
 * @author cstamas
 * @since 2.1
 */
public class NexusWrappedLogger
    implements Logger
{
    private final ApplicationEventMulticaster eventMulticaster;

    private final Level levelThreshold;

    private final Logger logger;

    public NexusWrappedLogger( final ApplicationEventMulticaster eventMulticaster, final Level levelThreshold,
                               final Logger logger )
    {
        this.eventMulticaster = Preconditions.checkNotNull( eventMulticaster, "ApplicationEventMulticaster is null!" );
        this.levelThreshold = Preconditions.checkNotNull( levelThreshold, "Level threshold is null!" );
        this.logger = Preconditions.checkNotNull( logger, "Logger to be wrapped is null!" );
    }

    protected void maybeFireEvent( final Level level, final Throwable throwable, final String format,
                                   final Object... arg )
    {
        if ( levelThreshold.compareTo( level ) <= 0 )
        {
            final String formattedMessage = formatMessage( format, arg );
            fireEvent( level, formattedMessage, throwable );
        }
    }

    protected void fireEvent( final Level level, final String message, final Throwable throwable )
    {
        eventMulticaster.notifyEventListeners( new LogbackLoggingEvent( logger, level, message, throwable ) );
    }

    protected String formatMessage( final String format, Object... arg )
    {
        return MessageFormatter.arrayFormat( format, arg ).getMessage();
    }

    public String getName()
    {
        return logger.getName();
    }

    public boolean isTraceEnabled()
    {
        return logger.isTraceEnabled();
    }

    public void trace( String msg )
    {
        logger.trace( msg );
        maybeFireEvent( Level.TRACE, null, msg );
    }

    public void trace( String format, Object arg )
    {
        logger.trace( format, arg );
        maybeFireEvent( Level.TRACE, null, format, arg );
    }

    public void trace( String format, Object arg1, Object arg2 )
    {
        logger.trace( format, arg1, arg2 );
        maybeFireEvent( Level.TRACE, null, format, arg1, arg2 );
    }

    public void trace( String format, Object[] argArray )
    {
        logger.trace( format, argArray );
        maybeFireEvent( Level.TRACE, null, format, argArray );
    }

    public void trace( String msg, Throwable t )
    {
        logger.trace( msg, t );
        maybeFireEvent( Level.TRACE, t, msg );
    }

    public boolean isTraceEnabled( Marker marker )
    {
        return logger.isTraceEnabled( marker );
    }

    public void trace( Marker marker, String msg )
    {
        logger.trace( marker, msg );
        maybeFireEvent( Level.TRACE, null, msg );
    }

    public void trace( Marker marker, String format, Object arg )
    {
        logger.trace( marker, format, arg );
        maybeFireEvent( Level.TRACE, null, format, arg );
    }

    public void trace( Marker marker, String format, Object arg1, Object arg2 )
    {
        logger.trace( marker, format, arg1, arg2 );
        maybeFireEvent( Level.TRACE, null, format, arg1, arg2 );
    }

    public void trace( Marker marker, String format, Object[] argArray )
    {
        logger.trace( marker, format, argArray );
        maybeFireEvent( Level.TRACE, null, format, argArray );
    }

    public void trace( Marker marker, String msg, Throwable t )
    {
        logger.trace( marker, msg, t );
        maybeFireEvent( Level.TRACE, t, msg );
    }

    public boolean isDebugEnabled()
    {
        return logger.isDebugEnabled();
    }

    public void debug( String msg )
    {
        logger.debug( msg );
        maybeFireEvent( Level.DEBUG, null, msg );
    }

    public void debug( String format, Object arg )
    {
        logger.debug( format, arg );
        maybeFireEvent( Level.DEBUG, null, format, arg );
    }

    public void debug( String format, Object arg1, Object arg2 )
    {
        logger.debug( format, arg1, arg2 );
        maybeFireEvent( Level.DEBUG, null, format, arg1, arg2 );
    }

    public void debug( String format, Object[] argArray )
    {
        logger.debug( format, argArray );
        maybeFireEvent( Level.DEBUG, null, format, argArray );
    }

    public void debug( String msg, Throwable t )
    {
        logger.debug( msg, t );
        maybeFireEvent( Level.DEBUG, t, msg );
    }

    public boolean isDebugEnabled( Marker marker )
    {
        return logger.isDebugEnabled( marker );
    }

    public void debug( Marker marker, String msg )
    {
        logger.debug( marker, msg );
        maybeFireEvent( Level.DEBUG, null, msg );
    }

    public void debug( Marker marker, String format, Object arg )
    {
        logger.debug( marker, format, arg );
        maybeFireEvent( Level.DEBUG, null, format, arg );
    }

    public void debug( Marker marker, String format, Object arg1, Object arg2 )
    {
        logger.debug( marker, format, arg1, arg2 );
        maybeFireEvent( Level.DEBUG, null, format, arg1, arg2 );
    }

    public void debug( Marker marker, String format, Object[] argArray )
    {
        logger.debug( marker, format, argArray );
        maybeFireEvent( Level.DEBUG, null, format, argArray );
    }

    public void debug( Marker marker, String msg, Throwable t )
    {
        logger.debug( marker, msg, t );
        maybeFireEvent( Level.DEBUG, t, msg );
    }

    public boolean isInfoEnabled()
    {
        return logger.isInfoEnabled();
    }

    public void info( String msg )
    {
        logger.info( msg );
        maybeFireEvent( Level.INFO, null, msg );
    }

    public void info( String format, Object arg )
    {
        logger.info( format, arg );
        maybeFireEvent( Level.INFO, null, format, arg );
    }

    public void info( String format, Object arg1, Object arg2 )
    {
        logger.info( format, arg1, arg2 );
        maybeFireEvent( Level.INFO, null, format, arg1, arg2 );
    }

    public void info( String format, Object[] argArray )
    {
        logger.info( format, argArray );
        maybeFireEvent( Level.INFO, null, format, argArray );
    }

    public void info( String msg, Throwable t )
    {
        logger.info( msg, t );
        maybeFireEvent( Level.INFO, t, msg );
    }

    public boolean isInfoEnabled( Marker marker )
    {
        return logger.isInfoEnabled( marker );
    }

    public void info( Marker marker, String msg )
    {
        logger.info( marker, msg );
        maybeFireEvent( Level.INFO, null, msg );
    }

    public void info( Marker marker, String format, Object arg )
    {
        logger.info( marker, format, arg );
        maybeFireEvent( Level.INFO, null, format, arg );
    }

    public void info( Marker marker, String format, Object arg1, Object arg2 )
    {
        logger.info( marker, format, arg1, arg2 );
        maybeFireEvent( Level.INFO, null, format, arg1, arg2 );
    }

    public void info( Marker marker, String format, Object[] argArray )
    {
        logger.info( marker, format, argArray );
        maybeFireEvent( Level.INFO, null, format, argArray );
    }

    public void info( Marker marker, String msg, Throwable t )
    {
        logger.info( marker, msg, t );
        maybeFireEvent( Level.INFO, t, msg );
    }

    public boolean isWarnEnabled()
    {
        return logger.isWarnEnabled();
    }

    public void warn( String msg )
    {
        logger.warn( msg );
        maybeFireEvent( Level.WARN, null, msg );
    }

    public void warn( String format, Object arg )
    {
        logger.warn( format, arg );
        maybeFireEvent( Level.WARN, null, format, arg );
    }

    public void warn( String format, Object[] argArray )
    {
        logger.warn( format, argArray );
        maybeFireEvent( Level.WARN, null, format, argArray );
    }

    public void warn( String format, Object arg1, Object arg2 )
    {
        logger.warn( format, arg1, arg2 );
        maybeFireEvent( Level.WARN, null, format, arg1, arg2 );
    }

    public void warn( String msg, Throwable t )
    {
        logger.warn( msg, t );
        maybeFireEvent( Level.WARN, t, msg );
    }

    public boolean isWarnEnabled( Marker marker )
    {
        return logger.isWarnEnabled( marker );
    }

    public void warn( Marker marker, String msg )
    {
        logger.warn( marker, msg );
        maybeFireEvent( Level.WARN, null, msg );
    }

    public void warn( Marker marker, String format, Object arg )
    {
        logger.warn( marker, format, arg );
        maybeFireEvent( Level.WARN, null, format, arg );
    }

    public void warn( Marker marker, String format, Object arg1, Object arg2 )
    {
        logger.warn( marker, format, arg1, arg2 );
        maybeFireEvent( Level.WARN, null, format, arg1, arg2 );
    }

    public void warn( Marker marker, String format, Object[] argArray )
    {
        logger.warn( marker, format, argArray );
        maybeFireEvent( Level.WARN, null, format, argArray );
    }

    public void warn( Marker marker, String msg, Throwable t )
    {
        logger.warn( marker, msg, t );
        maybeFireEvent( Level.WARN, t, msg );
    }

    public boolean isErrorEnabled()
    {
        return logger.isErrorEnabled();
    }

    public void error( String msg )
    {
        logger.error( msg );
        maybeFireEvent( Level.ERROR, null, msg );
    }

    public void error( String format, Object arg )
    {
        logger.error( format, arg );
        maybeFireEvent( Level.ERROR, null, format, arg );
    }

    public void error( String format, Object arg1, Object arg2 )
    {
        logger.error( format, arg1, arg2 );
        maybeFireEvent( Level.ERROR, null, format, arg1, arg2 );
    }

    public void error( String format, Object[] argArray )
    {
        logger.error( format, argArray );
        maybeFireEvent( Level.ERROR, null, format, argArray );
    }

    public void error( String msg, Throwable t )
    {
        logger.error( msg, t );
        maybeFireEvent( Level.ERROR, t, msg );
    }

    public boolean isErrorEnabled( Marker marker )
    {
        return logger.isErrorEnabled( marker );
    }

    public void error( Marker marker, String msg )
    {
        logger.error( marker, msg );
        maybeFireEvent( Level.ERROR, null, msg );
    }

    public void error( Marker marker, String format, Object arg )
    {
        logger.error( marker, format, arg );
        maybeFireEvent( Level.ERROR, null, format, arg );
    }

    public void error( Marker marker, String format, Object arg1, Object arg2 )
    {
        logger.error( marker, format, arg1, arg2 );
        maybeFireEvent( Level.ERROR, null, format, arg1, arg2 );
    }

    public void error( Marker marker, String format, Object[] argArray )
    {
        logger.error( marker, format, argArray );
        maybeFireEvent( Level.ERROR, null, format, argArray );
    }

    public void error( Marker marker, String msg, Throwable t )
    {
        logger.error( marker, msg, t );
        maybeFireEvent( Level.ERROR, t, msg );
    }
}
