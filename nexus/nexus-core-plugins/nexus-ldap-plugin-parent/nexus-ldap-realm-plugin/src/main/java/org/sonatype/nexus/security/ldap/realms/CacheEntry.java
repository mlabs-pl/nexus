package org.sonatype.nexus.security.ldap.realms;

public class CacheEntry<TItem>
{
	private static final long MINUTES_30 = 1000 * 60 * 30;
	private TItem m_item;
	
	private long m_createdOn;
	
	public CacheEntry (TItem itemToCache)
	{
		m_createdOn = System.currentTimeMillis ();
		m_item = itemToCache;
	}
	
	public boolean isValid ()
	{
		long now = System.currentTimeMillis ();
		return now - m_createdOn <= MINUTES_30;
	}
	
	public TItem getItem ()
	{
		return m_item;
	}
}
