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
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZZ_Application_Form
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
@SuppressWarnings("all")
public interface I_ZZ_Application_Form 
{

    /** TableName=ZZ_Application_Form */
    public static final String Table_Name = "ZZ_Application_Form";

    /** AD_Table_ID=1000048 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner.
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner.
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

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

    /** Column name NumberEmployees */
    public static final String COLUMNNAME_NumberEmployees = "NumberEmployees";

	/** Set Employees.
	  * Number of employees
	  */
	public void setNumberEmployees (int NumberEmployees);

	/** Get Employees.
	  * Number of employees
	  */
	public int getNumberEmployees();

    /** Column name OrgName */
    public static final String COLUMNNAME_OrgName = "OrgName";

	/** Set Organization Name.
	  * Name of the Organization
	  */
	public void setOrgName (String OrgName);

	/** Get Organization Name.
	  * Name of the Organization
	  */
	public String getOrgName();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner&#039;
s site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner&#039;
s site
	  */
	public String getReferenceNo();

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

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set User Name	  */
	public void setUserName (String UserName);

	/** Get User Name	  */
	public String getUserName();

    /** Column name ZZCetTvetCollege_ID */
    public static final String COLUMNNAME_ZZCetTvetCollege_ID = "ZZCetTvetCollege_ID";

	/** Set Cet/Tvet College	  */
	public void setZZCetTvetCollege_ID (int ZZCetTvetCollege_ID);

	/** Get Cet/Tvet College	  */
	public int getZZCetTvetCollege_ID();

	public org.compiere.model.I_C_BPartner getZZCetTvetCollege() throws RuntimeException;

    /** Column name ZZCollegeRecognised */
    public static final String COLUMNNAME_ZZCollegeRecognised = "ZZCollegeRecognised";

	/** Set College Recognised.
	  * Is the TVET College recognised as a centre of specialisation
	  */
	public void setZZCollegeRecognised (String ZZCollegeRecognised);

	/** Get College Recognised.
	  * Is the TVET College recognised as a centre of specialisation
	  */
	public String getZZCollegeRecognised();

    /** Column name ZZCollegeRegistered */
    public static final String COLUMNNAME_ZZCollegeRegistered = "ZZCollegeRegistered";

	/** Set College Registered.
	  * Is the TVET College registered with the DHET?
	  */
	public void setZZCollegeRegistered (String ZZCollegeRegistered);

	/** Get College Registered.
	  * Is the TVET College registered with the DHET?
	  */
	public String getZZCollegeRegistered();

    /** Column name ZZCollegeSla */
    public static final String COLUMNNAME_ZZCollegeSla = "ZZCollegeSla";

	/** Set College SLA.
	  * Does the TVET College have an SLA with a company within the Yes  No MMS
	  */
	public void setZZCollegeSla (String ZZCollegeSla);

	/** Get College SLA.
	  * Does the TVET College have an SLA with a company within the Yes  No MMS
	  */
	public String getZZCollegeSla();

    /** Column name ZZHasPastParticipatedLecturer */
    public static final String COLUMNNAME_ZZHasPastParticipatedLecturer = "ZZHasPastParticipatedLecturer";

	/** Set Has Past Participated Lecturer.
	  * Has the institution participated in MQA Lecturer Development Programme in the past years?
	  */
	public void setZZHasPastParticipatedLecturer (String ZZHasPastParticipatedLecturer);

	/** Get Has Past Participated Lecturer.
	  * Has the institution participated in MQA Lecturer Development Programme in the past years?
	  */
	public String getZZHasPastParticipatedLecturer();

    /** Column name ZZNoLearners */
    public static final String COLUMNNAME_ZZNoLearners = "ZZNoLearners";

	/** Set No Learners.
	  * Number of learners applying should be based on
	  */
	public void setZZNoLearners (int ZZNoLearners);

	/** Get No Learners.
	  * Number of learners applying should be based on
	  */
	public int getZZNoLearners();

    /** Column name ZZPlacementWorkplaceCoaches */
    public static final String COLUMNNAME_ZZPlacementWorkplaceCoaches = "ZZPlacementWorkplaceCoaches";

	/** Set Placement Workplace Coaches.
	  * Placement of workplace coaches coaching learners on artisan development programmes
	  */
	public void setZZPlacementWorkplaceCoaches (int ZZPlacementWorkplaceCoaches);

	/** Get Placement Workplace Coaches.
	  * Placement of workplace coaches coaching learners on artisan development programmes
	  */
	public int getZZPlacementWorkplaceCoaches();

    /** Column name ZZProgramType */
    public static final String COLUMNNAME_ZZProgramType = "ZZProgramType";

	/** Set Program Type	  */
	public void setZZProgramType (String ZZProgramType);

	/** Get Program Type	  */
	public String getZZProgramType();

    /** Column name ZZTotalNumberApplied */
    public static final String COLUMNNAME_ZZTotalNumberApplied = "ZZTotalNumberApplied";

	/** Set Total Number Applied For	  */
	public void setZZTotalNumberApplied (int ZZTotalNumberApplied);

