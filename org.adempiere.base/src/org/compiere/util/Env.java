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
package org.compiere.util;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.ImageIcon;

import org.adempiere.base.Core;
import org.adempiere.base.IResourceFinder;
import org.adempiere.util.IProcessUI;
import org.adempiere.util.ServerContext;
import org.adempiere.util.ServerContextProvider;
import org.compiere.Adempiere;
import org.compiere.db.CConnection;
import org.compiere.dbPort.Convert;
import org.compiere.model.GridWindowVO;
import org.compiere.model.MClient;
import org.compiere.model.MQuery;
import org.compiere.model.MRole;
import org.compiere.model.MSequence;
import org.compiere.model.MSession;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.MZoomCondition;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;

/**
 *  Static constants for environment context attribute key.<br/>
 *  Static methods for environment context and session manipulation.
 *
 *  @author     Jorg Janke
 *  @version    $Id: Env.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 *
 *  @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 1619390 ] Use default desktop browser as external browser
 * 			<li>BF [ 2017987 ] Env.getContext(TAB_INFO) should NOT use global context
 * 			<li>FR [ 2392044 ] Introduce Env.WINDOW_MAIN
 */
public final class Env
{	
	//Environments Constants
	public static final String AD_CLIENT_ID = "#AD_Client_ID";
	public static final String AD_CLIENT_NAME = "#AD_Client_Name";
	public static final String AD_ORG_ID = "#AD_Org_ID";		
	public static final String AD_ORG_NAME = "#AD_Org_Name";	
	public static final String AD_PRINTCOLOR_ID = "#AD_PrintColor_ID";
	public static final String AD_PRINTFONT_ID = "#AD_PrintFont_ID";
	public static final String AD_PRINTPAPER_ID = "#AD_PrintPaper_ID";
	public static final String AD_PRINTTABLEFORMAT_ID = "#AD_PrintTableFormat_ID";
	public static final String AD_ROLE_ID = "#AD_Role_ID";
	public static final String AD_ROLE_NAME = "#AD_Role_Name";
	public static final String AD_ROLE_TYPE = "#AD_Role_Type";
	public static final String AD_SESSION_ID = "#AD_Session_ID";
	public static final String AD_USER_ID = "#AD_User_ID";
	public static final String AD_USER_NAME = "#AD_User_Name";
	public static final String C_ACCTSCHEMA_ID = "$C_AcctSchema_ID";
	public static final String C_BANKACCOUNT_ID = "#C_BankAccount_ID";
	public static final String C_BP_GROUP_ID = "#C_BP_Group_ID";
	public static final String C_CASHBOOK_ID = "#C_CashBook_ID";
	public static final String C_CONVERSIONTYPE_ID = "#C_ConversionType_ID";
	public static final String C_COUNTRY_ID = "#C_Country_ID";
	public static final String C_CURRENCY_ID = "$C_Currency_ID";
	public static final String C_DOCTYPETARGET_ID = "#C_DocTypeTarget_ID";
	public static final String C_DUNNING_ID = "#C_Dunning_ID";
	public static final String C_PAYMENTTERM_ID = "#C_PaymentTerm_ID";
	public static final String C_REGION_ID = "#C_Region_ID";
	public static final String C_TAXCATEGORY_ID = "#C_TaxCategory_ID";
	public static final String C_TAX_ID = "#C_Tax_ID";
	public static final String C_UOM_ID = "#C_UOM_ID";
	public static final String CLIENT_INFO_DESKTOP_HEIGHT = "#clientInfo_desktopHeight";
	public static final String CLIENT_INFO_DESKTOP_WIDTH = "#clientInfo_desktopWidth";
	public static final String CLIENT_INFO_MOBILE = "#clientInfo_mobile";
	public static final String CLIENT_INFO_ORIENTATION = "#clientInfo_orientation";
	public static final String CLIENT_INFO_TIME_ZONE = "#clientInfo_timeZone";
	public static final String DATE	= "#Date";
	public static final String DB_TYPE = "#DBType";
	public static final String GL_CATEGORY_ID = "#GL_Category_ID";
	public static final String HAS_ALIAS = "$HasAlias";
	public static final String IS_CAN_APPROVE_OWN_DOC = "#IsCanApproveOwnDoc";
	public static final String IS_CLIENT_ADMIN = "#IsClientAdmin";
	public static final String IS_SSO_LOGIN = "#IsSSOLogin";
	public static final String DEVELOPER_MODE = "#DeveloperMode";
	/** Context Language identifier */
	public static final String LANGUAGE = "#AD_Language";
	public static final String LANGUAGE_NAME = "#LanguageName";
	public static final String LOCAL_HTTP_ADDRESS = "#LocalHttpAddr";
	public static final String LOCALE = "#Locale";
	public static final String M_PRICELIST_ID = "#M_PriceList_ID";
	public static final String M_PRODUCT_CATEGORY_ID = "#M_Product_Category_ID";
	public static final String M_WAREHOUSE_ID = "#M_Warehouse_ID";	
	/** Context for multi factor authentication */
	public static final String MFA_Registration_ID = "#MFA_Registration_ID";
	/** Context for POS ID */
	public static final String POS_ID = "#POS_ID";
	public static final String R_STATUSCATEGORY_ID = "#R_StatusCategory_ID";
	public static final String R_STATUS_ID = "#R_Status_ID";
	public static final String RUNNING_UNIT_TESTING_TEST_CASE = "#RUNNING_UNIT_TESTING_TEST_CASE";
	public static final String SALESREP_ID = "#SalesRep_ID";
	public static final String SHOW_ACCOUNTING = "#ShowAcct";
	public static final String SHOW_ADVANCED = "#ShowAdvanced";
	public static final String SHOW_TRANSLATION = "#ShowTrl";
	public static final String STANDARD_PRECISION = "#StdPrecision";
	public static final String STANDARD_REPORT_FOOTER_TRADEMARK_TEXT = "#STANDARD_REPORT_FOOTER_TRADEMARK_TEXT";
	public static final String SYSTEM_NAME = "#System_Name";
	public static final String THEME = "#Theme";
	public static final String UI_CLIENT = "#UIClient";
	public static final String USER_LEVEL = "#User_Level";

	public static final String PREFIX_SYSTEM_VARIABLE = "$env.";
	
	public static final String PREFIX_SYSCONFIG_VARIABLE = "$sysconfig.";

	@Deprecated
	private final static ContextProvider clientContextProvider = new DefaultContextProvider();
	
	private static List<IEnvEventListener> eventListeners = new ArrayList<IEnvEventListener>();

	public static int adWindowDummyID =200054; 
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(Env.class);

	/**
	 * @param provider
	 * @deprecated
	 */
	@Deprecated
	public static void setContextProvider(ContextProvider provider)
	{
	}

	/**
	 * Add environment event listener
	 * @param listener
	 */
	public static void addEventListener(IEnvEventListener listener)
	{
		eventListeners.add(listener);
	}

	/**
	 * Remove environment event listener
	 * @param listener
	 * @return boolean
	 */
	public static boolean removeEventListener(IEnvEventListener listener)
	{
		return eventListeners.remove(listener);
	}

	/**
	 *	Close session and reset environment upon exit/logout of system.
	 *  @param status System exit status (usually 0 for no error)
	 */
	public static void exitEnv (int status)
	{
		//avoid unnecessary query of session when exit without log in
		if (DB.isConnected()) {
			//	End Session
			MSession session = MSession.get(Env.getCtx());	//	finish
			if (session != null) {
				session = new MSession(getCtx(), session.getAD_Session_ID(), null);
				session.logout();
			}
		}
		//
		reset(true);	// final cache reset
		//
		CLogMgt.shutdown();
		//
		if (Ini.isClient())
			System.exit (status);
	}	//	close

	/**
	 * Logout from the system
	 */
	public static void logout()
	{
		//	End Session
		MSession session = MSession.get(Env.getCtx());	//	finish
		if (session != null) {
			session = new MSession(getCtx(), session.getAD_Session_ID(), null);
			session.logout();
		}
		//
		reset(true);
		//
	}

