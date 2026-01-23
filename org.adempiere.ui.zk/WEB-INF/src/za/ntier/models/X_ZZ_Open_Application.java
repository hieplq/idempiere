/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package za.ntier.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_Open_Application
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Open_Application")
public class X_ZZ_Open_Application extends PO implements I_ZZ_Open_Application, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250825L;

    /** Standard Constructor */
    public X_ZZ_Open_Application (Properties ctx, int ZZ_Open_Application_ID, String trxName)
    {
      super (ctx, ZZ_Open_Application_ID, trxName);
      /** if (ZZ_Open_Application_ID == 0)
        {
			setC_Year_ID (0);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZZ_Open_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Open_Application (Properties ctx, int ZZ_Open_Application_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Open_Application_ID, trxName, virtualColumns);
      /** if (ZZ_Open_Application_ID == 0)
        {
			setC_Year_ID (0);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZZ_Open_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Open_Application (Properties ctx, String ZZ_Open_Application_UU, String trxName)
    {
      super (ctx, ZZ_Open_Application_UU, trxName);
      /** if (ZZ_Open_Application_UU == null)
        {
			setC_Year_ID (0);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZZ_Open_Application_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Open_Application (Properties ctx, String ZZ_Open_Application_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Open_Application_UU, trxName, virtualColumns);
      /** if (ZZ_Open_Application_UU == null)
        {
			setC_Year_ID (0);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZZ_Open_Application_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZZ_Open_Application (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_ZZ_Open_Application[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
	{
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_ID)
			.getPO(getC_Year_ID(), get_TrxName());
	}

	/** Set Year.
		@param C_Year_ID Calendar Year
	*/
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document No.
		@param DocumentNo Document sequence number of the document
	*/
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo()
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set End Date.
		@param EndDate Last effective date (inclusive)
	*/
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Start Date.
		@param StartDate First effective day (inclusive)
	*/
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Date Approved.
		@param ZZ_Date_Approved Date Approved
	*/
	public void setZZ_Date_Approved (Timestamp ZZ_Date_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_Approved, ZZ_Date_Approved);
	}

	/** Get Date Approved.
		@return Date Approved	  */
	public Timestamp getZZ_Date_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Approved);
	}

	/** Set Date Not Approved.
		@param ZZ_Date_Not_Approved Date Not Approved
	*/
	public void setZZ_Date_Not_Approved (Timestamp ZZ_Date_Not_Approved)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Approved, ZZ_Date_Not_Approved);
	}

	/** Get Date Not Approved.
		@return Date Not Approved	  */
	public Timestamp getZZ_Date_Not_Approved()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Approved);
	}

	/** Set Date Not Recommended.
		@param ZZ_Date_Not_Recommended Date Not Recommended
	*/
	public void setZZ_Date_Not_Recommended (Timestamp ZZ_Date_Not_Recommended)
	{
		set_Value (COLUMNNAME_ZZ_Date_Not_Recommended, ZZ_Date_Not_Recommended);
	}

	/** Get Date Not Recommended.
		@return Date Not Recommended	  */
	public Timestamp getZZ_Date_Not_Recommended()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Not_Recommended);
	}

	/** Set Date Recommended.
		@param ZZ_Date_Recommended Date Recommended
	*/
	public void setZZ_Date_Recommended (Timestamp ZZ_Date_Recommended)
	{
		set_Value (COLUMNNAME_ZZ_Date_Recommended, ZZ_Date_Recommended);
	}

	/** Get Date Recommended.
		@return Date Recommended	  */
	public Timestamp getZZ_Date_Recommended()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Recommended);
	}

	/** Set Date Submitted.
		@param ZZ_Date_Submitted Date Submitted
	*/
	public void setZZ_Date_Submitted (Timestamp ZZ_Date_Submitted)
	{
		set_Value (COLUMNNAME_ZZ_Date_Submitted, ZZ_Date_Submitted);
	}

	/** Get Date Submitted.
		@return Date Submitted	  */
	public Timestamp getZZ_Date_Submitted()
	{
		return (Timestamp)get_Value(COLUMNNAME_ZZ_Date_Submitted);
	}

	/** Approve/Do Not Approve = AP */
	public static final String ZZ_DOCACTION_ApproveDoNotApprove = "AP";
	/** Complete = CO */
	public static final String ZZ_DOCACTION_Complete = "CO";
	/** Final Approval/Do not Approve = FA */
	public static final String ZZ_DOCACTION_FinalApprovalDoNotApprove = "FA";
	/** Recommend = RE */
	public static final String ZZ_DOCACTION_Recommend = "RE";
	/** Submit to Manager Finance Consumables = SC */
	public static final String ZZ_DOCACTION_SubmitToManagerFinanceConsumables = "SC";
	/** Submit to SDL Finance Mgr = SD */
	public static final String ZZ_DOCACTION_SubmitToSDLFinanceMgr = "SD";
	/** Submit to Line Manager = SU */
	public static final String ZZ_DOCACTION_SubmitToLineManager = "SU";
	/** Set Document Action.
		@param ZZ_DocAction Document Action
	*/
	public void setZZ_DocAction (String ZZ_DocAction)
	{

		set_Value (COLUMNNAME_ZZ_DocAction, ZZ_DocAction);
	}

	/** Get Document Action.
		@return Document Action	  */
	public String getZZ_DocAction()
	{
		return (String)get_Value(COLUMNNAME_ZZ_DocAction);
	}

	/** Approved By Manager Finance Consumables = AC */
	public static final String ZZ_DOCSTATUS_ApprovedByManagerFinanceConsumables = "AC";
	/** Approved = AP */
	public static final String ZZ_DOCSTATUS_Approved = "AP";
	/** Completed = CO */
	public static final String ZZ_DOCSTATUS_Completed = "CO";
	/** Draft = DR */
	public static final String ZZ_DOCSTATUS_Draft = "DR";
	/** In Progress = IP */
	public static final String ZZ_DOCSTATUS_InProgress = "IP";
	/** Not Approved By Manager Finance Consumables = NC */
	public static final String ZZ_DOCSTATUS_NotApprovedByManagerFinanceConsumables = "NC";
	/** Not Approved By SDL Finance Mgr = ND */
	public static final String ZZ_DOCSTATUS_NotApprovedBySDLFinanceMgr = "ND";
	/** Not Approved By IT Manager = NI */
	public static final String ZZ_DOCSTATUS_NotApprovedByITManager = "NI";
	/** Not Approved by LM = NL */
	public static final String ZZ_DOCSTATUS_NotApprovedByLM = "NL";
	/** Not Recommended = NR */
	public static final String ZZ_DOCSTATUS_NotRecommended = "NR";
	/** Not Approved by Snr Admin Finance = NS */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrAdminFinance = "NS";
	/** Recommended = RC */
	public static final String ZZ_DOCSTATUS_Recommended = "RC";
	/** Submitted to Manager Finance Consumables = SC */
	public static final String ZZ_DOCSTATUS_SubmittedToManagerFinanceConsumables = "SC";
	/** Submitted To SDL Finance Mgr = SD */
	public static final String ZZ_DOCSTATUS_SubmittedToSDLFinanceMgr = "SD";
	/** Submitted To IT Manager = SI */
	public static final String ZZ_DOCSTATUS_SubmittedToITManager = "SI";
	/** Submitted To IT Admin = ST */
	public static final String ZZ_DOCSTATUS_SubmittedToITAdmin = "ST";
	/** Submitted = SU */
	public static final String ZZ_DOCSTATUS_Submitted = "SU";
	/** Set Document Status.
		@param ZZ_DocStatus Document Status
	*/
	public void setZZ_DocStatus (String ZZ_DocStatus)
	{

		set_Value (COLUMNNAME_ZZ_DocStatus, ZZ_DocStatus);
	}

	/** Get Document Status.
		@return Document Status	  */
	public String getZZ_DocStatus()
	{
		return (String)get_Value(COLUMNNAME_ZZ_DocStatus);
	}

	public org.compiere.model.I_AD_User getZZ_Exec_Approver() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Exec_Approver_ID(), get_TrxName());
	}

	/** Set Exec Approver.
		@param ZZ_Exec_Approver_ID Exec Approver
	*/
	public void setZZ_Exec_Approver_ID (int ZZ_Exec_Approver_ID)
	{
		if (ZZ_Exec_Approver_ID < 1)
			set_Value (COLUMNNAME_ZZ_Exec_Approver_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Exec_Approver_ID, Integer.valueOf(ZZ_Exec_Approver_ID));
	}

	/** Get Exec Approver.
		@return Exec Approver	  */
	public int getZZ_Exec_Approver_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Exec_Approver_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Open Application.
		@param ZZ_Open_Application_ID Open Application
	*/
	public void setZZ_Open_Application_ID (int ZZ_Open_Application_ID)
	{
		if (ZZ_Open_Application_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Open_Application_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Open_Application_ID, Integer.valueOf(ZZ_Open_Application_ID));
	}

	/** Get Open Application.
		@return Open Application	  */
	public int getZZ_Open_Application_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Open_Application_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Open_Application_UU.
		@param ZZ_Open_Application_UU ZZ_Open_Application_UU
	*/
	public void setZZ_Open_Application_UU (String ZZ_Open_Application_UU)
	{
		set_Value (COLUMNNAME_ZZ_Open_Application_UU, ZZ_Open_Application_UU);
	}

	/** Get ZZ_Open_Application_UU.
		@return ZZ_Open_Application_UU	  */
	public String getZZ_Open_Application_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Open_Application_UU);
	}

	/** Set Programs.
		@param ZZ_Programs Programs
	*/
	public void setZZ_Programs (String ZZ_Programs)
	{

		set_ValueNoCheck (COLUMNNAME_ZZ_Programs, ZZ_Programs);
	}

	/** Get Programs.
		@return Programs	  */
	public String getZZ_Programs()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Programs);
	}

	public org.compiere.model.I_AD_User getZZ_Recommender() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getZZ_Recommender_ID(), get_TrxName());
	}

	/** Set Recommender.
		@param ZZ_Recommender_ID Recommender
	*/
	public void setZZ_Recommender_ID (int ZZ_Recommender_ID)
	{
		if (ZZ_Recommender_ID < 1)
			set_Value (COLUMNNAME_ZZ_Recommender_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Recommender_ID, Integer.valueOf(ZZ_Recommender_ID));
	}

	/** Get Recommender.
		@return Recommender	  */
	public int getZZ_Recommender_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Recommender_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}