	/** Get Total Number Applied For	  */
	public int getZZTotalNumberApplied();

    /** Column name ZZTrainingWorkplaceCoaches */
    public static final String COLUMNNAME_ZZTrainingWorkplaceCoaches = "ZZTrainingWorkplaceCoaches";

	/** Set Training Workplace Coaches.
	  * Training of workplace coaches by the mqa appointed service provider
	  */
	public void setZZTrainingWorkplaceCoaches (int ZZTrainingWorkplaceCoaches);

	/** Get Training Workplace Coaches.
	  * Training of workplace coaches by the mqa appointed service provider
	  */
	public int getZZTrainingWorkplaceCoaches();

    /** Column name ZZ_Application_Form_ID */
    public static final String COLUMNNAME_ZZ_Application_Form_ID = "ZZ_Application_Form_ID";

	/** Set Application Form	  */
	public void setZZ_Application_Form_ID (int ZZ_Application_Form_ID);

	/** Get Application Form	  */
	public int getZZ_Application_Form_ID();

    /** Column name ZZ_Application_Form_UU */
    public static final String COLUMNNAME_ZZ_Application_Form_UU = "ZZ_Application_Form_UU";

	/** Set ZZ_Application_Form_UU	  */
	public void setZZ_Application_Form_UU (String ZZ_Application_Form_UU);

	/** Get ZZ_Application_Form_UU	  */
	public String getZZ_Application_Form_UU();

    /** Column name ZZ_DocStatus */
    public static final String COLUMNNAME_ZZ_DocStatus = "ZZ_DocStatus";

	/** Set Document Status	  */
	public void setZZ_DocStatus (String ZZ_DocStatus);

	/** Get Document Status	  */
	public String getZZ_DocStatus();

    /** Column name ZZ_HasPivotalPlanSubmited */
    public static final String COLUMNNAME_ZZ_HasPivotalPlanSubmited = "ZZ_HasPivotalPlanSubmited";

	/** Set Has Pivotal Plan Submited.
	  * Has the organisation submitted the Pivotal Plan and Report In previous financial year?
	  */
	public void setZZ_HasPivotalPlanSubmited (boolean ZZ_HasPivotalPlanSubmited);

	/** Get Has Pivotal Plan Submited.
	  * Has the organisation submitted the Pivotal Plan and Report In previous financial year?
	  */
	public boolean isZZ_HasPivotalPlanSubmited();

    /** Column name ZZ_HasWSPSubmited */
    public static final String COLUMNNAME_ZZ_HasWSPSubmited = "ZZ_HasWSPSubmited";

	/** Set Has WSP Submited.
	  * Has the organisation submitted the WSP/ATR In previous financial year?
	  */
	public void setZZ_HasWSPSubmited (boolean ZZ_HasWSPSubmited);

	/** Get Has WSP Submited.
	  * Has the organisation submitted the WSP/ATR In previous financial year?
	  */
	public boolean isZZ_HasWSPSubmited();

    /** Column name ZZ_Org_Reg_No */
    public static final String COLUMNNAME_ZZ_Org_Reg_No = "ZZ_Org_Reg_No";

	/** Set Organisation Registration Number 	  */
	public void setZZ_Org_Reg_No (String ZZ_Org_Reg_No);

	/** Get Organisation Registration Number 	  */
	public String getZZ_Org_Reg_No();

    /** Column name ZZ_Program_Master_Data_ID */
    public static final String COLUMNNAME_ZZ_Program_Master_Data_ID = "ZZ_Program_Master_Data_ID";

	/** Set Program Master Data	  */
	public void setZZ_Program_Master_Data_ID (int ZZ_Program_Master_Data_ID);

	/** Get Program Master Data	  */
	public int getZZ_Program_Master_Data_ID();

    /** Column name ZZ_SDL_No */
    public static final String COLUMNNAME_ZZ_SDL_No = "ZZ_SDL_No";

	/** Set SDL Number	  */
	public void setZZ_SDL_No (String ZZ_SDL_No);

	/** Get SDL Number	  */
	public String getZZ_SDL_No();

    /** Column name ZZ_Side_SDL_No */
    public static final String COLUMNNAME_ZZ_Side_SDL_No = "ZZ_Side_SDL_No";

	/** Set Side SDL Number	  */
	public void setZZ_Side_SDL_No (String ZZ_Side_SDL_No);

	/** Get Side SDL Number	  */
	public String getZZ_Side_SDL_No();

    /** Column name ZZ_VAT */
    public static final String COLUMNNAME_ZZ_VAT = "ZZ_VAT";

	/** Set VAT	  */
	public void setZZ_VAT (String ZZ_VAT);

	/** Get VAT	  */
	public String getZZ_VAT();

    /** Column name ZZ_VAT_Exemption */
    public static final String COLUMNNAME_ZZ_VAT_Exemption = "ZZ_VAT_Exemption";

	/** Set VAT Exemption	  */
	public void setZZ_VAT_Exemption (byte[] ZZ_VAT_Exemption);

	/** Get VAT Exemption	  */
	public byte[] getZZ_VAT_Exemption();
}