	/**
	 * 	Reset envronment context
	 * 	@param finalCall true to clear everything otherwise login data remains
	 */
	public static void reset (boolean finalCall)
	{
		IEnvEventListener[] listeners = eventListeners.toArray(new IEnvEventListener[0]);
		for(IEnvEventListener listener : listeners)
		{
			listener.onReset(finalCall);
		}

		//	Clear all Context
		if (finalCall)
			getCtx().clear();
		else	//	clear window context only
		{
			Object[] keys = getCtx().keySet().toArray();
			for (int i = 0; i < keys.length; i++)
			{
				String tag = keys[i].toString();
				if (Character.isDigit(tag.charAt(0)))
					getCtx().remove(keys[i]);
			}
		}
		
		if (Ini.isClient()) {			
			DB.closeTarget();
		}
		
		//	Reset Role Access
		if (!finalCall)
		{
			if (Ini.isClient()) {
				// Cache
				CacheMgt.get().resetLocalCache();
				DB.setDBTarget(CConnection.get());
			}
			MRole defaultRole = MRole.getDefault(getCtx(), false);
			if (defaultRole != null)
				defaultRole.loadAccess(true);	//	Reload
		}
	}	//	resetAll

	/** Window No for Main           */
	public static final int     WINDOW_MAIN = 0;

	/** Tab No for Info                */
	public static final int     TAB_INFO = 1113;

	/**
	 *  Get Context
	 *  @return Properties
	 */
	public static final Properties getCtx()
	{
		return getContextProvider().getContext();
	}   //  getCtx

	/**
	 * Get context provider
	 * @return context provider for current environment
	 */
	public static ContextProvider getContextProvider() {
		if (Ini.isClient())
			return clientContextProvider;
		else
			return ServerContextProvider.INSTANCE;
	}

	/**
	 * Replace the contents of the current session/environment context.<br/>
	 * Don't use this to setup a new session/process context, use ServerContext.setCurrentInstance instead.
	 * @param ctx context
	 */
	public static void setCtx (Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		
		//nothing to do if ctx is already the current context
		if (ServerContext.getCurrentInstance() == ctx)
			return;
		
		getCtx().clear();
		getCtx().putAll(ctx);
	}   //  setCtx

	/**
	 *	Set Global Context to Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, String context, String value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINER)) log.finer("Context " + context + "==" + value);
		//
		if (value == null || value.length() == 0)
			ctx.remove(context);
		else
			ctx.setProperty(context, value);
	}	//	setContext

	/**
	 *	Set Global Context to Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, String context, Timestamp value)
	{
		if (ctx == null || context == null)
			return;
		if (value == null)
		{
			ctx.remove(context);
			if (log.isLoggable(Level.FINER)) log.finer("Context " + context + "==" + value);
		}
		else
		{	//	JDBC Format	2005-05-09 00:00:00.0
			// BUG:3075946 KTU, Fix Thai Date
			//String stringValue = value.toString();
			String stringValue = "";
			Calendar c1 = Calendar.getInstance();
			c1.setTime(value);
			stringValue = DisplayType.getTimestampFormat_Default().format(c1.getTime());
			//	Chop off .0 (nanos)
			//stringValue = stringValue.substring(0, stringValue.indexOf("."));
			// KTU
			ctx.setProperty(context, stringValue);
			if (log.isLoggable(Level.FINER)) log.finer("Context " + context + "==" + stringValue);
		}
	}	//	setContext

	/**
	 *	Set Global Context to (int) Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, String context, int value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINER)) log.finer("Context " + context + "==" + value);
		//
		ctx.setProperty(context, String.valueOf(value));
	}	//	setContext

	/**
	 *	Set Global Context to Y/N Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, String context, boolean value)
	{
		setContext (ctx, context, convert(value));
	}	//	setContext

	/**
	 *	Set Context for WindowNo to Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, String context, String value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINER)) log.finer("Context("+WindowNo+") " + context + "==" + value);
		//
		if (value == null || value.equals(""))
			ctx.remove(WindowNo+"|"+context);
		else
			ctx.setProperty(WindowNo+"|"+context, value);
	}	//	setContext

	/**
	 *	Set Context for WindowNo to Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, String context, Timestamp value)
	{
		if (ctx == null || context == null)
			return;
		if (value == null)
		{
			ctx.remove(WindowNo+"|"+context);
			if (log.isLoggable(Level.FINER)) log.finer("Context("+WindowNo+") " + context + "==" + value);
		}
		else
		{	//	JDBC Format	2005-05-09 00:00:00.0
			// BUG:3075946 KTU, Fix Thai year 
			//String stringValue = value.toString();
			String stringValue = "";
			Calendar c1 = Calendar.getInstance();
			c1.setTime(value);
			stringValue = DisplayType.getTimestampFormat_Default().format(c1.getTime());
			//	Chop off .0 (nanos)
			//stringValue = stringValue.substring(0, stringValue.indexOf("."));
			// KTU
			ctx.setProperty(WindowNo+"|"+context, stringValue);
			if (log.isLoggable(Level.FINER)) log.finer("Context("+WindowNo+") " + context + "==" + stringValue);
		}
	}	//	setContext
	
	/**
	 *	Set Context for WindowNo to int Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, String context, int value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINER)) log.finer("Context("+WindowNo+") " + context + "==" + value);
		//
		ctx.setProperty(WindowNo+"|"+context, String.valueOf(value));
	}	//	setContext

	/**
	 * Set context value for WindowNo and TabNo
	 * @param ctx
	 * @param WindowNo
	 * @param TabNo
	 * @param context context key
	 * @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, int TabNo, String context, int value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINER)) log.finer("Context("+WindowNo+") " + context + "==" + value);
		//
		ctx.setProperty(WindowNo+"|"+TabNo+"|"+context, String.valueOf(value));
	}	//	setContext

	/**
	 *	Set Context for WindowNo to Y/N Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, String context, boolean value)
	{
		setContext (ctx, WindowNo, context, convert(value));
	}	//	setContext

	/**
	 * Convert boolean value to Y or N
	 * @param value
	 * @return Y for true, N for false
	 */
	private static String convert(boolean value) {
		return value ? "Y" : "N";
	}

	/**
	 *	Set Context for WindowNo and TabNo to Y/N Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Properties ctx, int WindowNo, int TabNo, String context, boolean value)
	{
		setContext (ctx, WindowNo, TabNo, context, convert(value));
	}	//	setContext
	
	/**
	 *	Set Context for WindowNo and TabNo to Value
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	public static void setContext (Properties ctx, int WindowNo, int TabNo, String context, String value)
	{
		if (ctx == null || context == null)
			return;
		if (log.isLoggable(Level.FINEST)) log.finest("Context("+WindowNo+","+TabNo+") " + context + "==" + value);
		//
		if (value == null)
			if (context.endsWith("_ID"))
				// TODO: Research potential problems with tables with Record_ID=0
				value = new String("0");
			else
				value = new String("");
		ctx.setProperty(WindowNo+"|"+TabNo+"|"+context, value);
	}	//	setContext

	/**
	 *	Set Auto Commit
	 *  @param ctx context
	 *  @param autoCommit auto commit (save)
	 *  @Deprecated user setProperty instead
	 */
	@Deprecated
	public static void setAutoCommit (Properties ctx, boolean autoCommit)
	{
		if (ctx == null)
			return;
		ctx.setProperty("AutoCommit", convert(autoCommit));
	}	//	setAutoCommit

	/**
	 *	Set Auto Commit for WindowNo
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param autoCommit auto commit (save)
	 */
	public static void setAutoCommit (Properties ctx, int WindowNo, boolean autoCommit)
	{
		if (ctx == null)
			return;
		ctx.setProperty(WindowNo+"|AutoCommit", convert(autoCommit));
	}	//	setAutoCommit

