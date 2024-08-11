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
package org.adempiere.payment.processor;

import java.io.Serializable;

import org.compiere.model.PaymentProcessor;

/**
 * 	Authorize.net Payment Processor Services Interface
 *	
 *  @author Jorg Janke
 *  @version $Id$
 */
public class PP_Authorize extends PaymentProcessor
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4010515114494556626L;
	/**	Status					*/
	private boolean		m_ok = false;

	/**
	 * 	Process CC
	 *	@return processed ok
	 *	@throws IllegalArgumentException
	 */
	public boolean processCC ()
		throws IllegalArgumentException
	{
		setEncoded(true);
		return m_ok;
	}	//	processCC

	/**
	 * 	Is Processed OK
	 *	@return true if OK
	 */
	public boolean isProcessedOK ()
	{
		return m_ok;
	}	//	isProcessedOK
	
}	//	PP_Authorize
