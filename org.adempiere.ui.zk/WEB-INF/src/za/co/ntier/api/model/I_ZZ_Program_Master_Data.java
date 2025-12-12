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
package za.co.ntier.api.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZZ_Program_Master_Data
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_Program_Master_Data 
{

    /** TableName=ZZ_Program_Master_Data */
    public static final String Table_Name = "ZZ_Program_Master_Data";

    /** AD_Table_ID=1000034 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Unit.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Unit.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException;

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Line_Manager_ID */
    public static final String COLUMNNAME_Line_Manager_ID = "Line_Manager_ID";

	/** Set Snr Mgr	  */
	public void setLine_Manager_ID (int Line_Manager_ID);

	/** Get Snr Mgr	  */
	public int getLine_Manager_ID();

	public org.compiere.model.I_AD_User getLine_Manager() throws RuntimeException;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

	/** Set Title.
	  * Name this entity is referred to as
	  */
	public void setTitle (String Title);

	/** Get Title.
	  * Name this entity is referred to as
	  */
	public String getTitle();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ZZSupportInfo */
    public static final String COLUMNNAME_ZZSupportInfo = "ZZSupportInfo";

	/** Set Support Info	  */
	public void setZZSupportInfo (String ZZSupportInfo);

	/** Get Support Info	  */
	public String getZZSupportInfo();

    /** Column name ZZ_Criteria */
    public static final String COLUMNNAME_ZZ_Criteria = "ZZ_Criteria";

	/** Set Criteria	  */
	public void setZZ_Criteria (String ZZ_Criteria);

	/** Get Criteria	  */
	public String getZZ_Criteria();

    /** Column name ZZ_Date_Approved */
    public static final String COLUMNNAME_ZZ_Date_Approved = "ZZ_Date_Approved";

	/** Set Date Approved	  */
	public void setZZ_Date_Approved (Timestamp ZZ_Date_Approved);

	/** Get Date Approved	  */
	public Timestamp getZZ_Date_Approved();

    /** Column name ZZ_Date_Not_Approved */
    public static final String COLUMNNAME_ZZ_Date_Not_Approved = "ZZ_Date_Not_Approved";

	/** Set Date Not Approved	  */
	public void setZZ_Date_Not_Approved (Timestamp ZZ_Date_Not_Approved);

	/** Get Date Not Approved	  */
	public Timestamp getZZ_Date_Not_Approved();

    /** Column name ZZ_Date_Submitted */
    public static final String COLUMNNAME_ZZ_Date_Submitted = "ZZ_Date_Submitted";

	/** Set Date Submitted	  */
	public void setZZ_Date_Submitted (Timestamp ZZ_Date_Submitted);

	/** Get Date Submitted	  */
	public Timestamp getZZ_Date_Submitted();

    /** Column name ZZ_DocAction */
    public static final String COLUMNNAME_ZZ_DocAction = "ZZ_DocAction";

	/** Set Document Action	  */
	public void setZZ_DocAction (String ZZ_DocAction);

	/** Get Document Action	  */
	public String getZZ_DocAction();

    /** Column name ZZ_DocStatus */
    public static final String COLUMNNAME_ZZ_DocStatus = "ZZ_DocStatus";

	/** Set Document Status	  */
	public void setZZ_DocStatus (String ZZ_DocStatus);

	/** Get Document Status	  */
	public String getZZ_DocStatus();

    /** Column name ZZ_Program_Master_Data_ID */
    public static final String COLUMNNAME_ZZ_Program_Master_Data_ID = "ZZ_Program_Master_Data_ID";

	/** Set Program Master Data	  */
	public void setZZ_Program_Master_Data_ID (int ZZ_Program_Master_Data_ID);

	/** Get Program Master Data	  */
	public int getZZ_Program_Master_Data_ID();

    /** Column name ZZ_Program_Master_Data_UU */
    public static final String COLUMNNAME_ZZ_Program_Master_Data_UU = "ZZ_Program_Master_Data_UU";

	/** Set ZZ_Program_Master_Data_UU	  */
	public void setZZ_Program_Master_Data_UU (String ZZ_Program_Master_Data_UU);

	/** Get ZZ_Program_Master_Data_UU	  */
	public String getZZ_Program_Master_Data_UU();

    /** Column name ZZ_Snr_Mgr_LP_ID */
    public static final String COLUMNNAME_ZZ_Snr_Mgr_LP_ID = "ZZ_Snr_Mgr_LP_ID";

	/** Set Snr Mgr LP	  */
	public void setZZ_Snr_Mgr_LP_ID (int ZZ_Snr_Mgr_LP_ID);

	/** Get Snr Mgr LP	  */
	public int getZZ_Snr_Mgr_LP_ID();

	public org.compiere.model.I_AD_User getZZ_Snr_Mgr_LP() throws RuntimeException;

    /** Column name ZZ_Submitter_ID */
    public static final String COLUMNNAME_ZZ_Submitter_ID = "ZZ_Submitter_ID";

	/** Set Submitted By	  */
	public void setZZ_Submitter_ID (int ZZ_Submitter_ID);

	/** Get Submitted By	  */
	public int getZZ_Submitter_ID();

	public org.compiere.model.I_AD_User getZZ_Submitter() throws RuntimeException;
}