	/**
	 *	Set Auto New Record
	 *  @param ctx context
	 *  @param autoNew auto new record
	 *  @Deprecated user setProperty instead
	 */
	@Deprecated
	public static void setAutoNew (Properties ctx, boolean autoNew)
	{
		if (ctx == null)
			return;
		ctx.setProperty("AutoNew", convert(autoNew));
	}	//	setAutoNew

	/**
	 *	Set Auto New Record for WindowNo
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param autoNew auto new record
	 */
	public static void setAutoNew (Properties ctx, int WindowNo, boolean autoNew)
	{
		if (ctx == null)
			return;
		ctx.setProperty(WindowNo+"|AutoNew", convert(autoNew));
	}	//	setAutoNew

	/**
	 *	Set IsSOTrx Y/N flag
	 *  @param ctx context
	 *  @param isSOTrx SO Context
	 */
	public static void setSOTrx (Properties ctx, boolean isSOTrx)
	{
		if (ctx == null)
			return;
		ctx.setProperty("IsSOTrx", convert(isSOTrx));
	}	//	setSOTrx

	/**
	 *	Get global Value of Context
	 *  @param ctx context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext (Properties ctx, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		if (context.startsWith(PREFIX_SYSTEM_VARIABLE)) {
			String retValue = System.getenv(context.substring(PREFIX_SYSTEM_VARIABLE.length()));
			if (retValue == null)
				retValue = System.getProperty(context.substring(PREFIX_SYSTEM_VARIABLE.length()), "");
			return retValue;
		} else if (isSysConfig(context)) {
			return getSysConfigValue(context, Env.getAD_Org_ID(ctx));
		}
		String value = ctx.getProperty(context, "");
		if (Util.isEmpty(value) && !context.startsWith("#"))
			value = ctx.getProperty("#"+context, "");
		return value;
	}	//	getContext

	/**
	 *	Get Value of Context for WindowNo.
	 *	if not found global context if available and enabled
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext (Properties ctx, int WindowNo, String context, boolean onlyWindow)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("No Ctx");
		if (context == null)
			throw new IllegalArgumentException ("Require Context");
		String s = ctx.getProperty(WindowNo+"|"+context);
		if (s == null)
		{
			if (isSysConfig(context))
			{
				int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, Env.AD_ORG_ID, false);
				return getSysConfigValue(context, AD_Org_ID);
			}
			//	Explicit Base Values			
			if (Env.isGlobalVariable(context) || Env.isPreference(context))
				return getContext(ctx, context);
			if (onlyWindow)			//	no Default values
				return "";
			return getContext(ctx, "#" + context);
		}
		return s;
	}	//	getContext

	private static String getSysConfigValue(String context, int AD_Org_ID) {
		String retValue = MSysConfig.getValue(context.substring(PREFIX_SYSCONFIG_VARIABLE.length()), Env.getAD_Client_ID(Env.getCtx()), AD_Org_ID);
		if (retValue == null)
			retValue = "";
		return retValue;
	}

	/**
	 *	Get Value of Context for WindowNo.<br/>
	 *	If not found, try global context.
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext (Properties ctx, int WindowNo, String context)
	{
		return getContext(ctx, WindowNo, context, false);
	}	//	getContext

	/**
	 * Get Value of Context for WindowNo and TabNo.<br/>
	 * If not found, try global context. <br/>
	 * If TabNo is TAB_INFO, only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or ""
	 */
	public static String getContext (Properties ctx, int WindowNo, int TabNo, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String s = ctx.getProperty(WindowNo+"|"+TabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (Util.isEmpty(s))
		{
			if (isSysConfig(context))
			{
				int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, TabNo, Env.AD_ORG_ID);
				return getSysConfigValue(context, AD_Org_ID);
			}
			return getContext(ctx, WindowNo, context, false);
		}
		return s;
	}	//	getContext

	/**
	 * Get Value of Context for WindowNo and TabNo.<br/>
	 * If not found, try global context.<br/>
	 * If TabNo is TAB_INFO, only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or ""
	 */
	public static String getContext (Properties ctx, int WindowNo, int TabNo, String context, boolean onlyTab)
	{
		return getContext(ctx, WindowNo, TabNo, context, onlyTab, onlyTab);
	}

