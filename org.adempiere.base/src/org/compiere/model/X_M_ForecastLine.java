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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_ForecastLine
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="M_ForecastLine")
public class X_M_ForecastLine extends PO implements I_M_ForecastLine, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_M_ForecastLine (Properties ctx, int M_ForecastLine_ID, String trxName)
    {
      super (ctx, M_ForecastLine_ID, trxName);
      /** if (M_ForecastLine_ID == 0)
        {
			setC_Period_ID (0);
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setM_ForecastLine_ID (0);
			setM_Forecast_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
// @M_Warehouse_ID@
			setQty (Env.ZERO);
			setQtyCalculated (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ForecastLine (Properties ctx, int M_ForecastLine_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, M_ForecastLine_ID, trxName, virtualColumns);
      /** if (M_ForecastLine_ID == 0)
        {
			setC_Period_ID (0);
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setM_ForecastLine_ID (0);
			setM_Forecast_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
// @M_Warehouse_ID@
			setQty (Env.ZERO);
			setQtyCalculated (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ForecastLine (Properties ctx, String M_ForecastLine_UU, String trxName)
    {
      super (ctx, M_ForecastLine_UU, trxName);
      /** if (M_ForecastLine_UU == null)
        {
			setC_Period_ID (0);
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setM_ForecastLine_ID (0);
			setM_Forecast_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
// @M_Warehouse_ID@
			setQty (Env.ZERO);
			setQtyCalculated (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ForecastLine (Properties ctx, String M_ForecastLine_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, M_ForecastLine_UU, trxName, virtualColumns);
      /** if (M_ForecastLine_UU == null)
        {
			setC_Period_ID (0);
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setM_ForecastLine_ID (0);
			setM_Forecast_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
// @M_Warehouse_ID@
			setQty (Env.ZERO);
			setQtyCalculated (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_ForecastLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_ForecastLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_ID)
			.getPO(getC_Period_ID(), get_TrxName());
	}

	/** Set Period.
		@param C_Period_ID Period of the Calendar
	*/
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Period.
		@return Period of the Calendar
	  */
	public int getC_Period_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_Period_ID()));
    }

	/** Set Date Promised.
		@param DatePromised Date Order was promised
	*/
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised()
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Forecast Line.
		@param M_ForecastLine_ID Forecast Line
	*/
	public void setM_ForecastLine_ID (int M_ForecastLine_ID)
	{
		if (M_ForecastLine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_ForecastLine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_ForecastLine_ID, Integer.valueOf(M_ForecastLine_ID));
	}

	/** Get Forecast Line.
		@return Forecast Line
	  */
	public int getM_ForecastLine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ForecastLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_ForecastLine_UU.
		@param M_ForecastLine_UU M_ForecastLine_UU
	*/
	public void setM_ForecastLine_UU (String M_ForecastLine_UU)
	{
		set_Value (COLUMNNAME_M_ForecastLine_UU, M_ForecastLine_UU);
	}

	/** Get M_ForecastLine_UU.
		@return M_ForecastLine_UU	  */
	public String getM_ForecastLine_UU()
	{
		return (String)get_Value(COLUMNNAME_M_ForecastLine_UU);
	}

	public org.compiere.model.I_M_Forecast getM_Forecast() throws RuntimeException
	{
		return (org.compiere.model.I_M_Forecast)MTable.get(getCtx(), org.compiere.model.I_M_Forecast.Table_ID)
			.getPO(getM_Forecast_ID(), get_TrxName());
	}

	/** Set Forecast.
		@param M_Forecast_ID Material Forecast
	*/
	public void setM_Forecast_ID (int M_Forecast_ID)
	{
		if (M_Forecast_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Forecast_ID, Integer.valueOf(M_Forecast_ID));
	}

	/** Get Forecast.
		@return Material Forecast
	  */
	public int getM_Forecast_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Forecast_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_ID)
			.getPO(getM_Warehouse_ID(), get_TrxName());
	}

	/** Set Warehouse.
		@param M_Warehouse_ID Storage Warehouse and Service Point
	*/
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1)
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param Qty Quantity
	*/
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Calculated Quantity.
		@param QtyCalculated Calculated Quantity
	*/
	public void setQtyCalculated (BigDecimal QtyCalculated)
	{
		set_Value (COLUMNNAME_QtyCalculated, QtyCalculated);
	}

	/** Get Calculated Quantity.
		@return Calculated Quantity
	  */
	public BigDecimal getQtyCalculated()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCalculated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_ID)
			.getPO(getSalesRep_ID(), get_TrxName());
	}

	/** Set Sales Representative.
		@param SalesRep_ID Sales Representative or Company Agent
	*/
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1)
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}