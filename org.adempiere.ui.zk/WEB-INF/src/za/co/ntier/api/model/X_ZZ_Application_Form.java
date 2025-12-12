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
package za.co.ntier.api.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for ZZ_Application_Form
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ZZ_Application_Form")
public class X_ZZ_Application_Form extends PO implements I_ZZ_Application_Form, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20251127L;

    /** Standard Constructor */
    public X_ZZ_Application_Form (Properties ctx, int ZZ_Application_Form_ID, String trxName)
    {
      super (ctx, ZZ_Application_Form_ID, trxName);
      /** if (ZZ_Application_Form_ID == 0)
        {
			setName (null);
			setZZ_Application_Form_ID (0);
			setZZ_HasPivotalPlanSubmited (false);
// N
			setZZ_HasWSPSubmited (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Application_Form (Properties ctx, int ZZ_Application_Form_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Application_Form_ID, trxName, virtualColumns);
      /** if (ZZ_Application_Form_ID == 0)
        {
			setName (null);
			setZZ_Application_Form_ID (0);
			setZZ_HasPivotalPlanSubmited (false);
// N
			setZZ_HasWSPSubmited (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Application_Form (Properties ctx, String ZZ_Application_Form_UU, String trxName)
    {
      super (ctx, ZZ_Application_Form_UU, trxName);
      /** if (ZZ_Application_Form_UU == null)
        {
			setName (null);
			setZZ_Application_Form_ID (0);
			setZZ_HasPivotalPlanSubmited (false);
// N
			setZZ_HasWSPSubmited (false);
// N
        } */
    }

    /** Standard Constructor */
    public X_ZZ_Application_Form (Properties ctx, String ZZ_Application_Form_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ZZ_Application_Form_UU, trxName, virtualColumns);
      /** if (ZZ_Application_Form_UU == null)
        {
			setName (null);
			setZZ_Application_Form_ID (0);
			setZZ_HasPivotalPlanSubmited (false);
// N
			setZZ_HasWSPSubmited (false);
// N
        } */
    }

    /** Load Constructor */
    public X_ZZ_Application_Form (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZZ_Application_Form[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getC_BPartner_ID(), get_TrxName());
	}

	/** Set Business Partner.
		@param C_BPartner_ID Identifies a Business Partner
	*/
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc Date of the Document
	*/
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set Employees.
		@param NumberEmployees Number of employees
	*/
	public void setNumberEmployees (int NumberEmployees)
	{
		set_Value (COLUMNNAME_NumberEmployees, Integer.valueOf(NumberEmployees));
	}

	/** Get Employees.
		@return Number of employees
	  */
	public int getNumberEmployees()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumberEmployees);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Organization Name.
		@param OrgName Name of the Organization
	*/
	public void setOrgName (String OrgName)
	{
		set_Value (COLUMNNAME_OrgName, OrgName);
	}

	/** Get Organization Name.
		@return Name of the Organization
	  */
	public String getOrgName()
	{
		return (String)get_Value(COLUMNNAME_OrgName);
	}

	/** Set Reference No.
		@param ReferenceNo Your customer or vendor number at the Business Partner&#039;s site
	*/
	public void setReferenceNo (String ReferenceNo)
	{
		set_ValueNoCheck (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner&#039;s site
	  */
	public String getReferenceNo()
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set User Name.
		@param UserName User Name
	*/
	public void setUserName (String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get User Name.
		@return User Name	  */
	public String getUserName()
	{
		return (String)get_Value(COLUMNNAME_UserName);
	}

	public org.compiere.model.I_C_BPartner getZZCetTvetCollege() throws RuntimeException
	{
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_ID)
			.getPO(getZZCetTvetCollege_ID(), get_TrxName());
	}

	/** Set Cet/Tvet College.
		@param ZZCetTvetCollege_ID Cet/Tvet College
	*/
	public void setZZCetTvetCollege_ID (int ZZCetTvetCollege_ID)
	{
		if (ZZCetTvetCollege_ID < 1)
			set_Value (COLUMNNAME_ZZCetTvetCollege_ID, null);
		else
			set_Value (COLUMNNAME_ZZCetTvetCollege_ID, Integer.valueOf(ZZCetTvetCollege_ID));
	}

	/** Get Cet/Tvet College.
		@return Cet/Tvet College	  */
	public int getZZCetTvetCollege_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZCetTvetCollege_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ZZCollegeRecognised AD_Reference_ID=319 */
	public static final int ZZCOLLEGERECOGNISED_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZCOLLEGERECOGNISED_No = "N";
	/** Yes = Y */
	public static final String ZZCOLLEGERECOGNISED_Yes = "Y";
	/** Set College Recognised.
		@param ZZCollegeRecognised Is the TVET College recognised as a centre of specialisation
	*/
	public void setZZCollegeRecognised (String ZZCollegeRecognised)
	{

		set_Value (COLUMNNAME_ZZCollegeRecognised, ZZCollegeRecognised);
	}

	/** Get College Recognised.
		@return Is the TVET College recognised as a centre of specialisation
	  */
	public String getZZCollegeRecognised()
	{
		return (String)get_Value(COLUMNNAME_ZZCollegeRecognised);
	}

	/** ZZCollegeRegistered AD_Reference_ID=319 */
	public static final int ZZCOLLEGEREGISTERED_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZCOLLEGEREGISTERED_No = "N";
	/** Yes = Y */
	public static final String ZZCOLLEGEREGISTERED_Yes = "Y";
	/** Set College Registered.
		@param ZZCollegeRegistered Is the TVET College registered with the DHET?
	*/
	public void setZZCollegeRegistered (String ZZCollegeRegistered)
	{

		set_Value (COLUMNNAME_ZZCollegeRegistered, ZZCollegeRegistered);
	}

	/** Get College Registered.
		@return Is the TVET College registered with the DHET?
	  */
	public String getZZCollegeRegistered()
	{
		return (String)get_Value(COLUMNNAME_ZZCollegeRegistered);
	}

	/** ZZCollegeSla AD_Reference_ID=319 */
	public static final int ZZCOLLEGESLA_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZCOLLEGESLA_No = "N";
	/** Yes = Y */
	public static final String ZZCOLLEGESLA_Yes = "Y";
	/** Set College SLA.
		@param ZZCollegeSla Does the TVET College have an SLA with a company within the Yes  No MMS
	*/
	public void setZZCollegeSla (String ZZCollegeSla)
	{

		set_Value (COLUMNNAME_ZZCollegeSla, ZZCollegeSla);
	}

	/** Get College SLA.
		@return Does the TVET College have an SLA with a company within the Yes  No MMS
	  */
	public String getZZCollegeSla()
	{
		return (String)get_Value(COLUMNNAME_ZZCollegeSla);
	}

	/** ZZHasPastParticipatedLecturer AD_Reference_ID=319 */
	public static final int ZZHASPASTPARTICIPATEDLECTURER_AD_Reference_ID=319;
	/** No = N */
	public static final String ZZHASPASTPARTICIPATEDLECTURER_No = "N";
	/** Yes = Y */
	public static final String ZZHASPASTPARTICIPATEDLECTURER_Yes = "Y";
	/** Set Has Past Participated Lecturer.
		@param ZZHasPastParticipatedLecturer Has the institution participated in MQA Lecturer Development Programme in the past years?
	*/
	public void setZZHasPastParticipatedLecturer (String ZZHasPastParticipatedLecturer)
	{

		set_Value (COLUMNNAME_ZZHasPastParticipatedLecturer, ZZHasPastParticipatedLecturer);
	}

	/** Get Has Past Participated Lecturer.
		@return Has the institution participated in MQA Lecturer Development Programme in the past years?
	  */
	public String getZZHasPastParticipatedLecturer()
	{
		return (String)get_Value(COLUMNNAME_ZZHasPastParticipatedLecturer);
	}

	/** Set No Learners.
		@param ZZNoLearners Number of learners applying should be based on
	*/
	public void setZZNoLearners (int ZZNoLearners)
	{
		set_Value (COLUMNNAME_ZZNoLearners, Integer.valueOf(ZZNoLearners));
	}

	/** Get No Learners.
		@return Number of learners applying should be based on
	  */
	public int getZZNoLearners()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZNoLearners);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Placement Workplace Coaches.
		@param ZZPlacementWorkplaceCoaches Placement of workplace coaches coaching learners on artisan development programmes
	*/
	public void setZZPlacementWorkplaceCoaches (int ZZPlacementWorkplaceCoaches)
	{
		set_Value (COLUMNNAME_ZZPlacementWorkplaceCoaches, Integer.valueOf(ZZPlacementWorkplaceCoaches));
	}

	/** Get Placement Workplace Coaches.
		@return Placement of workplace coaches coaching learners on artisan development programmes
	  */
	public int getZZPlacementWorkplaceCoaches()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZPlacementWorkplaceCoaches);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AET = AET */
	public static final String ZZPROGRAMTYPE_AET = "AET";
	/** ARTISAN_AIDES = ARTISAN_AIDES */
	public static final String ZZPROGRAMTYPE_ARTISAN_AIDES = "ARTISAN_AIDES";
	/** ARTISAN_DEV = ARTISAN_DEV */
	public static final String ZZPROGRAMTYPE_ARTISAN_DEV = "ARTISAN_DEV";
	/** ARTISAN_RPL = ARTISAN_RPL */
	public static final String ZZPROGRAMTYPE_ARTISAN_RPL = "ARTISAN_RPL";
	/** CANDIDACY = CANDIDACY */
	public static final String ZZPROGRAMTYPE_CANDIDACY = "CANDIDACY";
	/** CENTRE_SPECIALISATION = CENTRE_SPECIALISATION */
	public static final String ZZPROGRAMTYPE_CENTRE_SPECIALISATION = "CENTRE_SPECIALISATION";
	/** CET = CET */
	public static final String ZZPROGRAMTYPE_CET = "CET";
	/** DEV_PROGRAM = DEV_PROGRAM */
	public static final String ZZPROGRAMTYPE_DEV_PROGRAM = "DEV_PROGRAM";
	/** EXPERIENCE = EXPERIENCE */
	public static final String ZZPROGRAMTYPE_EXPERIENCE = "EXPERIENCE";
	/** HET_LECTURE_SUPPORT = HET_LECTURE_SUPPORT */
	public static final String ZZPROGRAMTYPE_HET_LECTURE_SUPPORT = "HET_LECTURE_SUPPORT";
	/** INHOUSE_TRAINING = INHOUSE_TRAINING */
	public static final String ZZPROGRAMTYPE_INHOUSE_TRAINING = "INHOUSE_TRAINING";
	/** INTERNSHIP = INTERNSHIP */
	public static final String ZZPROGRAMTYPE_INTERNSHIP = "INTERNSHIP";
	/** LEARNING_MATERIALS_DEVELOPMENT = LEARNING_MATERIALS_DEVELOPMENT */
	public static final String ZZPROGRAMTYPE_LEARNING_MATERIALS_DEVELOPMENT = "LEARNING_MATERIALS_DEVELOPMENT";
	/** MINING_COMMUNITY = MINING_COMMUNITY */
	public static final String ZZPROGRAMTYPE_MINING_COMMUNITY = "MINING_COMMUNITY";
	/** MULTIYEAR_PARTNERSHIP_INTERNSHIP = MULTIYEAR_PARTNERSHIP_INTERNSHIP */
	public static final String ZZPROGRAMTYPE_MULTIYEAR_PARTNERSHIP_INTERNSHIP = "MULTIYEAR_PARTNERSHIP_INTERNSHIP";
	/** MULTIYEAR_PARTNERSHIP_WORK_EXPERIENCE = MULTIYEAR_PARTNERSHIP_WORK_EXPERIENCE */
	public static final String ZZPROGRAMTYPE_MULTIYEAR_PARTNERSHIP_WORK_EXPERIENCE = "MULTIYEAR_PARTNERSHIP_WORK_EXPERIENCE";
	/** NCV_GRADUATES = NCV_GRADUATES */
	public static final String ZZPROGRAMTYPE_NCV_GRADUATES = "NCV_GRADUATES";
	/** NON_ARTISAN_DEV = NON_ARTISAN_DEV */
	public static final String ZZPROGRAMTYPE_NON_ARTISAN_DEV = "NON_ARTISAN_DEV";
	/** NON_ARTISAN_DEV_RPL = NON_ARTISAN_DEV_RPL */
	public static final String ZZPROGRAMTYPE_NON_ARTISAN_DEV_RPL = "NON_ARTISAN_DEV_RPL";
	/** OHASSP = OHASSP */
	public static final String ZZPROGRAMTYPE_OHASSP = "OHASSP";
	/** SMALL_BUSINESS = SMALL_BUSINESS */
	public static final String ZZPROGRAMTYPE_SMALL_BUSINESS = "SMALL_BUSINESS";
	/** SMALL_SCALE_MINING = SMALL_SCALE_MINING */
	public static final String ZZPROGRAMTYPE_SMALL_SCALE_MINING = "SMALL_SCALE_MINING";
	/** STANDARD_SETTING = STANDARD_SETTING */
	public static final String ZZPROGRAMTYPE_STANDARD_SETTING = "STANDARD_SETTING";
	/** TVET = TVET */
	public static final String ZZPROGRAMTYPE_TVET = "TVET";
	/** TVET_BURSARS = TVET_BURSARS */
	public static final String ZZPROGRAMTYPE_TVET_BURSARS = "TVET_BURSARS";
	/** UNEMPLOYED_YOUTH = UNEMPLOYED_YOUTH */
	public static final String ZZPROGRAMTYPE_UNEMPLOYED_YOUTH = "UNEMPLOYED_YOUTH";
	/** UNIVERSITY = UNIVERSITY */
	public static final String ZZPROGRAMTYPE_UNIVERSITY = "UNIVERSITY";
	/** WORKER_INITIATED_TRAINING = WORKER_INITIATED_TRAINING */
	public static final String ZZPROGRAMTYPE_WORKER_INITIATED_TRAINING = "WORKER_INITIATED_TRAINING";
	/** WORKPLACE_COACHES = WORKPLACE_COACHES */
	public static final String ZZPROGRAMTYPE_WORKPLACE_COACHES = "WORKPLACE_COACHES";
	/** Set Program Type.
		@param ZZProgramType Program Type
	*/
	public void setZZProgramType (String ZZProgramType)
	{

		set_Value (COLUMNNAME_ZZProgramType, ZZProgramType);
	}

	/** Get Program Type.
		@return Program Type	  */
	public String getZZProgramType()
	{
		return (String)get_Value(COLUMNNAME_ZZProgramType);
	}

	/** Set Total Number Applied For.
		@param ZZTotalNumberApplied Total Number Applied For
	*/
	public void setZZTotalNumberApplied (int ZZTotalNumberApplied)
	{
		set_Value (COLUMNNAME_ZZTotalNumberApplied, Integer.valueOf(ZZTotalNumberApplied));
	}

	/** Get Total Number Applied For.
		@return Total Number Applied For	  */
	public int getZZTotalNumberApplied()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZTotalNumberApplied);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Training Workplace Coaches.
		@param ZZTrainingWorkplaceCoaches Training of workplace coaches by the mqa appointed service provider
	*/
	public void setZZTrainingWorkplaceCoaches (int ZZTrainingWorkplaceCoaches)
	{
		set_Value (COLUMNNAME_ZZTrainingWorkplaceCoaches, Integer.valueOf(ZZTrainingWorkplaceCoaches));
	}

	/** Get Training Workplace Coaches.
		@return Training of workplace coaches by the mqa appointed service provider
	  */
	public int getZZTrainingWorkplaceCoaches()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZTrainingWorkplaceCoaches);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Application Form.
		@param ZZ_Application_Form_ID Application Form
	*/
	public void setZZ_Application_Form_ID (int ZZ_Application_Form_ID)
	{
		if (ZZ_Application_Form_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ZZ_Application_Form_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ZZ_Application_Form_ID, Integer.valueOf(ZZ_Application_Form_ID));
	}

	/** Get Application Form.
		@return Application Form	  */
	public int getZZ_Application_Form_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Application_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZZ_Application_Form_UU.
		@param ZZ_Application_Form_UU ZZ_Application_Form_UU
	*/
	public void setZZ_Application_Form_UU (String ZZ_Application_Form_UU)
	{
		set_Value (COLUMNNAME_ZZ_Application_Form_UU, ZZ_Application_Form_UU);
	}

	/** Get ZZ_Application_Form_UU.
		@return ZZ_Application_Form_UU	  */
	public String getZZ_Application_Form_UU()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Application_Form_UU);
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
	/** Not Recommended By Senior Mgr SDR = N1 */
	public static final String ZZ_DOCSTATUS_NotRecommendedBySeniorMgrSDR = "N1";
	/** Not Recommended By Senior Mgr Finance = N2 */
	public static final String ZZ_DOCSTATUS_NotRecommendedBySeniorMgrFinance = "N2";
	/** Not Recommended By COO = N3 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCOO = "N3";
	/** Not Recommended By CFO = N4 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCFO = "N4";
	/** Not Recommended By CEO = N5 */
	public static final String ZZ_DOCSTATUS_NotRecommendedByCEO = "N5";
	/** Not Approved by Snr Manager = NA */
	public static final String ZZ_DOCSTATUS_NotApprovedBySnrManager = "NA";
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
	/** Pending = PE */
	public static final String ZZ_DOCSTATUS_Pending = "PE";
	/** Recommended By Senior Mgr Finance = R1 */
	public static final String ZZ_DOCSTATUS_RecommendedBySeniorMgrFinance = "R1";
	/** Recommended By COO = R2 */
	public static final String ZZ_DOCSTATUS_RecommendedByCOO = "R2";
	/** Recommended By CFO = R3 */
	public static final String ZZ_DOCSTATUS_RecommendedByCFO = "R3";
	/** Recommended = RC */
	public static final String ZZ_DOCSTATUS_Recommended = "RC";
	/** Recommended By Senior Mgr SDR = RD */
	public static final String ZZ_DOCSTATUS_RecommendedBySeniorMgrSDR = "RD";
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

	/** Set Has Pivotal Plan Submited.
		@param ZZ_HasPivotalPlanSubmited Has the organisation submitted the Pivotal Plan and Report In previous financial year?
	*/
	public void setZZ_HasPivotalPlanSubmited (boolean ZZ_HasPivotalPlanSubmited)
	{
		set_Value (COLUMNNAME_ZZ_HasPivotalPlanSubmited, Boolean.valueOf(ZZ_HasPivotalPlanSubmited));
	}

	/** Get Has Pivotal Plan Submited.
		@return Has the organisation submitted the Pivotal Plan and Report In previous financial year?
	  */
	public boolean isZZ_HasPivotalPlanSubmited()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_HasPivotalPlanSubmited);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Has WSP Submited.
		@param ZZ_HasWSPSubmited Has the organisation submitted the WSP/ATR In previous financial year?
	*/
	public void setZZ_HasWSPSubmited (boolean ZZ_HasWSPSubmited)
	{
		set_Value (COLUMNNAME_ZZ_HasWSPSubmited, Boolean.valueOf(ZZ_HasWSPSubmited));
	}

	/** Get Has WSP Submited.
		@return Has the organisation submitted the WSP/ATR In previous financial year?
	  */
	public boolean isZZ_HasWSPSubmited()
	{
		Object oo = get_Value(COLUMNNAME_ZZ_HasWSPSubmited);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Organisation Registration Number .
		@param ZZ_Org_Reg_No Organisation Registration Number 
	*/
	public void setZZ_Org_Reg_No (String ZZ_Org_Reg_No)
	{
		set_Value (COLUMNNAME_ZZ_Org_Reg_No, ZZ_Org_Reg_No);
	}

	/** Get Organisation Registration Number .
		@return Organisation Registration Number 	  */
	public String getZZ_Org_Reg_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Org_Reg_No);
	}

	/** Set Program Master Data.
		@param ZZ_Program_Master_Data_ID Program Master Data
	*/
	public void setZZ_Program_Master_Data_ID (int ZZ_Program_Master_Data_ID)
	{
		if (ZZ_Program_Master_Data_ID < 1)
			set_Value (COLUMNNAME_ZZ_Program_Master_Data_ID, null);
		else
			set_Value (COLUMNNAME_ZZ_Program_Master_Data_ID, Integer.valueOf(ZZ_Program_Master_Data_ID));
	}

	/** Get Program Master Data.
		@return Program Master Data	  */
	public int getZZ_Program_Master_Data_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZZ_Program_Master_Data_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SDL Number.
		@param ZZ_SDL_No SDL Number
	*/
	public void setZZ_SDL_No (String ZZ_SDL_No)
	{
		set_Value (COLUMNNAME_ZZ_SDL_No, ZZ_SDL_No);
	}

	/** Get SDL Number.
		@return SDL Number	  */
	public String getZZ_SDL_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_SDL_No);
	}

	/** Set Side SDL Number.
		@param ZZ_Side_SDL_No Side SDL Number
	*/
	public void setZZ_Side_SDL_No (String ZZ_Side_SDL_No)
	{
		set_Value (COLUMNNAME_ZZ_Side_SDL_No, ZZ_Side_SDL_No);
	}

	/** Get Side SDL Number.
		@return Side SDL Number	  */
	public String getZZ_Side_SDL_No()
	{
		return (String)get_Value(COLUMNNAME_ZZ_Side_SDL_No);
	}

	/** Set VAT.
		@param ZZ_VAT VAT
	*/
	public void setZZ_VAT (String ZZ_VAT)
	{
		set_Value (COLUMNNAME_ZZ_VAT, ZZ_VAT);
	}

	/** Get VAT.
		@return VAT	  */
	public String getZZ_VAT()
	{
		return (String)get_Value(COLUMNNAME_ZZ_VAT);
	}

	/** Set VAT Exemption.
		@param ZZ_VAT_Exemption VAT Exemption
	*/
	public void setZZ_VAT_Exemption (byte[] ZZ_VAT_Exemption)
	{
		set_Value (COLUMNNAME_ZZ_VAT_Exemption, ZZ_VAT_Exemption);
	}

	/** Get VAT Exemption.
		@return VAT Exemption	  */
	public byte[] getZZ_VAT_Exemption()
	{
		return (byte[])get_Value(COLUMNNAME_ZZ_VAT_Exemption);
	}
}