	/**
	 * Get Value of Context for WindowNo and TabNo.<br/>
	 * If not found, try global context. <br/>
	 * If TabNo is TAB_INFO, only tab's context will be checked.
	 * @param ctx context
	 * @param WindowNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @param onlyWindow if true, no global context will be searched
	 * @return value or ""
	 */
	public static String getContext (Properties ctx, int WindowNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String s = ctx.getProperty(WindowNo+"|"+TabNo+"|"+context);
		//
		if (Util.isEmpty(s) && ! onlyTab)
			return getContext(ctx, WindowNo, context, onlyWindow);
		return s;
	}	//	getContext

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param context context key
	 *  @return value
	 */
	public static int getContextAsInt(Properties ctx, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, context);
		if (s.length() == 0)
			s = getContext(ctx, 0, context, false);		//	search 0 and defaults
		if (s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			log.log(Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context for WindowNo and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(Properties ctx, int WindowNo, String context)
	{
		String s = getContext(ctx, WindowNo, context, false);
		if (s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			log.log(Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context for WindowNo and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, do not try global context if context key not found with WindowNo
	 *  @return value or 0
	 */
	public static int getContextAsInt(Properties ctx, int WindowNo, String context, boolean onlyWindow)
	{
		String s = getContext(ctx, WindowNo, context, onlyWindow);
		if (s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			log.log(Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Get Context for WindowNo and TabNo and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt (Properties ctx, int WindowNo, int TabNo, String context)
	{
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (Util.isEmpty(s))
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			log.log(Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt

	/**
	 *	Is AutoCommit
	 *  @param ctx context
	 *  @return true if auto commit
	 */
	public static boolean isAutoCommit (Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, "AutoCommit");
		if (s != null && s.equals("Y"))
			return true;
		return false;
	}	//	isAutoCommit

	/**
	 *	Is Window AutoCommit (if not set, use default)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if auto commit
	 */
	public static boolean isAutoCommit (Properties ctx, int WindowNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, WindowNo, "AutoCommit", false);
		if (s != null)
		{
			if (s.equals("Y"))
				return true;
			else
				return false;
		}
		return isAutoCommit(ctx);
	}	//	isAutoCommit

	/**
	 * Is Show Technical Information 
	 * @param ctx context
	 * @return true if IsShowTechnicalInfOnHelp on User Preference
	 */
	public static boolean IsShowTechnicalInfOnHelp(Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(Env.getCtx(), "P|IsShowTechnicalInfOnHelp");
		if (s != null)
		{
			if (s.equals("Y"))
				return true;
		}
		return false;
	}	//	IsShowTechnicalInfOnHelp

	/**
	 *	Is Auto New Record
	 *  @param ctx context
	 *  @return true if auto new
	 */
	public static boolean isAutoNew (Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, "AutoNew");
		if (s != null && s.equals("Y"))
			return true;
		return false;
	}	//	isAutoNew

	/**
	 *	Is Window Auto New Record (if not set, use default)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if auto new record
	 */
	public static boolean isAutoNew (Properties ctx, int WindowNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, WindowNo, "AutoNew", false);
		if (s != null)
		{
			if (s.equals("Y"))
				return true;
			else
				return false;
		}
		return isAutoNew(ctx);
	}	//	isAutoNew

	/**
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Properties ctx)
	{
		String s = getContext(ctx, "IsSOTrx");
		if (s != null && s.equals("N"))
			return false;
		return true;
	}	//	isSOTrx

	/**
	 *	Is Sales Order Trx for WindowNo
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Properties ctx, int WindowNo)
	{
		String s = getContext(ctx, WindowNo, "IsSOTrx", true);
		if (s != null && s.equals("N"))
			return false;
		return true;
	}	//	isSOTrx

	/**
	 *	Get Context and convert it to Timestamp.<br/>
	 *	If error return today's date.
	 *  @param ctx context
	 *  @param context context key
	 *  @return Timestamp
	 */
	public static Timestamp getContextAsDate(Properties ctx, String context)
	{
		return getContextAsDate(ctx, 0, context);
	}	//	getContextAsDate

	/**
	 *	Get Context for WindowNo and convert it to Timestamp.<br/>
	 *	If error return today's date.
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param context context key
	 *  @return Timestamp
	 */
	public static Timestamp getContextAsDate(Properties ctx, int WindowNo, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String s = getContext(ctx, WindowNo, context, false);
		//	JDBC Format YYYY-MM-DD	example 2000-09-11 00:00:00.0
		if (Util.isEmpty(s))
			return new Timestamp(System.currentTimeMillis());

		Date date = null;
		try {
			date = DisplayType.getTimestampFormat_Default().parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		Timestamp timeStampDate = new Timestamp(date.getTime());
		
		return timeStampDate;
	}	//	getContextAsDate

	/**
	 * 	Get Login AD_Client_ID
	 *	@param ctx context
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID (Properties ctx)
	{
		return Env.getContextAsInt(ctx, AD_CLIENT_ID);
	}	//	getAD_Client_ID

	/**
	 * 	Get Login AD_Org_ID
	 *	@param ctx context
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID (Properties ctx)
	{
		return Env.getContextAsInt(ctx, AD_ORG_ID);
	}	//	getAD_Org_ID

	/**
	 * 	Get Login AD_User_ID
	 *	@param ctx context
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID (Properties ctx)
	{
		return Env.getContextAsInt(ctx, AD_USER_ID);
	}	//	getAD_User_ID

	/**
	 * 	Get Login AD_Role_ID
	 *	@param ctx context
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID (Properties ctx)
	{
		return Env.getContextAsInt(ctx, AD_ROLE_ID);
	}	//	getAD_Role_ID

	/**
	 *	Get Preference.
	 *  <pre>
	 *		0)	Current Setting
	 *		1) 	Window Preference
	 *		2) 	Global Preference
	 *		3)	Login settings
	 *		4)	Accounting settings
	 *  </pre>
	 *  @param  ctx context
	 *	@param	AD_Window_ID window no
	 *	@param	context		Entity to search
	 *	@param	system		System level preferences (vs. user defined)
	 *  @return preference value
	 */
	public static String getPreference (Properties ctx, int AD_Window_ID, String context, boolean system)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String retValue = null;
		//
		if (!system)	//	User Preferences
		{
			retValue = ctx.getProperty("P"+AD_Window_ID+"|"+context);//	Window Pref
			if (retValue == null)
				retValue = ctx.getProperty("P|"+context);  			//	Global Pref
		}
		else			//	System Preferences
		{
			retValue = ctx.getProperty("#"+context);   				//	Login setting
			if (retValue == null)
				retValue = ctx.getProperty("$"+context);   			//	Accounting setting
			if (retValue == null)
				retValue = ctx.getProperty("+"+context);   			//	Injected Role Variable
		}
		//
		return (retValue == null ? "" : retValue);
	}	//	getPreference

	/**
	 * Get preference of process from environment context
	 * @param ctx
	 * @param AD_Window_ID
	 * @param AD_InfoWindow
	 * @param AD_Process_ID_Of_Panel
	 * @param context
	 * @return preference value
	 */
	public static String getPreference (Properties ctx, int AD_Window_ID, int AD_InfoWindow, int AD_Process_ID_Of_Panel, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String retValue = null;
		
		retValue = ctx.getProperty("P"+AD_Window_ID+"|"+ AD_InfoWindow + "|" + AD_Process_ID_Of_Panel + "|" + context);

		return (retValue == null ? "" : retValue);
	}	//	getPreference
	
	/**
	 * Get preference of info window from environment context
	 * @param ctx
	 * @param AD_Window_ID
	 * @param AD_InfoWindow
	 * @param context
	 * @return preference value
	 */
	public static String getPreference (Properties ctx, int AD_Window_ID, int AD_InfoWindow, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		String retValue = null;
		
		retValue = ctx.getProperty("P"+AD_Window_ID+"|"+ AD_InfoWindow + "|" + context);

		return (retValue == null ? "" : retValue);
	}	//	getPreference
	
	/**
	 *  Is login language Base Language
	 *  @param ctx context
	 * 	@param tableName ignore
	 * 	@return true if language value in ctx is base language
	 */
	public static boolean isBaseLanguage (Properties ctx, String tableName)
	{
		return Language.isBaseLanguage (getAD_Language(ctx));
	}	//	isBaseLanguage

	/**
	 *	Is AD_Language a Base Language
	 * 	@param AD_Language language
	 * 	@param tableName ignore
	 * 	@return true if AD_Language is a base language
	 */
	public static boolean isBaseLanguage (String AD_Language, String tableName)
	{
		return Language.isBaseLanguage (AD_Language);
	}	//	isBaseLanguage

	/**
	 *	Is language a Base Language
	 * 	@param language language
	 * 	@param tableName ignore
	 * 	@return true if language is a base language
	 */
	public static boolean isBaseLanguage (Language language, String tableName)
	{
		return language.isBaseLanguage();
	}	//	isBaseLanguage

	/**
	 * 	Is Table in Base Translation (AD)
	 *	@param tableName table
	 *	@return true if table is in base trl
	 */
	public static boolean isBaseTranslation (String tableName)
	{
		if (tableName.startsWith("AD")
			|| tableName.equals("C_Country_Trl") )
			return true;
		return false;
	}	//	isBaseTranslation

	/**
	 * 	Do we have Multi-Lingual Documents.
	 *  Set in DB.loadOrgs.
	 * 	@param ctx context
	 * 	@return true if tenant is using multi lingual documents
	 */
	public static boolean isMultiLingualDocument (Properties ctx)
	{
		return MClient.get(ctx).isMultiLingualDocument();
	}	//	isMultiLingualDocument

	/**
	 *  Get AD_Language value in context.<br/>
	 *  Fall back to base language if there's no AD_Language value in context.
	 *  @param ctx context
	 *	@return AD_Language eg. en_US
	 */
	public static String getAD_Language (Properties ctx)
	{
		if (ctx != null)
		{
			String lang = getContext(ctx, LANGUAGE);
			if (!Util.isEmpty(lang))
				return lang;
		}
		return Language.getBaseAD_Language();
	}	//	getAD_Language

	/**
	 *  Get Language from context.<br/>
	 *  Fall back to base language if there's no language value in context.
	 *  @param ctx context
	 *	@return Language
	 */
	public static Language getLanguage (Properties ctx)
	{
		if (ctx != null)
		{
			String lang = getContext(ctx, LANGUAGE);
			if (!Util.isEmpty(lang))
				return Language.getLanguage(lang);
		}
		return Language.getBaseLanguage();
	}	//	getLanguage

	/**
	 *  Get Login Language
	 *  @param ctx context
	 *	@return Login Language
	 */
	public static Language getLoginLanguage (Properties ctx)
	{
		return Language.getLoginLanguage();
	}	//	getLanguage

	/**
	 * Get language from locale value in context
	 * @param ctx context
	 * @return Language
	 */
	public static Language getLocaleLanguage(Properties ctx) {
		Locale locale = getLocale(ctx);
		Language language = Env.getLanguage(ctx);
		if (!language.getLocale().equals(locale)) {
			Language tmp = Language.getLanguage(locale.toString());
			String adLanguage = language.getAD_Language();
			language = new Language(tmp.getName(), adLanguage, tmp.getLocale(), tmp.isDecimalPoint(),
	    			tmp.getDateFormat().toPattern(), tmp.getMediaSize());
		}
		return language;
	}
	
	/**
	 * Get locale value in context
	 * @param ctx context
	 * @return Locale
	 */
	public static Locale getLocale(Properties ctx) {
		String value = Env.getContext(ctx, Env.LOCALE);
        Locale locale = null;
        if (value != null && value.length() > 0)
        {
	        String[] components = value.split("\\_");
	        String language = components.length > 0 ? components[0] : "";
	        String country = components.length > 1 ? components[1] : "";
	        locale = new Locale(language, country);
        }
        else
        {
        	locale = Env.getLanguage(ctx).getLocale();
        }

        return locale;
	}

	/**
	 * Get list of language from AD_Message_Trl.
	 * @return list of supported language
	 */
	public static ArrayList<String> getSupportedLanguages()
	{
		ArrayList<String> AD_Languages = new ArrayList<String>();
		String sql = "SELECT DISTINCT AD_Language FROM AD_Message_Trl";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String AD_Language = rs.getString(1);
				// called to add the language to supported in case it's not added
				Language.getLanguage(AD_Language);
				AD_Languages.add(AD_Language);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return AD_Languages;
	}

	/**
	 * Get list of active login languages  
	 * @return list of active login languages
	 */
	public static ArrayList<String> getLoginLanguages()
	{
		ArrayList<String> AD_Languages = new ArrayList<String>();
		String sql = "SELECT AD_Language FROM AD_Language WHERE IsActive='Y' AND IsLoginLocale = 'Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String AD_Language = rs.getString(1);
				// called to add the language to supported in case it's not added
				Language.getLanguage(AD_Language);
				AD_Languages.add(AD_Language);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		
		return AD_Languages;
	}
	
	/**
	 *  Verify Language.<br/>
	 *  Check that language is supported by the system.
	 *  @param ctx might be updated with new AD_Language
	 *  @param language language
	 */
	public static void verifyLanguage (Properties ctx, Language language)
	{
		if (language.isBaseLanguage())
			return;

		boolean isSystemLanguage = false;
		ArrayList<String> AD_Languages = new ArrayList<String>();
		AD_Languages.add(Language.getBaseAD_Language());
		String sql = "SELECT DISTINCT AD_Language FROM AD_Message_Trl";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String AD_Language = rs.getString(1);
				if (AD_Language.equals(language.getAD_Language()))
				{
					isSystemLanguage = true;
					break;
				}
				AD_Languages.add(AD_Language);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//	Found it
		if (isSystemLanguage)
			return;
		//	No Language - set to System
		if (AD_Languages.size() == 0)
		{
			log.warning ("NO System Language - Set to Base " + Language.getBaseAD_Language());
			language.setAD_Language(Language.getBaseAD_Language());
			return;
		}

		for (int i = 0; i < AD_Languages.size(); i++)
		{
			String AD_Language = (String)AD_Languages.get(i);	//	en_US
			String lang = AD_Language.substring(0, 2);			//	en
			//
			String langCompare = language.getAD_Language().substring(0, 2);
			if (lang.equals(langCompare))
			{
				if (log.isLoggable(Level.INFO)) log.info("Found similar Language " + AD_Language);
				language.setAD_Language(AD_Language);
				return;
			}
		}

		//	We found same language

		log.warning ("Not System Language=" + language
			+ " - Set to Base Language " + Language.getBaseAD_Language());
		language.setAD_Language(Language.getBaseAD_Language());
	}   //  verifyLanguage

	/**
	 *	Get Context as String array with format: key == value
	 *  @param ctx context
	 *  @return context string array
	 */
	public static String[] getEntireContext(Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		Iterator<?> keyIterator = ctx.keySet().iterator();
		String[] sList = new String[ctx.size()];
		int i = 0;
		while (keyIterator.hasNext())
		{
			Object key = keyIterator.next();
			sList[i++] = key.toString() + " == " + ctx.get(key).toString();
		}

		return sList;
	}	//	getEntireContext

	/**
	 *	Get Header info (documentno, value, name, user name, tenant name and organization name)
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @return Header String
	 */
	public static String getHeader(Properties ctx, int WindowNo)
	{
		StringBuilder sb = new StringBuilder();
		if (WindowNo > 0){
			sb.append(getContext(ctx, WindowNo, "_WinInfo_WindowName", false)).append("  ");
			final String documentNo = getContext(ctx, WindowNo, "DocumentNo", false);
			final String value = getContext(ctx, WindowNo, "Value", false);
			final String name = getContext(ctx, WindowNo, "Name", false);
			if(!"".equals(documentNo)) {
				sb.append(documentNo).append("  ");
			}
			if(!"".equals(value)) {
				sb.append(value).append("  ");
			}
			if(!"".equals(name)) {
				sb.append(name).append("  ");
			}
		}
		sb.append(getContext(ctx, Env.AD_USER_NAME)).append(Evaluator.VARIABLE_START_END_MARKER)
			.append(getContext(ctx, Env.AD_CLIENT_NAME)).append(".")
			.append(getContext(ctx, Env.AD_ORG_NAME))
			.append(" [").append(CConnection.get().toString()).append("]");
		return sb.toString();
	}	//	getHeader

	/**
	 *	Clean up context for WindowNo (i.e. delete it)
	 *  @param ctx context
	 *  @param WindowNo window
	 */
	public static void clearWinContext(Properties ctx, int WindowNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		//
		Object[] keys = ctx.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			String tag = keys[i].toString();
			if (tag.startsWith(WindowNo+"|"))
				ctx.remove(keys[i]);
		}
		//
		IEnvEventListener[] listeners = eventListeners.toArray(new IEnvEventListener[0]);
		for(IEnvEventListener listener : listeners)
		{
			listener.onClearWindowContext(WindowNo);
		}
	}	//	clearWinContext

	/**
	 * Clean up context for WindowNo and TabNo (i.e. delete it).<br/>
	 * Please note that this method is not clearing the tab info context (i.e. _TabInfo).
	 * @param ctx context
	 * @param WindowNo window
	 * @param TabNo tab
	 */
	public static void clearTabContext(Properties ctx, int WindowNo, int TabNo)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		//
		Object[] keys = ctx.keySet().toArray();
		for (int i = 0; i < keys.length; i++)
		{
			String tag = keys[i].toString();
			if (tag.startsWith(WindowNo+"|"+TabNo+"|")
					&& !tag.startsWith(WindowNo+"|"+TabNo+"|_TabInfo"))
			{
				ctx.remove(keys[i]);
			}
		}
	}
	
	/**
	 *	Clean up all context (i.e. delete it)
	 *  @param ctx context
	 */
	public static void clearContext(Properties ctx)
	{
		if (ctx == null)
			throw new IllegalArgumentException ("Require Context");
		ctx.clear();
	}	//	clearContext

	/**
	 *	Parse expression and replaces global or Window context @tag@ with actual value.<br/>
	 *
	 *  @param ctx context
	 *	@param WindowNo	Number of Window
	 *	@param value Expression to be parsed
	 *  @param onlyWindow if true, do not use global context value
	 * 	@param ignoreUnparsable 
	 *  If true, just skip context variable that's not resolvable. 
	 *  If false, return "" if there are context variable that's not resolvable.  
	 *	@return parsed expression
	 */
	public static String parseContext (Properties ctx, int WindowNo, String value,
		boolean onlyWindow, boolean ignoreUnparsable)
	{
		return parseContext(ctx, WindowNo, value, onlyWindow, ignoreUnparsable, false);
	}

	/**
	 *	Parse expression and replaces global or Window context @tag@ with actual value.<br/>
	 *
	 *  @param ctx context
	 *	@param WindowNo	Number of Window
	 *	@param value Expression to be parsed
	 *  @param onlyWindow if true, do not use global context value
	 * 	@param ignoreUnparsable 
	 *  If true, just skip context variable that's not resolvable. 
	 *  If false, return "" if there are context variable that's not resolvable.
	 *  @param keepEscapeSequence if true, keeps the escape sequence '@@' in the parsed string. Otherwise, the '@@' escape sequence is used to keep '@' character in the string.  
	 *	@return parsed expression
	 */
	public static String parseContext (Properties ctx, int WindowNo, String value,
		boolean onlyWindow, boolean ignoreUnparsable, boolean keepEscapeSequence)
	{
		if (value == null || value.length() == 0)
			return "";

		String token;
		String inStr = new String(value);
		StringBuilder outStr = new StringBuilder();

		DefaultEvaluatee evaluatee = new DefaultEvaluatee(null, WindowNo, 0, onlyWindow);
		int i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);						// next @
			if (j < 0)
			{
				if (log.isLoggable(Level.INFO)) log.log(Level.INFO, "No second tag: " + inStr);
				//not context variable, add back @ and break
				outStr.append(Evaluator.VARIABLE_START_END_MARKER);
				break;
			}

			if (j == 0)
			{
				if (keepEscapeSequence) {
					outStr.append("@@");
				} else {
					outStr.append("@");
				}
				inStr = inStr.substring(1);
				i = inStr.indexOf('@');
				continue;
			}

			token = inStr.substring(0, j);

			String ctxInfo = evaluatee.get_ValueAsString(ctx, token);
			if (ctxInfo.length() == 0)
			{
				if (log.isLoggable(Level.CONFIG)) log.config("No Context Win=" + WindowNo + " for: " + token);
				if (!ignoreUnparsable)
					return "";						//	token not found
			}
			else
				outStr.append(ctxInfo);				// replace context with Context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}	//	parseContext
	
	/**
	 *	Parse expression and replaces global, window or tab context @tag@ with actual value.
	 *
	 *  @param ctx context
	 *	@param WindowNo	Number of Window
	 *	@param tabNo	Number of Tab
	 *	@param value Expression to be parsed
	 *  @param onlyTab if true, only context for tabNo are used
	 * 	@param ignoreUnparsable 
	 *  If true, just skip context variable that's not resolvable. 
	 *  If false, return "" if there are context variable that's not resolvable.
	 *	@return parsed expression
	 */
	public static String parseContext (Properties ctx, int WindowNo, int tabNo, String value,
		boolean onlyTab, boolean ignoreUnparsable)
	{
		return parseContext(ctx, WindowNo, tabNo, value, onlyTab, ignoreUnparsable, false);
	}

	/**
	 *	Parse expression and replaces global, window or tab context @tag@ with actual value.
	 *
	 *  @param ctx context
	 *	@param WindowNo	Number of Window
	 *	@param tabNo	Number of Tab
	 *	@param value Expression to be parsed
	 *  @param onlyTab if true, only context for tabNo are used
	 * 	@param ignoreUnparsable 
	 *  If true, just skip context variable that's not resolvable. 
	 *  If false, return "" if there are context variable that's not resolvable.
	 *  @param keepEscapeSequence if true, keeps the escape sequence '@@' in the parsed string. Otherwise, the '@@' escape sequence is used to keep '@' character in the string.
	 *	@return parsed expression
	 */
	public static String parseContext (Properties ctx, int WindowNo, int tabNo, String value,
		boolean onlyTab, boolean ignoreUnparsable, boolean keepEscapeSequence)
	{
		if (value == null || value.length() == 0)
			return "";

		String token;
		String inStr = new String(value);
		StringBuilder outStr = new StringBuilder();

		DefaultEvaluatee evaluatee = new DefaultEvaluatee(null, WindowNo, tabNo, onlyTab, onlyTab);
		int i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);						// next @
			if (j < 0)
			{
				if (log.isLoggable(Level.INFO)) log.log(Level.INFO, "No second tag: " + inStr);
				//not context variable, add back @ and break
				outStr.append(Evaluator.VARIABLE_START_END_MARKER);
				break;
			}

			if (j == 0)
			{
				if (keepEscapeSequence) {
					outStr.append(Evaluator.VARIABLE_START_END_MARKER).append(Evaluator.VARIABLE_START_END_MARKER);
				} else {
					outStr.append(Evaluator.VARIABLE_START_END_MARKER);
				}
				inStr = inStr.substring(1);
				i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
				continue;
			}

			token = inStr.substring(0, j);

			String ctxInfo = evaluatee.get_ValueAsString(ctx, token);			
			if (Util.isEmpty(ctxInfo))
			{
				if (log.isLoggable(Level.CONFIG)) log.config("No Context Win=" + WindowNo + " for: " + token);
				if (!ignoreUnparsable)
					return "";						//	token not found
			}
			else
				outStr.append(ctxInfo);				// replace context with Context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}	//	parseContext

	/**
	 *	Parse expression and replaces global or Window context @tag@ with actual value.
	 *
	 *  @param ctx context
	 *	@param	WindowNo	Number of Window
	 *	@param	value		Expression to be parsed
	 *  @param  onlyWindow  if true, no defaults are used
	 *  @return parsed expression or "" if not successful
	 */
	public static String parseContext (Properties ctx, int WindowNo, String value,
		boolean onlyWindow)
	{
		return parseContext(ctx, WindowNo, value, onlyWindow, false);
	}	//	parseContext
	
	/**
	 *	Parse expression and replaces global, window or tab context @tag@ with actual value.
	 *
	 *  @param ctx context
	 *	@param	WindowNo	Number of Window
	 *	@param	tabNo   	Number of Tab
	 *	@param	value		Expression to be parsed
	 *  @param  onlyTab  	if true, only context for tabNo are used
	 *  @return parsed String or "" if not successful
	 */
	public static String parseContext (Properties ctx, int WindowNo, int tabNo, String value,
		boolean onlyTab)
	{
		return parseContext(ctx, WindowNo, tabNo, value, onlyTab, false);
	}	//	parseContext

	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value.
	 * @param expression
	 * @param po
	 * @param trxName
	 * @param keepUnparseable
	 * @return String
	 */
	public static String parseVariable(String expression, PO po, String trxName, boolean keepUnparseable) {
		return parseVariable(expression, po, trxName, false, false, keepUnparseable);
	}
	
	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value.
	 * @param expression
	 * @param po
	 * @param useColumnDateFormat
	 * @param useMsgForBoolean
	 * @param trxName
	 * @param keepUnparseable true to keep original context variable tag that can't be resolved
	 * @return Parsed expression
	 */
	public static String parseVariable(String expression, PO po, String trxName, boolean useColumnDateFormat, 
			boolean useMsgForBoolean, boolean keepUnparseable) {
		return parseVariable(expression, po, trxName, useColumnDateFormat, useMsgForBoolean, keepUnparseable, false);
	}

	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value.
	 * @param expression
	 * @param po
	 * @param useColumnDateFormat
	 * @param useMsgForBoolean
	 * @param trxName
	 * @param keepUnparseable true to keep original context variable tag that can't be resolved
	 * @param keepEscapeSequence if true, keeps the escape sequence '@@' in the parsed string. Otherwise, the '@@' escape sequence is used to keep '@' character in the string.
	 * @return Parsed expression
	 */
	public static String parseVariable(String expression, PO po, String trxName, boolean useColumnDateFormat, 
			boolean useMsgForBoolean, boolean keepUnparseable, boolean keepEscapeSequence) {
		DefaultEvaluatee evaluatee = new DefaultEvaluatee(po);
		evaluatee.setUseColumnDateFormat(useColumnDateFormat);
		evaluatee.setUseMsgForBoolean(useMsgForBoolean);
		evaluatee.setTrxName(trxName);
		return parseVariable(expression, evaluatee, keepUnparseable, keepEscapeSequence);
	}
	
	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value.
	 * @param expression
	 * @param evaluatee
	 * @param keepUnparseable true to keep original context variable tag that can't be resolved
	 * @param keepEscapeSequence if true, keeps the escape sequence '@@' in the parsed string. Otherwise, the '@@' escape sequence is used to keep '@' character in the string.
	 * @return Parsed expression
	 */
	public static String parseVariable(String expression, DefaultEvaluatee evaluatee, boolean keepUnparseable, boolean keepEscapeSequence) {
		if (expression == null || expression.length() == 0)
			return "";

		String token;
		String inStr = new String(expression);
		StringBuilder outStr = new StringBuilder();
		
		int i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);						// next @
			if (j < 0)
			{
				log.log(Level.SEVERE, "No second tag: " + inStr);
				return "";						//	no second tag
			}

			if (j == 0)
			{
				if (keepEscapeSequence) {
					outStr.append(Evaluator.VARIABLE_START_END_MARKER).append(Evaluator.VARIABLE_START_END_MARKER);
				} else {
					outStr.append(Evaluator.VARIABLE_START_END_MARKER);
				}
				inStr = inStr.substring(1);
				i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
				continue;
			}

			token = inStr.substring(0, j);

			Properties ctx = evaluatee.getPO() != null ? evaluatee.getPO().getCtx() : Env.getCtx();
			String value = evaluatee.get_ValueAsString(ctx, token);
			if (Util.isEmpty(value)) {
				if (keepUnparseable) {
					outStr.append(Evaluator.VARIABLE_START_END_MARKER)
						.append(token)
						.append(Evaluator.VARIABLE_START_END_MARKER);
				}
			} else {
				outStr.append(value);
			}
			
			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf(Evaluator.VARIABLE_START_END_MARKER);
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}

	/**
	 *	Clean up context for WindowNo (i.e. delete it)
	 *  @param WindowNo window
	 */
	public static void clearWinContext(int WindowNo)
	{
		clearWinContext (getCtx(), WindowNo);
	}	//	clearWinContext

	/**
	 *	Clean up all context (i.e. delete it)
	 */
	public static void clearContext()
	{
		getCtx().clear();
	}	//	clearContext
		
	/**
	 *  Get ImageIcon.
	 *
	 *  @param fileNameInImageDir file name in images folder (e.g. Bean16.gif)
	 *  @return image
	 */
	public static ImageIcon getImageIcon (String fileNameInImageDir)
	{
		IResourceFinder rf = Core.getResourceFinder();
		URL url =  rf.getResource("images/" + fileNameInImageDir);
		if (url == null)
		{
			if (log.isLoggable(Level.INFO)) log.log(Level.INFO, "Not found: " +  fileNameInImageDir);
			return null;
		}
		return new ImageIcon(url);
	}   //  getImageIcon

	/**
	 *  Get ImageIcon. This method different from getImageIcon
	 *  where the fileName parameter is without extension. The
	 *  method will first try .gif and then .png if .gif does not
	 *  exists.
	 *
	 *  @param fileName file name in images folder without the extension(e.g. Bean16)
	 *  @return image
	 */
	public static ImageIcon getImageIcon2 (String fileName)
	{
		IResourceFinder rf = Core.getResourceFinder();
		URL url =  rf.getResource("images/" + fileName+".gif");
		if (url == null)
			url = rf.getResource("images/" + fileName+".png");
		if (url == null)
		{
			if (log.isLoggable(Level.INFO)) log.log(Level.INFO, "GIF/PNG Not found: " + fileName);
			return null;
		}
		return new ImageIcon(url);
	}   //  getImageIcon2

	/**
	 *  Show url in Browser
	 *  @param url url
	 */
	public static void startBrowser (String url)
	{
		if (log.isLoggable(Level.INFO)) log.info(url);
		getContextProvider().showURL(url);
	}   //  startBrowser

	/**
	 * 	Do we run on Apple
	 *	@return true if Mac
	 */
	public static boolean isMac()
   	{
   		String osName = System.getProperty ("os.name");
   		osName = osName.toLowerCase();
   		return osName.indexOf ("mac") != -1;
   	}	//	isMac

   	/**
   	 * 	Do we run on Windows
   	 *	@return true if windows
   	 */
   	public static boolean isWindows()
   	{
   		String osName = System.getProperty ("os.name");
   		osName = osName.toLowerCase();
   		return osName.indexOf ("windows") != -1;
   	}	//	isWindows

	/**
	 * 	Sleep
	 *	@param sec seconds
	 */
	public static void sleep (int sec)
	{
		if (log.isLoggable(Level.INFO)) log.info("Start - Seconds=" + sec);
		try
		{
			Thread.sleep(sec*1000);
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, "", e);
		}
		if (log.isLoggable(Level.INFO)) log.info("End");
	}	//	sleep

	/**
	 * Prepare the context for calling remote server (for e.g, ejb),
	 * only default and global variables are pass over.
	 * It is too expensive and also can have serialization issue if
	 * every remote call to server is passing the whole client context.
	 * @param ctx
	 * @return Properties
	 */
	@Deprecated(forRemoval = true, since = "11")
	public static Properties getRemoteCallCtx(Properties ctx)
	{
		Properties p = new Properties();
		Set<Object> keys = ctx.keySet();
		for (Object key : keys)
		{
			if(!(key instanceof String))
				continue;

			Object value = ctx.get(key);
			if (!(value instanceof String))
				continue;

			p.put(key, value);
		}

		return p;
	}

	/**
	 *  Get AD_Window value object model
	 *
	 *  @param WindowNo  Window No
	 *  @param AD_Window_ID window
	 *  @param AD_Menu_ID menu
	 *  @return Model Window Value Object
	 */
	public static GridWindowVO getMWindowVO (int WindowNo, int AD_Window_ID, int AD_Menu_ID)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("Window=" + WindowNo + ", AD_Window_ID=" + AD_Window_ID);
		GridWindowVO mWindowVO = GridWindowVO.get(AD_Window_ID, WindowNo, AD_Menu_ID);
		if (mWindowVO == null)
			return null;

		return mWindowVO;
	}   //  getWindow

	/**
	 * Get IProcessUI instance
	 * @param ctx
	 * @return IProcessUI instance or null
	 */
	public static IProcessUI getProcessUI(Properties ctx)
	{
		return (IProcessUI) ctx.get(SvrProcess.PROCESS_UI_CTX_KEY);
	}
	
	/**
	 * Get process info instance from context
	 * @param ctx context
	 * @return process info instance or null
	 */
	public static ProcessInfo getProcessInfo(Properties ctx)
	{
		return (ProcessInfo) ctx.get(SvrProcess.PROCESS_INFO_CTX_KEY);
	}
	
	/**
	 * Get footer trademark text for report
	 * @return trademark text for standard report footer
	 */
	public static String getStandardReportFooterTrademarkText() {
		String s = MSysConfig.getValue(MSysConfig.STANDARD_REPORT_FOOTER_TRADEMARK_TEXT, Env.getAD_Client_ID(Env.getCtx()));
		if (Util.isEmpty(s, true))
			s = Env.getContext(Env.getCtx(), STANDARD_REPORT_FOOTER_TRADEMARK_TEXT);
		if (Util.isEmpty(s))
			s = Adempiere.ADEMPIERE_R;
		return s;
	}
	
	/**
	 * Get zoom AD_Window_ID
	 * @param query
	 * @return zoom AD_Window_ID
	 */
	public static int getZoomWindowID(MQuery query)
	{
		int AD_Window_ID = MZoomCondition.findZoomWindow(query);
		if (AD_Window_ID <= 0)
		{
			String TableName = query.getTableName();
			int PO_Window_ID = 0;
			String sql = "SELECT AD_Window_ID, PO_Window_ID FROM AD_Table WHERE TableName=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, TableName);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					AD_Window_ID = rs.getInt(1);
					PO_Window_ID = rs.getInt(2);
				}
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, sql, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			//  Nothing to Zoom to
			if (AD_Window_ID == 0)
				return AD_Window_ID;
	
			//	PO Zoom ?
			boolean isSOTrx = true;
			if (PO_Window_ID != 0)
			{
				isSOTrx = DB.isSOTrx(TableName, query.getWhereClause(false));
				if (!isSOTrx)
					AD_Window_ID = PO_Window_ID;
			}

			if (log.isLoggable(Level.CONFIG)) log.config(query + " (IsSOTrx=" + isSOTrx + ")");
		}
		return AD_Window_ID;
	}
	
