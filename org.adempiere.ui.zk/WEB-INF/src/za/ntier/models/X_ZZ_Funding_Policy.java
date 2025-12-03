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

/** Generated Model for ZZ_Funding_Policy
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Funding_Policy")
public class X_ZZ_Funding_Policy extends PO implements I_ZZ_Funding_Policy, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250903L;

    /** Standard Constructor */
    public X_ZZ_Funding_Policy (Properties ctx, int ZZ_Funding_Policy_ID, String trxName)
    {
      super (ctx, ZZ_Funding_Policy_ID, trxName);
      /** if (ZZ_Funding_Policy_ID == 0)
        {
			setZZ_Funding_Policy_ID (0);
			setZZ_Is_Policy_Document_Uploaded (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Funding_Policy (Properties ctx, int ZZ_Funding_Policy_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Funding_Policy_ID, trxName, virtualColumns);
      /** if (ZZ_Funding_Policy_ID == 0)
        {
			setZZ_Funding_Policy_ID (0);
			setZZ_Is_Policy_Document_Uploaded (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Funding_Policy (Properties ctx, String ZZ_Funding_Policy_UU, String trxName)
    {
      super (ctx, ZZ_Funding_Policy_UU, trxName);
      /** if (ZZ_Funding_Policy_UU == null)
        {
			setZZ_Funding_Policy_ID (0);
			setZZ_Is_Policy_Document_Uploaded (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Funding_Policy (Properties ctx, String ZZ_Funding_Policy_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Funding_Policy_UU, trxName, virtualColumns);
      /** if (ZZ_Funding_Policy_UU == null)
        {
			setZZ_Funding_Policy_ID (0);
			setZZ_Is_Policy_Document_Uploaded (false);
// N
        } */
    }

    /** Load Constructor */
    public X_ZZ_Funding_Policy (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Funding_Policy[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Comments.
		@param Comments Comments or additional information
	*/
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments()
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	/** Exec Approve = AE */
	public static final String ZZ_DOCACTION_ExecApprove = "AE";
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
	/** Submit to Snr Mgr LP = SL */
	public static final String ZZ_DOCACTION_SubmitToSnrMgrLP = "SL";
	/** Submit to Recommender = SR */
	public static final String ZZ_DOCACTION_SubmitToRecommender = "SR";
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

	/** Set Funding Policy.
		@param ZZ_Funding_Policy_ID Funding Policy
	*/
	public void setZZ_Funding_Policy_ID (int ZZ_Funding_Policy_ID)
	{
		if (ZZ_Funding_Policy_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Funding_Policy_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Funding_Policy_ID, Integer.valueOf(ZZ_Funding_Policy_ID));
	}

	/** Get Funding Policy.
		@return Funding Policy	  */
	public int getZZ_Funding_Policy_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Funding_Policy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Funding_Policy_UU.
		@param ZZ_Funding_Policy_UU ZZ_Funding_Policy_UU
	*/
	public void setZZ_Funding_Policy_UU (String ZZ_Funding_Policy_UU)
	{
		set_Value (COLUMNNAME_ZZ_Funding_Policy_UU, ZZ_Funding_Policy_UU);
	}

	/** Get ZZ_Funding_Policy_UU.
		@return ZZ_Funding_Policy_UU	  */
	public String getZZ_Funding_Policy_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Funding_Policy_UU);
	}

	/** Set Policy Document Uploaded.
		@param ZZ_Is_Policy_Document_Uploaded Policy Document Uploaded
	*/
	public void setZZ_Is_Policy_Document_Uploaded (boolean ZZ_Is_Policy_Document_Uploaded)
	{
		set_Value (COLUMNNAME_ZZ_Is_Policy_Document_Uploaded, Boolean.valueOf(ZZ_Is_Policy_Document_Uploaded));
	}

	/** Get Policy Document Uploaded.
		@return Policy Document Uploaded	  */
	public boolean isZZ_Is_Policy_Document_Uploaded()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_Is_Policy_Document_Uploaded);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}