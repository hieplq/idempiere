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
package org.compiere.report;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MElementValue;
import org.compiere.model.MPeriod;
import org.compiere.model.MProcessPara;

import static org.compiere.model.SystemIDs.*;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Msg;

/**
 *  Statement of Account
 *
 *  @author Jorg Janke
 *  @version $Id: FinStatement.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 *  @author Low Heng Sin
 *  - Remove update balance option to resolved Feature Request [ 1557707 ] and
 *    bug [1619917]
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org 
 *			@see https://sourceforge.net/p/adempiere/feature-requests/631/
 *	@author Armen Rizal, Goodwill Consulting
 *			<li>FR [2857076] User Element 1 and 2 completion - https://sourceforge.net/p/adempiere/feature-requests/817/
 *   
 */
@org.adempiere.base.annotation.Process
public class FinStatement extends SvrProcess
{
	/** AcctSchame Parameter			*/
	private int					p_C_AcctSchema_ID = 0;
	/** Posting Type					*/
	private String				p_PostingType = "A";
	/**	Period Parameter				*/
	private int					p_C_Period_ID = 0;
	private Timestamp			p_DateAcct_From = null;
	private Timestamp			p_DateAcct_To = null;
	/**	Org Parameter					*/
	private int					p_AD_Org_ID = 0;
	/**	Account Parameter				*/
	private int					p_Account_ID = 0;
	/**	BPartner Parameter				*/
	private int					p_C_BPartner_ID = 0;
	/**	Product Parameter				*/
	private int					p_M_Product_ID = 0;
	/**	Project Parameter				*/
	private int					p_C_Project_ID = 0;
	/**	Activity Parameter				*/
	private int					p_C_Activity_ID = 0;
	/**	SalesRegion Parameter			*/
	private int					p_C_SalesRegion_ID = 0;
	/**	Campaign Parameter				*/
	private int					p_C_Campaign_ID = 0;
	/** User List 1 Parameter			*/
	private int					p_User1_ID = 0;
	/** User List 2 Parameter			*/
	private int					p_User2_ID = 0;
	/** User Element 1 Parameter		*/
	private int					p_UserElement1_ID = 0;
	/** User Element 2 Parameter		*/
	private int					p_UserElement2_ID = 0;
	/** Hierarchy						*/
	private int					p_PA_Hierarchy_ID = 0;
	/** BPartner Employee Parameter */
	private int				p_C_Employee_ID				= 0;
	/** Charge Parameter */
	private int				p_C_Charge_ID				= 0;
	/** Cost Center Parameter */
	private int				p_C_CostCenter_ID			= 0;
	/** Department Parameter */
	private int				p_C_Department_ID			= 0;
	/** Warehouse Parameter */
	private int				p_M_Warehouse_ID			= 0;
	/** Asset Parameter */
	private int				p_A_Asset_ID				= 0;
	/** Attribute Set Instance Parameter */
	private int				p_M_AttributeSetInstance_ID	= 0;
	/** Tax Parameter */
	private int				p_C_Tax_ID					= 0;

	/**	Parameter Where Clause			*/
	private StringBuffer		m_parameterWhere = new StringBuffer();
	/**	Account							*/ 
	private MElementValue 		m_acct = null;
	