	/**
	 * Get zoom AD_Window_ID
	 * @param AD_Table_ID
	 * @param Record_UU
	 * @return zoom AD_Window_ID
	 */
	public static int getZoomWindowUU(int AD_Table_ID, String Record_UU) {
		return getZoomWindowUU(AD_Table_ID, Record_UU, 0);
	}

	/**
	 * Get zoom AD_Window_ID
	 * @param AD_Table_ID
	 * @param Record_UU
	 * @param windowNo
	 * @return zoom AD_Window_ID
	 */
	public static int getZoomWindowUU(int AD_Table_ID, String Record_UU, int windowNo)
	{
		return getZoomWindowIDOrUU(AD_Table_ID, -1, Record_UU, windowNo);
	}

	/**
	 * Get zoom AD_Window_ID
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @return zoom AD_Window_ID
	 */
	public static int getZoomWindowID(int AD_Table_ID, int Record_ID)
	{
		return getZoomWindowID(AD_Table_ID, Record_ID, 0);
	}

	/**
	 * Get zoom AD_Window_ID
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @param windowNo
	 * @return zoom AD_Window_ID
	 */
	public static int getZoomWindowID(int AD_Table_ID, int Record_ID, int windowNo)
	{
		return getZoomWindowIDOrUU(AD_Table_ID, Record_ID, null, windowNo);
	}

