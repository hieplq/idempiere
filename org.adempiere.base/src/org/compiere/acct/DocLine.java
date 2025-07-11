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
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.model.MCostDetail;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.model.ProductCost;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *  Standard Document Line
 *
 *  @author 	Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs  
 *  @version 	$Id: DocLine.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 */
public class DocLine
{	
	/**
	 *	Create Document Line
	 *	@param po line persistent object
	 *	@param doc header
	 */
	public DocLine (PO po, Doc doc)
	{
		if (po == null)
			throw new IllegalArgumentException("PO is null");
		p_po = po;
		m_doc = doc;
		//
		//  Document Consistency
		if (p_po.getAD_Org_ID() == 0)
			p_po.setAD_Org_ID(m_doc.getAD_Org_ID());
	}	//	DocLine

	/** Persistent Object		*/
	protected PO				p_po = null;
	/** Parent					*/
	private Doc					m_doc = null;
	/**	 Log					*/
	protected transient CLogger	log = CLogger.getCLogger(getClass());

	/** Qty                     */
	private BigDecimal	 		m_qty = null;

	//  -- GL Amounts
	/** Debit Journal Amt   	*/
	private BigDecimal      	m_AmtSourceDr = Env.ZERO;
	/** Credit Journal Amt		*/
	private BigDecimal      	m_AmtSourceCr = Env.ZERO;
	/** Net Line Amt            */
	private BigDecimal			m_LineNetAmt = null;
	/** List Amount             */
	private BigDecimal			m_ListAmt = Env.ZERO;
	/** Discount Amount         */
	private BigDecimal 			m_DiscountAmt = Env.ZERO;

	/** Converted Amounts   	*/
	private BigDecimal      	m_AmtAcctDr = null;
	private BigDecimal      	m_AmtAcctCr = null;
	/** Acct Schema				*/
	private int             	m_C_AcctSchema_ID = 0;

	/**	Product Costs			*/
	private ProductCost			m_productCost = null;
	/** Production indicator	*/
	private boolean 			m_productionBOM = false;
	/** Outside Processing	*/
	private int 				m_PP_Cost_Collector_ID = 0;
	/** Account used only for GL Journal    */
	private MAccount 			m_account = null;

	/** Accounting Date				*/
	private Timestamp			m_DateAcct = null;
	/** Document Date				*/
	private Timestamp			m_DateDoc = null;
	/** Sales Region				*/
	private int					m_C_SalesRegion_ID = -1;
	/** Sales Region				*/
	private int					m_C_BPartner_ID = -1;
	/** Location From				*/
	private int					m_C_LocFrom_ID = 0;
	/** Location To					*/
	private int					m_C_LocTo_ID = 0;
	/** Item						*/
	private Boolean				m_isItem = null;
	/** Currency					*/
	private int					m_C_Currency_ID = -1;
	/** Conversion Type				*/
	private int					m_C_ConversionType_ID = -1;
	/** Department */
	private int					m_C_Department_ID		= -1;
	/** Cost Center */
	private int					m_C_CostCenter_ID		= -1;
	/** B Partner Employee */
	private int					m_C_Employee_ID			= -1;
	/** Asset */
	private int					m_A_Asset_ID			= -1;
    /** Period                        */
	private int					m_C_Period_ID = -1;
	private BigDecimal 			m_currencyRate = null;