	/**	Start Time						*/
	private long 				m_start = System.currentTimeMillis();

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		StringBuilder sb = new StringBuilder ("Record_ID=")
			.append(getRecord_ID());
		//	Parameter
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null && para[i].getParameter_To() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("PostingType"))
				p_PostingType = (String)para[i].getParameter(); 
			else if (name.equals("C_Period_ID"))
				p_C_Period_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DateAcct"))
			{
				p_DateAcct_From = (Timestamp)para[i].getParameter();
				p_DateAcct_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("PA_Hierarchy_ID"))
				p_PA_Hierarchy_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("Account_ID"))
				p_Account_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Project_ID"))
				p_C_Project_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Activity_ID"))
				p_C_Activity_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_SalesRegion_ID"))
				p_C_SalesRegion_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Campaign_ID"))
				p_C_Campaign_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("User1_ID"))
				p_User1_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("User2_ID"))
				p_User2_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("UserElement1_ID"))
				p_UserElement1_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("UserElement2_ID"))
				p_UserElement2_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Employee_ID"))
				p_C_Employee_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Charge_ID"))
				p_C_Charge_ID = para[i].getParameterAsInt();
			else if (name.equals("C_CostCenter_ID"))
				p_C_CostCenter_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Department_ID"))
				p_C_Department_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("A_Asset_ID"))
				p_A_Asset_ID = para[i].getParameterAsInt();
			else if (name.equals("M_AttributeSetInstance_ID"))
				p_M_AttributeSetInstance_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Tax_ID"))
				p_C_Tax_ID = para[i].getParameterAsInt();
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}
		//	Mandatory C_AcctSchema_ID, PostingType
		m_parameterWhere.append("C_AcctSchema_ID=").append(p_C_AcctSchema_ID)
			.append(" AND PostingType='").append(p_PostingType).append("'");
		//	Optional Account_ID
		if (p_Account_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Account, p_Account_ID));
		//	Optional Org
		if (p_AD_Org_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Organization, p_AD_Org_ID));
		//	Optional BPartner
		if (p_C_BPartner_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_BPartner, p_C_BPartner_ID));
		//	Optional Product
		if (p_M_Product_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Product, p_M_Product_ID));
		//	Optional Project
		if (p_C_Project_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Project, p_C_Project_ID));
		//	Optional Activity
		if (p_C_Activity_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_Activity, p_C_Activity_ID));
		//	Optional Campaign
		if (p_C_Campaign_ID != 0)
			m_parameterWhere.append(" AND C_Campaign_ID=").append(p_C_Campaign_ID);
		//	m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
		//		MAcctSchemaElement.ELEMENTTYPE_Campaign, p_C_Campaign_ID));
		//	Optional Sales Region
		if (p_C_SalesRegion_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_SalesRegion, p_C_SalesRegion_ID));
		//	Optional User1_ID
		if (p_User1_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_UserElementList1, p_User1_ID));
		//  Optional User2_ID
		if (p_User2_ID != 0)
			m_parameterWhere.append(" AND ").append(MReportTree.getWhereClause(getCtx(), 
				p_PA_Hierarchy_ID, MAcctSchemaElement.ELEMENTTYPE_UserElementList2, p_User2_ID));
		//	Optional UserElement1_ID
		if (p_UserElement1_ID != 0)
			m_parameterWhere.append(" AND UserElement1_ID=").append(p_UserElement1_ID);
		//  Optional UserElement2_ID
		if (p_UserElement2_ID != 0)
			m_parameterWhere.append(" AND UserElement2_ID=").append(p_UserElement2_ID);	
		//	Optional Employee
		if (p_C_Employee_ID != 0)
			m_parameterWhere.append(" AND C_Employee_ID = ").append(p_C_Employee_ID);
		//	Optional Charge
		if (p_C_Charge_ID != 0)
			m_parameterWhere.append(" AND C_Charge_ID = ").append(p_C_Charge_ID);
		//	Optional Cost Center
		if (p_C_CostCenter_ID != 0)
			m_parameterWhere.append(" AND C_CostCenter_ID = ").append(p_C_CostCenter_ID);
		//	Optional Department
		if (p_C_Department_ID != 0)
			m_parameterWhere.append(" AND C_Department_ID = ").append(p_C_Department_ID);
		//	Optional Warehouse
		if (p_M_Warehouse_ID != 0)
			m_parameterWhere.append(" AND M_Warehouse_ID = ").append(p_M_Warehouse_ID);
		// Optional Asset
		if (p_A_Asset_ID != 0)
			m_parameterWhere.append(" AND A_Asset_ID = ").append(p_A_Asset_ID);
		// Optional ASI
		if (p_M_AttributeSetInstance_ID != 0)
			m_parameterWhere.append(" AND M_AttributeSetInstance_ID = ").append(p_M_AttributeSetInstance_ID);
		// Optional ASI
		if (p_C_Tax_ID != 0)
			m_parameterWhere.append(" AND C_Tax_ID = ").append(p_C_Tax_ID);
		//
		setDateAcct();
		sb.append(" - DateAcct ").append(p_DateAcct_From).append("-").append(p_DateAcct_To);
		sb.append(" - Where=").append(m_parameterWhere);
		if (log.isLoggable(Level.FINE)) log.fine(sb.toString());
	}	//	prepare

	/**
	 * 	Set Start/End Date of Report - if not defined current Month
	 */
	private void setDateAcct()
	{
		//	Date defined
		if (p_DateAcct_From != null)
		{
			if (p_DateAcct_To == null)
				p_DateAcct_To = new Timestamp (System.currentTimeMillis());
			return;
		}
		//	Get Date from Period
		if (p_C_Period_ID == 0)
		{
		   GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		   cal.setTimeInMillis(System.currentTimeMillis());
		   cal.set(Calendar.HOUR_OF_DAY, 0);
		   cal.set(Calendar.MINUTE, 0);
		   cal.set(Calendar.SECOND, 0);
		   cal.set(Calendar.MILLISECOND, 0);
		   cal.set(Calendar.DAY_OF_MONTH, 1);		//	set to first of month
		   p_DateAcct_From = new Timestamp (cal.getTimeInMillis());
		   cal.add(Calendar.MONTH, 1);
		   cal.add(Calendar.DAY_OF_YEAR, -1);		//	last of month
		   p_DateAcct_To = new Timestamp (cal.getTimeInMillis());
		   return;
		}

		String sql = "SELECT StartDate, EndDate FROM C_Period WHERE C_Period_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, p_C_Period_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				p_DateAcct_From = rs.getTimestamp(1);
				p_DateAcct_To = rs.getTimestamp(2);
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}	//	setDateAcct

	/**
	 *  Insert reporting data to T_ReportStatement
	 *  @return Message to be translated
	 */
	@Override
	protected String doIt()
	{
		createBalanceLine();
		createDetailLines();

		int AD_PrintFormat_ID = PRINTFORMAT_STATEMENTOFACCOUNT;
		if (Ini.isClient())
			getProcessInfo().setTransientObject (MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));
		else
			getProcessInfo().setSerializableObject(MPrintFormat.get (getCtx(), AD_PrintFormat_ID, false));

		if (log.isLoggable(Level.FINE)) log.fine((System.currentTimeMillis() - m_start) + " ms");
		return "";
	}	//	doIt

	/**
	 * 	Create Beginning Balance Line
	 */
	private void createBalanceLine()
	{
		StringBuilder sb = new StringBuilder ("INSERT INTO T_ReportStatement "
			+ "(AD_PInstance_ID, Fact_Acct_ID, LevelNo,"
			+ "DateAcct, Name, Description,"
			+ "AmtAcctDr, AmtAcctCr, Balance, Qty) ");
		sb.append("SELECT ").append(getAD_PInstance_ID()).append(",0,0,")
			.append(DB.TO_DATE(p_DateAcct_From, true)).append(",")
			.append(DB.TO_STRING(Msg.getMsg(Env.getCtx(), "BeginningBalance"))).append(",NULL,"
			+ "COALESCE(SUM(AmtAcctDr),0), COALESCE(SUM(AmtAcctCr),0), COALESCE(SUM(AmtAcctDr-AmtAcctCr),0), COALESCE(SUM(Qty),0) "
			+ "FROM Fact_Acct "
			+ "WHERE ").append(m_parameterWhere)
			.append(" AND TRUNC(DateAcct) < ").append(DB.TO_DATE(p_DateAcct_From));
			
		//	Start Beginning of Year
		if (p_Account_ID > 0)
		{
			m_acct = new MElementValue (getCtx(), p_Account_ID, get_TrxName());
			if (!m_acct.isBalanceSheet())
			{
				MPeriod first = MPeriod.getFirstInYear (getCtx(), p_DateAcct_From, p_AD_Org_ID);
				if (first != null)
					sb.append(" AND TRUNC(DateAcct) >= ").append(DB.TO_DATE(first.getStartDate()));
				else
					log.log(Level.SEVERE, "First period not found");
			}
		}
		//
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("#" + no + " (Account_ID=" + p_Account_ID + ")");
		if (log.isLoggable(Level.FINEST)) log.finest(sb.toString());
	}	//	createBalanceLine

	/**
	 * 	Create Detail Lines
	 */
	private void createDetailLines()
	{
		StringBuilder sb = new StringBuilder ("INSERT INTO T_ReportStatement "
			+ "(AD_PInstance_ID, Fact_Acct_ID, LevelNo,"
			+ "DateAcct, Name, Description,"
			+ "AmtAcctDr, AmtAcctCr, Balance, Qty) ");
		sb.append("SELECT ").append(getAD_PInstance_ID()).append(",Fact_Acct_ID,1,")
			.append("TRUNC(DateAcct),NULL,NULL,"
			+ "AmtAcctDr, AmtAcctCr, AmtAcctDr-AmtAcctCr, Qty "
			+ "FROM Fact_Acct "
			+ "WHERE ").append(m_parameterWhere)
			.append(" AND TRUNC(DateAcct) BETWEEN ").append(DB.TO_DATE(p_DateAcct_From))
			.append(" AND ").append(DB.TO_DATE(p_DateAcct_To));
		//
		int no = DB.executeUpdate(sb.toString(), get_TrxName());
		if (log.isLoggable(Level.FINE)) log.fine("#" + no);
		if (log.isLoggable(Level.FINEST)) log.finest(sb.toString());

		//	Set Name,Description
		String sql_select;
		Language lang = Language.getLoginLanguage();
		if (Env.isBaseLanguage(lang, "AD_Element")) {
			sql_select = "SELECT e.Name, fa.Description "
					+ "FROM Fact_Acct fa"
					+ " INNER JOIN AD_Table t ON (fa.AD_Table_ID=t.AD_Table_ID)"
					+ " INNER JOIN AD_Element e ON (t.TableName||'_ID'=e.ColumnName) "
					+ "WHERE r.Fact_Acct_ID=fa.Fact_Acct_ID";
		} else {
			sql_select = "SELECT et.Name, fa.Description "
					+ "FROM Fact_Acct fa"
					+ " INNER JOIN AD_Table t ON (fa.AD_Table_ID=t.AD_Table_ID)"
					+ " INNER JOIN AD_Element e ON (t.TableName||'_ID'=e.ColumnName) "
					+ " INNER JOIN AD_Element_Trl et ON (e.AD_Element_ID=et.AD_Element_ID AND et.AD_Language='"+lang.getAD_Language()+"') "
					+ "WHERE r.Fact_Acct_ID=fa.Fact_Acct_ID";
		}
		//	Translated Version ...
		sb = new StringBuilder ("UPDATE T_ReportStatement r SET (Name,Description)=(")
			.append(sql_select).append(") "
			+ "WHERE Fact_Acct_ID <> 0 AND AD_PInstance_ID=").append(getAD_PInstance_ID());
		//
	   no = DB.executeUpdate(sb.toString(), get_TrxName());
	   if (log.isLoggable(Level.FINE)) log.fine("Name #" + no);
	   if (log.isLoggable(Level.FINEST)) log.finest("Name - " + sb);

	}	//	createDetailLines

}	//	FinStatement