	/**
	 * Get zoom AD_Window_ID
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @param Record_UU
	 * @param windowNo
	 * @return zoom AD_Window_ID
	 */
	private static int getZoomWindowIDOrUU(int AD_Table_ID, int Record_ID, String Record_UU, int windowNo)
	{
		int AD_Window_ID = MZoomCondition.findZoomWindowByTableIdOrUU(AD_Table_ID, Record_ID, Record_UU, windowNo);
		if (AD_Window_ID <= 0)
		{
			MTable table = MTable.get(Env.getCtx(), AD_Table_ID);
			AD_Window_ID = table.getAD_Window_ID();
			//  Nothing to Zoom to
			if (AD_Window_ID == 0) 
			{
				AD_Window_ID = table.getWindowIDFromMenu();
				return AD_Window_ID > 0 ? AD_Window_ID : 0;
			}
			
			//	PO Zoom ?
			boolean isSOTrx = true;
			if (table.getPO_Window_ID() != 0 && ((Record_ID > 0 || Record_UU != null)))
			{
				String whereClause;
				if (Record_UU != null)
					whereClause = PO.getUUIDColumnName(table.getTableName()) + "=" + DB.TO_STRING(Record_UU);
				else
					whereClause = table.getTableName() + "_ID=" + Record_ID;
				isSOTrx = DB.isSOTrx(table.getTableName(), whereClause, windowNo);
				if (!isSOTrx)
					AD_Window_ID = table.getPO_Window_ID();
			}

			if (log.isLoggable(Level.CONFIG)) log.config(table.getTableName() + " - Record_ID=" + Record_ID + " - Record_UU=" + Record_UU + " (IsSOTrx=" + isSOTrx + ")");
		}
		return AD_Window_ID;
	}
	
