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
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for S_ResourceUnAvailable
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="S_ResourceUnAvailable")
public class X_S_ResourceUnAvailable extends PO implements I_S_ResourceUnAvailable, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_S_ResourceUnAvailable (Properties ctx, int S_ResourceUnAvailable_ID, String trxName)
    {
      super (ctx, S_ResourceUnAvailable_ID, trxName);
      /** if (S_ResourceUnAvailable_ID == 0)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setS_ResourceUnAvailable_ID (0);
			setS_Resource_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_S_ResourceUnAvailable (Properties ctx, int S_ResourceUnAvailable_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, S_ResourceUnAvailable_ID, trxName, virtualColumns);
      /** if (S_ResourceUnAvailable_ID == 0)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setS_ResourceUnAvailable_ID (0);
			setS_Resource_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_S_ResourceUnAvailable (Properties ctx, String S_ResourceUnAvailable_UU, String trxName)
    {
      super (ctx, S_ResourceUnAvailable_UU, trxName);
      /** if (S_ResourceUnAvailable_UU == null)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setS_ResourceUnAvailable_ID (0);
			setS_Resource_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_S_ResourceUnAvailable (Properties ctx, String S_ResourceUnAvailable_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, S_ResourceUnAvailable_UU, trxName, virtualColumns);
      /** if (S_ResourceUnAvailable_UU == null)
        {
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setS_ResourceUnAvailable_ID (0);
			setS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_ResourceUnAvailable (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_S_ResourceUnAvailable[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date From.
		@param DateFrom Starting date for a range
	*/
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date To.
		@param DateTo End date of a date range
	*/
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

	/** Set Resource Unavailability.
		@param S_ResourceUnAvailable_ID Resource Unavailability
	*/
	public void setS_ResourceUnAvailable_ID (int S_ResourceUnAvailable_ID)
	{
		if (S_ResourceUnAvailable_ID < 1)
			set_ValueNoCheck (COLUMNNAME_S_ResourceUnAvailable_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_S_ResourceUnAvailable_ID, Integer.valueOf(S_ResourceUnAvailable_ID));
	}

	/** Get Resource Unavailability.
		@return Resource Unavailability	  */
	public int getS_ResourceUnAvailable_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ResourceUnAvailable_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set S_ResourceUnAvailable_UU.
		@param S_ResourceUnAvailable_UU S_ResourceUnAvailable_UU
	*/
	public void setS_ResourceUnAvailable_UU (String S_ResourceUnAvailable_UU)
	{
		set_Value (COLUMNNAME_S_ResourceUnAvailable_UU, S_ResourceUnAvailable_UU);
	}

	/** Get S_ResourceUnAvailable_UU.
		@return S_ResourceUnAvailable_UU	  */
	public String getS_ResourceUnAvailable_UU()
	{
		return (String)get_Value(COLUMNNAME_S_ResourceUnAvailable_UU);
	}

	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
	{
		return (org.compiere.model.I_S_Resource)MTable.get(getCtx(), org.compiere.model.I_S_Resource.Table_ID)
			.getPO(getS_Resource_ID(), get_TrxName());
	}

	/** Set Resource.
		@param S_Resource_ID Resource
	*/
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1)
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Resource.
		@return Resource
	  */
	public int getS_Resource_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getS_Resource_ID()));
    }
}