/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import it.sauronsoftware.cron4j.Predictor;
import it.sauronsoftware.cron4j.SchedulingPattern;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.compiere.util.Env;
import org.compiere.util.Util;
import org.idempiere.cache.ImmutableIntPOCache;
import org.idempiere.cache.ImmutablePOSupport;

/**
 * Schedule model for scheduler 
 */
public class MSchedule extends X_AD_Schedule implements ImmutablePOSupport
{
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 7183417983901074702L;
	private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
	private static final Pattern VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern,Pattern.CASE_INSENSITIVE);
	private static final Pattern VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern,Pattern.CASE_INSENSITIVE);	

    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param AD_Schedule_UU  UUID key
     * @param trxName Transaction
     */
    public MSchedule(Properties ctx, String AD_Schedule_UU, String trxName) {
        super(ctx, AD_Schedule_UU, trxName);
    }

    /**
     * @param ctx
     * @param AD_Schedule_ID
     * @param trxName
     */
	public MSchedule(Properties ctx, int AD_Schedule_ID, String trxName) {
		super(ctx, AD_Schedule_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MSchedule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Copy constructor
	 * @param copy
	 */
	public MSchedule(MSchedule copy) {
		this(Env.getCtx(), copy);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 */
	public MSchedule(Properties ctx, MSchedule copy) {
		this(ctx, copy, (String) null);
	}

	/**
	 * Copy constructor
	 * @param ctx
	 * @param copy
	 * @param trxName
	 */
	public MSchedule(Properties ctx, MSchedule copy, String trxName) {
		this(ctx, 0, trxName);
		copyPO(copy);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (SCHEDULETYPE_Frequency.equals(getScheduleType()))
		{
			// Set default frequency type and frequency 
			if (getFrequencyType() == null)
				setFrequencyType(FREQUENCYTYPE_Day);
			if (getFrequency() < 1)
				setFrequency(1);
			setCronPattern(null);
		}
		else if (SCHEDULETYPE_CronSchedulingPattern.equals(getScheduleType()))
		{
			// Validate cron pattern
			String pattern = getCronPattern();
			if (pattern != null && pattern.trim().length() > 0)
			{
				if (!SchedulingPattern.validate(pattern))
				{
					log.saveError("Error", "InvalidCronPattern");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 	Is it OK to Run process on this server based on server IP verification.
	 *	@return true if it is ok to run
	 */
	public boolean isOKtoRunOnIP()
	{
		if (!isActive()) {
			return false;
		}
		String ipOnly = getRunOnlyOnIP();
		// 0.0.0.0 = all ip address
		if ((ipOnly == null) || (ipOnly.length() == 0) || "0.0.0.0".equals(ipOnly))
			return true;

		StringTokenizer st = new StringTokenizer(ipOnly, ";");
		while (st.hasMoreElements())
		{
			String ip = st.nextToken();
			if (checkIP(ip))
				return true;
		}
		return false;
	}	//	isOKtoRunOnIP

	/**
	 * 	check whether this server's IP match the ipOnly argument
	 *	@param ipOnly
	 *	@return true if server IP match ipOnly
	 */
	private boolean checkIP(String ipOnly) {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) 
			{
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) 
				{
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (   !inetAddress.isLoopbackAddress() 
						&& !inetAddress.isLinkLocalAddress()
						&& inetAddress.isSiteLocalAddress()) 
					{
						String retVal = inetAddress.getHostAddress().toString();
						if (chekIPFormat(ipOnly)) {
							retVal = inetAddress.getHostAddress().toString();
						} else {
							retVal = inetAddress.getHostName();
						}
						if (ipOnly.equals(retVal)) {
							if (log.isLoggable(Level.INFO)) log.info("Allowed here - IP=" + retVal+ " match");
							return true;
						} else {
							if (log.isLoggable(Level.INFO)) log.info("Not Allowed here - IP=" + retVal+ " does not match " + ipOnly);
						}
					}
				}
			}
			if (!chekIPFormat(ipOnly)) {
				// verify with the local hostname
				String retVal = InetAddress.getLocalHost().getCanonicalHostName();
				if (ipOnly.equals(retVal)) {
					if (log.isLoggable(Level.INFO)) log.info("Allowed here - IP=" + retVal+ " match");
					return true;
				} else {
					if (log.isLoggable(Level.INFO)) log.info("Not Allowed here - IP=" + retVal+ " does not match " + ipOnly);
				}
			}
		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}
		return false;
	} // checkIP

	/**
	 * Get MSchedule from cache (immutable)
	 * @param AD_Schedule_ID
	 * @return MSchedule
	 */
	public static MSchedule get(int AD_Schedule_ID) 
	{
		return get(Env.getCtx(), AD_Schedule_ID);
	}
	
	/**
	 * Get MSchedule from cache (immutable)
	 * @param ctx
	 * @param AD_Schedule_ID
	 * @return MSchedule
	 */
	public static MSchedule get(Properties ctx, int AD_Schedule_ID) 
	{
		Integer key = Integer.valueOf(AD_Schedule_ID);
		MSchedule retValue = s_cache.get (ctx, key, e -> new MSchedule(ctx, e));
		if (retValue != null)
			return retValue;
		retValue = new MSchedule (ctx, AD_Schedule_ID, (String)null);
		if (retValue.get_ID() == AD_Schedule_ID)
		{
			s_cache.put (key, retValue, e -> new MSchedule(Env.getCtx(), e));
			return retValue;
		}
		return null;
	}

	/**	Cache						*/
	private static ImmutableIntPOCache<Integer, MSchedule> s_cache = new ImmutableIntPOCache<Integer, MSchedule> (Table_Name, 10);

	/**
	 * @param ipOnly
	 * @return true if ipOnly is IPV4 IPV6 address
	 */
	public boolean chekIPFormat(String ipOnly)
	{
		boolean IsIp = false;
		try {						
			
			Matcher m1 = VALID_IPV4_PATTERN.matcher(ipOnly);
			if (m1.matches()) {
				IsIp = true;
			} else {
				Matcher m2 = VALID_IPV6_PATTERN.matcher(ipOnly);
				if (m2.matches()) {
					IsIp = true;
				} else {
					IsIp = false;
				}
			}
		} catch (PatternSyntaxException e) {
			// TODO: handle exception
			if (log.isLoggable(Level.FINE)) log.fine("Error: " + e.getLocalizedMessage());
		}
		return IsIp;
	}

	/**
	 * 	Get Next Run
	 *	@param last in MS
	 *  @param scheduleType
	 *  @param frequencyType
	 *  @param frequency
	 *  @param cronPattern
	 *	@return next run in MS
	 *  @deprecated
	 */
	@Deprecated
	public static long getNextRunMS (long last, String scheduleType, String frequencyType, int frequency, String cronPattern)
	{
		return getNextRunMS(last, scheduleType, frequencyType, frequency, cronPattern, null);
	}
	
	/**
	 * 	Get Next Run
	 *	@param last in MS
	 *  @param scheduleType
	 *  @param frequencyType
	 *  @param frequency
	 *  @param cronPattern
	 *  @param timeZone
	 *	@return next run time stamp in millisecond
	 */
	public static long getNextRunMS (long last, String scheduleType, String frequencyType, int frequency, String cronPattern, String timeZone)
	{
		long now = System.currentTimeMillis();
		if (MSchedule.SCHEDULETYPE_Frequency.equals(scheduleType))
		{
			// Calculate sleep interval based on frequency defined
			if (frequency < 1)
				frequency = 1;
			long typeSec = 600;			//	10 minutes
			if (frequencyType == null)
				typeSec = 300;			//	5 minutes
			else if (MSchedule.FREQUENCYTYPE_Minute.equals(frequencyType))
				typeSec = 60;
			else if (MSchedule.FREQUENCYTYPE_Hour.equals(frequencyType))
				typeSec = 3600;
			else if (MSchedule.FREQUENCYTYPE_Day.equals(frequencyType))
				typeSec = 86400;
			long sleepInterval = typeSec * 1000 * frequency;		//	ms

			long next = last + sleepInterval;
			while (next < now)
			{
				next = next + sleepInterval;
			}
			return next;
		}
		else if (MSchedule.SCHEDULETYPE_CronSchedulingPattern.equals(scheduleType))
		{
			if (cronPattern != null && cronPattern.trim().length() > 0
					&& SchedulingPattern.validate(cronPattern)) {
				TimeZone tz = null;
				if (!Util.isEmpty(timeZone)) {
					tz = TimeZone.getTimeZone(timeZone);
					if (tz != null && !tz.getID().equals(timeZone)) {
						tz = null;
					}
				}
				Predictor predictor = new Predictor(cronPattern, last);
				if (tz != null)
					predictor.setTimeZone(tz);
				long next = predictor.nextMatchingTime();
				while (next < now)
				{
					predictor = new Predictor(cronPattern, next);
					if (tz != null)
						predictor.setTimeZone(tz);
					next = predictor.nextMatchingTime();
				}
				return next;
			}
			
		} // not implemented MSchedule.SCHEDULETYPE_MonthDay, MSchedule.SCHEDULETYPE_WeekDay - can be done with cron
		
		return 0;
	}	//	getNextRunMS

	@Override
	public MSchedule markImmutable() {
		if (is_Immutable())
			return this;

		makeImmutable();
		return this;
	}

}