	/**	Big Decimal 0	 */
	static final public BigDecimal ZERO = BigDecimal.valueOf(0.0);
	/**	Big Decimal 1	 */
	static final public BigDecimal ONE = BigDecimal.valueOf(1.0);
	/**	Big Decimal 100	 */
	static final public BigDecimal ONEHUNDRED = BigDecimal.valueOf(100.0);

	/**	New Line 		 */
	public static final String	NL = System.getProperty("line.separator");
	/* Prefix for predefined context variables coming from menu, window or role definition */
	public static final String PREFIX_PREDEFINED_VARIABLE = "+";

	/**
	 *  Static initializer
	 */
	static
	{
		//  Set English as default Language
		getCtx().put(LANGUAGE, Language.getBaseAD_Language());
	}   //  static

	/**
	 * <pre>
	 * Add in context predefined variables with prefix +, coming from menu, window or role definition.
	 * Predefined variables must come separated by new lines in one of the formats:
	 *   VAR=VALUE
	 *   VAR="VALUE"
	 *   VAR='VALUE'
	 *  The + prefix is not required, is added here to the defined variables.
	 * </pre>
	 * NOTE that any line that doesn't contain an equal sign (=) is ignored, can be used simply as a comment
	 * @param ctx
	 * @param windowNo window number or -1 to global level
	 * @param predefinedVariables
	 */
	public static void setPredefinedVariables(Properties ctx, int windowNo, String predefinedVariables) {
		if (predefinedVariables != null) {
			String[] lines = predefinedVariables.split("\n");
			for (String line : lines) {
				int idxEq = line.indexOf("=");
				if (idxEq > 0) {
					String var = line.substring(0, idxEq).trim();
					if (var.length() > 0) {
						String value = line.substring(idxEq+1).trim();
						if (   (value.startsWith("\"") && value.endsWith("\""))
							|| (value.startsWith("'")  && value.endsWith("'") )
							) {
							value = value.substring(1, value.length()-1);
						}
						if (windowNo >= 0)
							Env.setContext(ctx, windowNo, PREFIX_PREDEFINED_VARIABLE + var, value);
						else
							Env.setContext(ctx, PREFIX_PREDEFINED_VARIABLE + var, value);
					}
				}
			}
		}
	}

