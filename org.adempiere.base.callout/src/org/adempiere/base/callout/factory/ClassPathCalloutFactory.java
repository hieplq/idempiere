/***********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 **********************************************************************/
package org.adempiere.base.callout.factory;

import java.lang.reflect.Method;

import org.adempiere.base.ICalloutFactory;
import org.compiere.model.Callout;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = ICalloutFactory.class, property = {"service.ranking:Integer=1"})
public class ClassPathCalloutFactory implements ICalloutFactory {

	@Override
	public Callout getCallout(String className, String methodName) {
		Class<?> calloutClass = null;
		// legacy package name mapping - move to org.adempiere.base.callout to fix split package  
		if (className.startsWith("org.compiere.model"))
			className = "org.adempiere.base.callout" + className.substring("org.compiere.model".length());
		else if (className.startsWith("org.adempiere.model"))
			className = "org.adempiere.base.callout" + className.substring("org.adempiere.model".length());
		ClassLoader classLoader = this.getClass().getClassLoader();
		try
		{
			calloutClass = classLoader.loadClass(className);
		}
		catch (ClassNotFoundException ex)
		{}

		if (calloutClass == null) {
			return null;
		}

		//Get callout
		Callout callout = null;
		try
		{
			callout = (Callout)calloutClass.getDeclaredConstructor().newInstance();
		}
		catch (Exception ex)
		{
			return null;
		}

		//Check if callout method does really exist
		Method[] methods = calloutClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
	        if (methods[i].getName().equals(methodName)) {
	        	return callout;
	        }
		}
		return null;
	}
}