	/**
	 *  Get Currency
	 *  @return C_Currency_ID
	 */
	public int getC_Currency_ID ()
	{
		if (m_C_Currency_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_Currency_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_Currency_ID = ii.intValue();
			}
			if (m_C_Currency_ID <= 0)
				m_C_Currency_ID = m_doc.getC_Currency_ID();
		}
		return m_C_Currency_ID;
	}   //  getC_Currency_ID

	/**
	 *  Get Conversion Type
	 *  @return C_ConversionType_ID
	 */
	public int getC_ConversionType_ID ()
	{
		if (m_C_ConversionType_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_ConversionType_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_ConversionType_ID = ii.intValue();
			}
			if (m_C_ConversionType_ID <= 0)
				m_C_ConversionType_ID = m_doc.getC_ConversionType_ID();
		}
		return m_C_ConversionType_ID;
	}   //  getC_ConversionType_ID

	/**
	 * 	Set C_ConversionType_ID
	 *	@param C_ConversionType_ID id
	 */
	protected void setC_ConversionType_ID(int C_ConversionType_ID)
	{
		m_C_ConversionType_ID = C_ConversionType_ID;
	}	//	setC_ConversionType_ID
	
	/**
	 * @return Currency rate
	 */
	public BigDecimal getCurrencyRate()
	{
		return m_currencyRate;
	}
	
	/**
	 * @param currencyRate
	 */
	protected void setCurrencyRate(BigDecimal currencyRate) 
	{
		m_currencyRate = currencyRate;
	}
	
	/**
	 *  Set Amount (DR)
	 *  @param sourceAmt source amt
	 */
	public void setAmount (BigDecimal sourceAmt)
	{
		m_AmtSourceDr = sourceAmt == null ? Env.ZERO : sourceAmt;
		m_AmtSourceCr = Env.ZERO;
	}   //  setAmounts

	/**
	 *  Set Amounts
	 *  @param amtSourceDr source amount dr
	 *  @param amtSourceCr source amount cr
	 */
	public void setAmount (BigDecimal amtSourceDr, BigDecimal amtSourceCr)
	{
		m_AmtSourceDr = amtSourceDr == null ? Env.ZERO : amtSourceDr;
		m_AmtSourceCr = amtSourceCr == null ? Env.ZERO : amtSourceCr;
	}   //  setAmounts

	/**
	 *  Set Converted Amounts
	 *  @param C_AcctSchema_ID acct schema
	 *  @param amtAcctDr acct amount dr
	 *  @param amtAcctCr acct amount cr
	 */
	public void setConvertedAmt (int C_AcctSchema_ID, BigDecimal amtAcctDr, BigDecimal amtAcctCr)
	{
		m_C_AcctSchema_ID = C_AcctSchema_ID;
		m_AmtAcctDr = amtAcctDr;
		m_AmtAcctCr = amtAcctCr;
	}   //  setConvertedAmt

	/**
	 *  Line Net Amount or Dr-Cr
	 *  @return balance
	 */
	public BigDecimal getAmtSource()
	{
		return m_AmtSourceDr.subtract(m_AmtSourceCr);
	}   //  getAmount

	/**
	 *  Get (Journal) Line Source Dr Amount
	 *  @return DR source amount
	 */
	public BigDecimal getAmtSourceDr()
	{
		return m_AmtSourceDr;
	}   //  getAmtSourceDr

	/**
	 *  Get (Journal) Line Source Cr Amount
	 *  @return CR source amount
	 */
	public BigDecimal getAmtSourceCr()
	{
		return m_AmtSourceCr;
	}   //  getAmtSourceCr

	/**
	 *  Line Journal Accounted Dr Amount
	 *  @return DR accounted amount
	 */
	public BigDecimal getAmtAcctDr()
	{
		return m_AmtAcctDr;
	}   //  getAmtAcctDr

	/**
	 *  Line Journal Accounted Cr Amount
	 *  @return CR accounted amount
	 */
	public BigDecimal getAmtAcctCr()
	{
		return m_AmtAcctCr;
	}   //  getAmtAccrCr

	/**
	 *  Charge Amount
	 *  @return charge amount
	 */
	public BigDecimal getChargeAmt()
	{
		int index = p_po.get_ColumnIndex("ChargeAmt");
		if (index != -1)
		{
			BigDecimal bd = (BigDecimal)p_po.get_Value(index);
			if (bd != null)
				return bd;
		}
		return Env.ZERO;
	}   //  getChargeAmt

	/**
	 *  Set Product Amounts
	 *  @param LineNetAmt Line Net Amt
	 *  @param PriceList Price List
	 *  @param Qty Qty for discount calc
	 */
	public void setAmount (BigDecimal LineNetAmt, BigDecimal PriceList, BigDecimal Qty)
	{
		m_LineNetAmt =  LineNetAmt == null ? Env.ZERO : LineNetAmt;

		if (PriceList != null && Qty != null)
			m_ListAmt = PriceList.multiply(Qty);
		if (m_ListAmt.compareTo(Env.ZERO) == 0)
			m_ListAmt = m_LineNetAmt;
		m_DiscountAmt = m_ListAmt.subtract(m_LineNetAmt);
		//
		setAmount (m_ListAmt, m_DiscountAmt);
	}   //  setAmounts

	/**
	 *  Line Discount
	 *  @return discount amount
	 */
	public BigDecimal getDiscount()
	{
		return m_DiscountAmt;
	}   //  getDiscount

	/**
	 *  Line List Amount
	 *  @return list amount
	 */
	public BigDecimal getListAmount()
	{
		return m_ListAmt;
	}   //  getListAmount

	/**
	 * 	Set Line Net Amt Difference
	 *	@param diff difference (to be subtracted)
	 */
	public void setLineNetAmtDifference (BigDecimal diff)
	{
		String msg = "Diff=" + diff
			+ " - LineNetAmt=" + m_LineNetAmt;
		m_LineNetAmt = m_LineNetAmt.subtract(diff);
		m_DiscountAmt = m_ListAmt.subtract(m_LineNetAmt);
		setAmount (m_ListAmt, m_DiscountAmt);
		msg += " -> " + m_LineNetAmt;
		log.fine(msg);
	}	//	setLineNetAmtDifference

	/**
	 *  Set Accounting Date
	 *  @param dateAcct accounting date
	 */
	public void setDateAcct (Timestamp dateAcct)
	{
		m_DateAcct = dateAcct;
	}   //  setDateAcct

	/**
	 *  Get Accounting Date
	 *  @return accounting date
	 */
	public Timestamp getDateAcct ()
	{
		if (m_DateAcct != null)
			return m_DateAcct;
		int index = p_po.get_ColumnIndex("DateAcct");
		if (index != -1)
		{
			m_DateAcct = (Timestamp)p_po.get_Value(index);
			if (m_DateAcct != null)
				return m_DateAcct;
		}
		m_DateAcct = m_doc.getDateAcct();
		return m_DateAcct;
	}   //  getDateAcct

	/**
	 *  Get FX Conversion Date
	 *  
	 *  The foreign exchange rate conversion date may be different from the accounting posting date in some cases (e.g. bank statement)
	 *  
	 *  @return FX conversion date 
	 */
	public Timestamp getDateConv ()
	{
		Timestamp dateConv = null;
		int index = p_po.get_ColumnIndex("DateAcct");
		if (index != -1)
		{
			dateConv = (Timestamp)p_po.get_Value(index);
		}
		

		if (dateConv == null)
			dateConv = getDateAcct();
		
		return dateConv;
	}   //  getDateAcct
	
	/**
	 *  Set Document Date
	 *  @param dateDoc doc date
	 */
	public void setDateDoc (Timestamp dateDoc)
	{
		m_DateDoc = dateDoc;
	}   //  setDateDoc

	/**
	 *  Get Document Date
	 *  @return document date
	 */
	public Timestamp getDateDoc ()
	{
		if (m_DateDoc != null)
			return m_DateDoc;
		int index = p_po.get_ColumnIndex("DateDoc");
		if (index != -1)
		{
			m_DateDoc = (Timestamp)p_po.get_Value(index);
			if (m_DateDoc != null)
				return m_DateDoc;
		}
		m_DateDoc = m_doc.getDateDoc();
		return m_DateDoc;
	}   //  getDateDoc


	/**
	 *  Set GL Journal Account
	 *  @param acct account
	 */
	public void setAccount (MAccount acct)
	{
		m_account = acct;
	}   //  setAccount

	/**
	 *  Get GL Journal Account
	 *  @return account
	 */
	public MAccount getAccount()
	{
		return m_account;
	}   //  getAccount

	/**
	 *  Line Account from Product (or Charge).
	 *
	 *  @param  AcctType see ProductCost.ACCTTYPE_* (0..3)
	 *  @param as Accounting schema
	 *  @return Requested Product Account
	 */
	public MAccount getAccount (int AcctType, MAcctSchema as)
	{
		//	Charge Account
		if (getM_Product_ID() == 0 && getC_Charge_ID() != 0)
		{
			BigDecimal amt = new BigDecimal (-1);		//	Revenue (-)
			if (!m_doc.isSOTrx())
				amt = new BigDecimal (+1);				//	Expense (+)
			MAccount acct = getChargeAccount(as, amt);
			if (acct != null)
				return acct;
		}
		//	Product Account
		return getProductCost().getAccount (AcctType, as);
	}   //  getAccount

	/**
	 * 	Get Charge
	 * 	@return C_Charge_ID
	 */
	protected int getC_Charge_ID()
	{
		int index = p_po.get_ColumnIndex("C_Charge_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	//	getC_Charge_ID

	/**
	 *  Get Charge Account
	 *  @param as account schema
	 *  @param amount amount for expense(+)/revenue(-)
	 *  @return Charge Account or null
	 */
	public MAccount getChargeAccount (MAcctSchema as, BigDecimal amount)
	{
		int C_Charge_ID = getC_Charge_ID();
		if (C_Charge_ID == 0)
			return null;
		return MCharge.getAccount(C_Charge_ID, as);
	}   //  getChargeAccount

	/**
	 * 	Get Period
	 * 	@return C_Period_ID
	 */
	protected int getC_Period_ID()
	{
		if (m_C_Period_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_Period_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_Period_ID = ii.intValue();
			}
			if (m_C_Period_ID == -1)
				m_C_Period_ID = 0;
		}
		return m_C_Period_ID;
	}	//	getC_Period_ID
	
	/**
	 * 	Set C_Period_ID
	 *	@param C_Period_ID id
	 */
	protected void setC_Period_ID (int C_Period_ID)
	{
		m_C_Period_ID = C_Period_ID;
	}	//	setC_Period_ID
	
	/**
	 *  Get (Journal) AcctSchema
	 *  @return C_AcctSchema_ID
	 */
	public int getC_AcctSchema_ID()
	{
		return m_C_AcctSchema_ID;
	}   //  getC_AcctSchema_ID

	/**
	 * 	Get Line ID
	 *	@return id of line PO
	 */
	public int get_ID()
	{
		return p_po.get_ID();
	}	//	get_ID
	
	/**
	 * 	Get AD_Org_ID
	 *	@return AD_Org_ID
	 */
	public int getAD_Org_ID()
	{
		return p_po.getAD_Org_ID();
	}	//	getAD_Org_ID
	
	/**
	 * 	Get Order AD_Org_ID
	 *	@return order AD_Org_ID if defined
	 */
	public int getOrder_Org_ID()
	{
		int C_OrderLine_ID = getC_OrderLine_ID();
		if (C_OrderLine_ID != 0)
		{
			String sql = "SELECT AD_Org_ID FROM C_OrderLine WHERE C_OrderLine_ID=?";
			int AD_Org_ID = DB.getSQLValue(null, sql, C_OrderLine_ID);
			if (AD_Org_ID > 0)
				return AD_Org_ID;
		}
		return getAD_Org_ID();
	}	//	getOrder_Org_ID

	/**
	 *  Product
	 *  @return M_Product_ID
	 */
	public int getM_Product_ID()
	{
		int index = p_po.get_ColumnIndex("M_Product_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_Product_ID

	/**
	 * 	Is this an Item Product (vs. not a Service, a charge)
	 *	@return true if product is of type item
	 */
	public boolean isItem()
	{
		if (m_isItem != null)
			return m_isItem.booleanValue();

		m_isItem = Boolean.FALSE;
		if (getM_Product_ID() != 0)
		{
			MProduct product = MProduct.get(Env.getCtx(), getM_Product_ID());
			if (product.get_ID() == getM_Product_ID() && product.isItem())
				m_isItem = Boolean.TRUE;
		}
		return m_isItem.booleanValue();
	}	//	isItem

	/**
	 *  ASI
	 *  @return M_AttributeSetInstance_ID
	 */
	public int getM_AttributeSetInstance_ID()
	{
		int index = p_po.get_ColumnIndex("M_AttributeSetInstance_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_AttributeSetInstance_ID
	
	/**
	 *  ASI
	 *  @return getM_AttributeSetInstanceTo_ID
	 */
	public int getM_AttributeSetInstanceTo_ID()
	{
		int index = p_po.get_ColumnIndex("M_AttributeSetInstanceTo_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_AttributeSetInstanceTo_ID

	/**
	 *  Get Warehouse Locator (from)
	 *  @return M_Locator_ID
	 */
	public int getM_Locator_ID()
	{
		int index = p_po.get_ColumnIndex("M_Locator_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_Locator_ID

	/**
	 *  Get Warehouse Locator To
	 *  @return to M_Locator_ID
	 */
	public int getM_LocatorTo_ID()
	{
		int index = p_po.get_ColumnIndex("M_LocatorTo_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_LocatorTo_ID

	/**
	 * 	Set Production BOM flag
	 *	@param productionBOM flag
	 */
	public void setProductionBOM(boolean productionBOM)
	{
		m_productionBOM = productionBOM;
	}	//	setProductionBOM
	
	/**
	 * 	Is this the BOM to be produced
	 *	@return true if BOM
	 */
	public boolean isProductionBOM()
	{
		return m_productionBOM;
	}	//	isProductionBOM
	
	/**
	 *  Get Production Header
	 *  @return M_Production_ID
	 */
	public int getM_Production_ID()
	{
		int index = p_po.get_ColumnIndex("M_Production_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getM_Production_ID

	/**
	 *  Get Order Line Reference
	 *  @return C_OrderLine_ID
	 */
	public int getC_OrderLine_ID()
	{
		int index = p_po.get_ColumnIndex("C_OrderLine_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_OrderLine_ID

	/**
	 * 	Get C_LocFrom_ID
	 *	@return C_Location_ID from
	 */
	public int getC_LocFrom_ID()
	{
		return m_C_LocFrom_ID;
	}	//	getC_LocFrom_ID
		
	/**
	 * 	Set C_LocFrom_ID
	 *	@param C_LocFrom_ID loc from
	 */
	public void setC_LocFrom_ID(int C_LocFrom_ID)
	{
		m_C_LocFrom_ID = C_LocFrom_ID;
	}	//	setC_LocFrom_ID

	/**
	 * 	Get PP_Cost_Collector_ID
	 *	@return Cost Collector ID
	 */
	public int getPP_Cost_Collector_ID()
	{
		return m_PP_Cost_Collector_ID;
	}	//	getC_LocFrom_ID

	/**
	 * 	Get PP_Cost_Collector_ID
	 *	@return Cost Collector ID
	 */
	public int setPP_Cost_Collector_ID(int PP_Cost_Collector_ID)
	{
		return m_PP_Cost_Collector_ID;
	}	//	getC_LocFrom_ID

	/**
	 * 	Get C_LocTo_ID
	 *	@return C_Location_ID to
	 */
	public int getC_LocTo_ID()
	{
		return m_C_LocTo_ID;
	}	//	getC_LocTo_ID

	/**
	 * 	Set C_LocTo_ID
	 *	@param C_LocTo_ID loc to
	 */
	public void setC_LocTo_ID(int C_LocTo_ID)
	{
		m_C_LocTo_ID = C_LocTo_ID;
	}	//	setC_LocTo_ID

	/**
	 * 	Get Product Cost Info
	 *	@return product cost
	 */
	public ProductCost getProductCost()
	{
		if (m_productCost == null)
			m_productCost = new ProductCost (Env.getCtx(), 
				getM_Product_ID(), getM_AttributeSetInstance_ID(), p_po.get_TrxName());
		return m_productCost;
	}	//	getProductCost
	
	// MZ Goodwill
	/**
	 *  Get Total Product Costs from Cost Detail or from Current Cost
	 *  @param as accounting schema
	 *  @param AD_Org_ID trx org
	 *	@param zeroCostsOK zero/no costs are OK
	 *	@param whereClause null are OK
	 *  @return costs
	 */
	public BigDecimal getProductCosts (MAcctSchema as, int AD_Org_ID, boolean zeroCostsOK, String whereClause)
	{
		if (whereClause != null && !as.getCostingMethod().equals(MAcctSchema.COSTINGMETHOD_StandardCosting))
		{
			MCostDetail cd = MCostDetail.get (Env.getCtx(), whereClause, 
					get_ID(), getM_AttributeSetInstance_ID(), as.getC_AcctSchema_ID(), p_po.get_TrxName());
			if (cd != null)
			{
				BigDecimal amt = cd.getAmt();
				BigDecimal pcost = getProductCosts(as, AD_Org_ID, zeroCostsOK, cd);
				if (amt.signum() != 0 && pcost.signum() != 0 && amt.signum() != pcost.signum())
					return amt.signum() > 0 ? pcost.negate() : pcost;
				else
					return pcost;
			}
		}
		return getProductCosts(as, AD_Org_ID, zeroCostsOK);
	}   //  getProductCosts
	// end MZ
	
	/**
	 *  Get Total Product Costs
	 *  @param as accounting schema
	 *  @param AD_Org_ID trx org
	 *	@param zeroCostsOK zero/no costs are OK
	 *  @return costs
	 */
	public BigDecimal getProductCosts (MAcctSchema as, int AD_Org_ID, boolean zeroCostsOK)
	{
		return getProductCosts(as, AD_Org_ID, zeroCostsOK, (MCostDetail) null);
	}
	
	/**
	 * Get Total Product Costs
	 * @param as accounting schema
	 * @param AD_Org_ID trx org
	 * @param zeroCostsOK zero/no costs are OK
	 * @param costDetail optional cost detail - use to retrieve the cost history
	 * @return costs
	 */
	public BigDecimal getProductCosts (MAcctSchema as, int AD_Org_ID, boolean zeroCostsOK, MCostDetail costDetail)
	{
		ProductCost pc = getProductCost();
		int C_OrderLine_ID = getC_OrderLine_ID();
		String costingMethod = null;
		BigDecimal costs = pc.getProductCosts(as, AD_Org_ID, costingMethod, 
			C_OrderLine_ID, zeroCostsOK, 
			getDateAcct(), costDetail, m_doc.isInBackDatePostingProcess());
		if (costs != null)
			return costs;
		return Env.ZERO;
	}   //  getProductCosts

	/**
	 * 	Get Product 
	 *	@return product or null if no product
	 */
	public MProduct getProduct()
	{
		if (m_productCost == null)
			m_productCost = new ProductCost (Env.getCtx(), 
				getM_Product_ID(), getM_AttributeSetInstance_ID(), p_po.get_TrxName());
		if (m_productCost != null)
			return m_productCost.getProduct();
		return null;
	}	//	getProduct

	/**
	 *  Get Revenue Recognition
	 *  @return C_RevenueRecognition_ID or 0
	 */
	public int getC_RevenueRecognition_ID()
	{
		MProduct product = getProduct();
		if (product != null)
			return product.getC_RevenueRecognition_ID();
		return 0;
	}   //  getC_RevenueRecognition_ID

	/**
	 *  Quantity UOM
	 *  @return Transaction or Storage C_UOM_ID
	 */
	public int getC_UOM_ID()
	{
		//	Trx UOM
		int index = p_po.get_ColumnIndex("C_UOM_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		//  Storage UOM
		MProduct product = getProduct();
		if (product != null)
			return product.getC_UOM_ID();
		//
		return 0;
	}   //  getC_UOM

	/**
	 *  Quantity
	 *  @param qty transaction Qty
	 * 	@param isSOTrx SL order trx (i.e. negative qty)
	 */
	public void setQty (BigDecimal qty, boolean isSOTrx)
	{
		if (qty == null)
			m_qty = Env.ZERO;
		else if (isSOTrx)
			m_qty = qty.negate();
		else
			m_qty = qty;
		getProductCost().setQty (qty);
	}   //  setQty

	/**
	 *  Quantity
	 *  @return transaction Qty
	 */
	public BigDecimal getQty()
	{
		return m_qty;
	}   //  getQty

	
	
	/**
	 *  Description
	 *  @return doc line description
	 */
	public String getDescription()
	{
		int index = p_po.get_ColumnIndex("Description");
		if (index != -1)
			return (String)p_po.get_Value(index);
		return null;
	}	//	getDescription

	/**
	 *  Line Tax
	 *  @return C_Tax_ID
	 */
	public int getC_Tax_ID()
	{
		int index = p_po.get_ColumnIndex("C_Tax_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	//	getC_Tax_ID

	/**
	 *  Get Line Number
	 *  @return line no
	 */
	public int getLine()
	{
		int index = p_po.get_ColumnIndex("Line");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getLine

	/**
	 *  Get BPartner
	 *  @return C_BPartner_ID
	 */
	public int getC_BPartner_ID()
	{
		if (m_C_BPartner_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_BPartner_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_BPartner_ID = ii.intValue();
			}
			if (m_C_BPartner_ID <= 0)
				m_C_BPartner_ID = m_doc.getC_BPartner_ID();
		}
		return m_C_BPartner_ID;
	}   //  getC_BPartner_ID

	/**
	 *  Get BPartner Employee
	 *  @return C_Employee_ID
	 */
	public int getC_Employee_ID()
	{
		if (m_C_Employee_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_Employee_ID");
			if (index != -1)
			{
				Integer ii = (Integer)p_po.get_Value(index);
				if (ii != null)
					m_C_Employee_ID = ii.intValue();
			}
			if (m_C_Employee_ID <= 0)
				m_C_Employee_ID = 0;
		}
		return m_C_Employee_ID;
	}// getC_Employee_ID
	
	/**
	 * 	Set C_Employee_ID
	 *	@param C_Employee_ID id
	 */
	protected void setC_Employee_ID (int C_Employee_ID)
	{
		m_C_Employee_ID = C_Employee_ID;
	}	//	setC_Employee_ID

	/**
	 * 	Set C_BPartner_ID
	 *	@param C_BPartner_ID id
	 */
	protected void setC_BPartner_ID (int C_BPartner_ID)
	{
		m_C_BPartner_ID = C_BPartner_ID;
	}	//	setC_BPartner_ID
	
	
	/**
	 * 	Get C_BPartner_Location_ID
	 *	@return C_BPartner_Location_ID
	 */
	public int getC_BPartner_Location_ID()
	{
		int index = p_po.get_ColumnIndex("C_BPartner_Location_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return m_doc.getC_BPartner_Location_ID();
	}	//	getC_BPartner_Location_ID
	
	/**
	 *  Get TrxOrg
	 *  @return AD_OrgTrx_ID
	 */
	public int getAD_OrgTrx_ID()
	{
		int index = p_po.get_ColumnIndex("AD_OrgTrx_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getAD_OrgTrx_ID

	/**
	 *  Get SalesRegion.
	 *  - get Sales Region from BPartner
	 *  @return C_SalesRegion_ID
	 */
	public int getC_SalesRegion_ID()
	{
		if (m_C_SalesRegion_ID == -1)	//	never tried
		{
			if (getC_BPartner_Location_ID() != 0)
			//	&& m_acctSchema.isAcctSchemaElement(MAcctSchemaElement.ELEMENTTYPE_SalesRegion))
			{
				String sql = "SELECT COALESCE(C_SalesRegion_ID,0) FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
				m_C_SalesRegion_ID = DB.getSQLValue (null,
					sql, getC_BPartner_Location_ID());
				if (log.isLoggable(Level.FINE)) log.fine("C_SalesRegion_ID=" + m_C_SalesRegion_ID + " (from BPL)" );
				if (m_C_SalesRegion_ID == 0)
					m_C_SalesRegion_ID = -2;	//	don't try again
			}
			else
				m_C_SalesRegion_ID = -2;		//	don't try again
		}
		if (m_C_SalesRegion_ID < 0)				//	invalid
			return 0;
		return m_C_SalesRegion_ID;
	}   //  getC_SalesRegion_ID

	/**
	 *  Get Project
	 *  @return C_Project_ID
	 */
	public int getC_Project_ID()
	{
		int index = p_po.get_ColumnIndex("C_Project_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_Project_ID

	/**
	 *  Get Project Phase
	 *  @return C_ProjectPhase_ID
	 */
	public int getC_ProjectPhase_ID()
	{
		int index = p_po.get_ColumnIndex("C_ProjectPhase_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_ProjectPhase_ID
	
	/**
	 *  Get Project Task
	 *  @return C_ProjectTask_ID
	 */
	public int getC_ProjectTask_ID()
	{
		int index = p_po.get_ColumnIndex("C_ProjectTask_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_ProjectTask_ID
	
	/**
	 *  Get Campaign
	 *  @return C_Campaign_ID
	 */
	public int getC_Campaign_ID()
	{
		int index = p_po.get_ColumnIndex("C_Campaign_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_Campaign_ID

	/**
	 *  Get Activity
	 *  @return C_Activity_ID
	 */
	public int getC_Activity_ID()
	{
		int index = p_po.get_ColumnIndex("C_Activity_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getC_Activity_ID
	
	/**
	 * 	Get header level A_Asset_ID
	 *	@return A_Asset_ID or 0
	 */
	public int getA_Asset_ID()
	{
		if(m_A_Asset_ID == -1)
		{
			int index = p_po.get_ColumnIndex("A_Asset_ID");
			if (index != -1)
			{
				Integer ii = (Integer) p_po.get_Value(index);
				if (ii != null)
					m_A_Asset_ID = ii.intValue();
			}
			if (m_A_Asset_ID == -1)
				m_A_Asset_ID = 0;
		}
		return m_A_Asset_ID;
	}	//	getA_Asset_ID

	/**
	 * Set A_Asset_ID
	 * @param m_A_Asset_ID Asset
	 */
	public void setA_Asset_ID(int m_A_Asset_ID)
	{
		this.m_A_Asset_ID = m_A_Asset_ID;
	}// setA_Asset_ID
	
	/**
	 * 	Get M_Warehouse_ID
	 *	@return M_Warehouse_ID or 0
	 */
	public int getM_Warehouse_ID()
	{
		int index = p_po.get_ColumnIndex("M_Warehouse_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}	//	getM_Warehouse_ID
	
	/**
	 * Get C_CostCenter_ID
	 * 
	 * @return C_CostCenter_ID or 0
	 */
	public int getC_CostCenter_ID()
	{
		if (m_C_CostCenter_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_CostCenter_ID");
			if (index != -1)
			{
				Integer ii = (Integer) p_po.get_Value(index);
				if (ii != null)
					m_C_CostCenter_ID = ii.intValue();
			}
			if (m_C_CostCenter_ID == -1)
				m_C_CostCenter_ID = 0;
		}
		return m_C_CostCenter_ID;
	}// getC_CostCenter_ID
	
	/**
	 * Set C_CostCenter_ID
	 * 
	 * @param m_C_CostCenter_ID Cost Center
	 */
	public void setC_CostCenter_ID(int m_C_CostCenter_ID)
	{
		this.m_C_CostCenter_ID = m_C_CostCenter_ID;
	}// setC_CostCenter_ID

	/**
	 * Get C_Department_ID
	 * 
	 * @return C_Department_ID or 0
	 */
	public int getC_Department_ID()
	{
		if (m_C_Department_ID == -1)
		{
			int index = p_po.get_ColumnIndex("C_Department_ID");
			if (index != -1)
			{
				Integer ii = (Integer) p_po.get_Value(index);
				if (ii != null)
					m_C_Department_ID = ii.intValue();
			}
			if (m_C_Department_ID == -1)
				m_C_Department_ID = 0;
		}
		return m_C_Department_ID;
	}// getC_Department_ID
	
	/**
	 * Set C_Department_ID
	 * 
	 * @param m_C_Department_ID Department
	 */
	public void setC_Department_ID(int m_C_Department_ID)
	{
		this.m_C_Department_ID = m_C_Department_ID;
	} // setC_Department_ID
	
	/**
	 *  Get user defined id 1
	 *  @return User1_ID
	 */
	public int getUser1_ID()
	{
		int index = p_po.get_ColumnIndex("User1_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getUser1_ID

	/**
	 *  Get user defined id 2
	 *  @return User2_ID
	 */
	public int getUser2_ID()
	{
		int index = p_po.get_ColumnIndex("User2_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getUser2_ID
        
    /**
	 *  Get column value
	 *  @param ColumnName column name
	 *  @return column value or 0 (if column doesn't exist)
	 */
	public int getValue(String ColumnName)
	{
		int index = p_po.get_ColumnIndex(ColumnName);
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getValue
	
    /**
	 * Get value by column name
	 * @param ColumnName
	 * @return column value or null (if column doesn't exists)
	 */
	public String get_ValueAsString (String ColumnName)
	{
		int index = p_po.get_ColumnIndex(ColumnName);
		if (index != -1)
		{
			return p_po.get_ValueAsString(index);
		}
		return null;
	}	//	get_ValueAsString

	//AZ Goodwill
	private int         		m_ReversalLine_ID = 0;
	
	/**
	 *  Set ReversalLine_ID
	 *  store original (voided/reversed) document line
	 *  @param ReversalLine_ID
	 */
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		m_ReversalLine_ID = ReversalLine_ID;
	}   //  setReversalLine_ID

	/**
	 *  Get ReversalLine_ID
	 *  get original (voided/reversed) document line
	 *  @return ReversalLine_ID
	 */
	public int getReversalLine_ID()
	{
		return m_ReversalLine_ID;
	}   //  getReversalLine_ID
	//end AZ Goodwill
	
	/**
	 * @return line PO
	 */
	public PO getPO() 
	{
		return p_po;
	}
	
	/**
	 *  String representation
	 *  @return String
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("DocLine=[");
		sb.append(p_po.get_ID());
		if (getDescription() != null)
			sb.append(",").append(getDescription());
		if (getM_Product_ID() != 0)
			sb.append(",M_Product_ID=").append(getM_Product_ID());
		sb.append(",Qty=").append(m_qty)
			.append(",Amt=").append(getAmtSource())
			.append("]");
		return sb.toString();
	}	//	toString

}	//	DocumentLine