	/**
	 * Is log migration script for SQL statement
	 * @param tableName
	 * @return true if log migration script is turn on and should be used for tableName
	 */
	public static boolean isLogMigrationScript(String tableName) {
		boolean logMigrationScript = false;
		if (Ini.isClient()) {
			logMigrationScript = Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT);
		} else {
			String sysProperty = Env.getCtx().getProperty(Ini.P_LOGMIGRATIONSCRIPT, "N");
			logMigrationScript = "y".equalsIgnoreCase(sysProperty) || "true".equalsIgnoreCase(sysProperty);
		}
		
		return logMigrationScript ? !Convert.isDontLogTable(tableName) : false;
	}
	
	/**
	 * Is use centralized id from id server
	 * @return true if centralized id is turn on and should be used for tableName
	 */
	public static boolean isUseCentralizedId(String tableName)
	{
		String sysProperty = Env.getCtx().getProperty(Ini.P_ADEMPIERESYS, "N");
		boolean adempiereSys = "y".equalsIgnoreCase(sysProperty) || "true".equalsIgnoreCase(sysProperty);
		if (adempiereSys && Env.getAD_Client_ID(Env.getCtx()) > 11)
			adempiereSys = false;
		
		if (adempiereSys)
		{
			boolean b = MSysConfig.getBooleanValue(MSysConfig.DICTIONARY_ID_USE_CENTRALIZED_ID, true);
			if (b)
				return !MSequence.isExceptionCentralized(tableName);
			else
				return b;
		}
		else
		{
			boolean queryProjectServer = false;
			if (MSequence.isTableWithEntityType(tableName))
				queryProjectServer = true;
			if (!queryProjectServer && MSequence.Table_Name.equalsIgnoreCase(tableName))
				queryProjectServer = true;
			if (queryProjectServer && !MSequence.isExceptionCentralized(tableName)) {
				return MSysConfig.getBooleanValue(MSysConfig.PROJECT_ID_USE_CENTRALIZED_ID, false);
			}
		}
		return false;
	}

	/**
	 * Is read only session?  Based on user preference
	 * @return
	 */
	public static boolean isReadOnlySession() {
		return "Y".equals(Env.getContext(Env.getCtx(), "IsReadOnlySession"));
	}

	/**
	 * Verifies if a context variable name is global, this is, starting with:<br/>
	 *   #  Login<br/>
	 *   $  Accounting<br/>
	 *   +  Role Injected
	 * @param variable
	 * @return
	 */
	public static boolean isGlobalVariable(String variable) {
		return variable.startsWith("#")
			|| variable.startsWith("$")
			|| variable.startsWith("+");
	}

	/**
	 * Verifies if a context variable name is a preference, this is, starting with:<br/>
	 *   P| Preference
	 * @param variable
	 * @return
	 */
	public static boolean isPreference(String variable) {
		return variable.startsWith("P|");
	}

	/**
	 * Verifies if a context variable name is a system configuration, this is, starting with:<br/>
	 *   $sysconfig.
	 * @param variable
	 * @return
	 */
	public static boolean isSysConfig(String variable) {
		return variable.startsWith(PREFIX_SYSCONFIG_VARIABLE);
	}
}   //  Env