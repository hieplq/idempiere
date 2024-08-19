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
package org.idempiere.app.packin.folder;

import org.adempiere.base.PackInFolderApplication;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class PackInFolder {
	@Activate
	void activate(ComponentContext context) {
		String[] args = null;
		//get command line arguments
		ServiceReference<?> serviceRef = context.getBundleContext().getServiceReference("aQute.launcher.Launcher");
		if (serviceRef != null) {
			args = (String[]) serviceRef.getProperties().get("launcher.arguments");
		}
		try {
			start(context, args);
		} catch (Throwable t) {
			stop(1);
		}
	}

	private void start(ComponentContext context, String[] args) throws Exception {
		PackInFolderApplication app = new PackInFolderApplication();
		int exitCode = app.start(args);
		stop(exitCode);
	}

	protected void stop(int exitCode) {
		System.exit(exitCode);
	}
}
