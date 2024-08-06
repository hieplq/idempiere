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
package org.idempiere.app.install;

import org.compiere.install.InstallApplication;
import org.compiere.install.console.ConsoleInstallApplication;
import org.compiere.install.console.SilentInstallApplication;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class InstallService {
	@Activate
	void activate(ComponentContext context) {
		String[] args = new String[0];
		//get command line arguments
		ServiceReference<?> serviceRef = context.getBundleContext().getServiceReference("aQute.launcher.Launcher");
		if (serviceRef != null) {
			args = (String[]) serviceRef.getProperties().get("launcher.arguments");
		}
		try {
			start(context, args);
		} catch (Throwable t) {
			t.printStackTrace();
			stop();
		}
	}

	private void start(ComponentContext context, String[] args) throws Exception {
		String mode = System.getProperty("setup.mode", "gui");

		if ("console".equalsIgnoreCase(mode)) {
			ConsoleInstallApplication console = new ConsoleInstallApplication();
			console.run(args);
		} else if ("silent".equalsIgnoreCase(mode)) {
			SilentInstallApplication silent = new SilentInstallApplication();
			silent.run(args);
		} else {
			InstallApplication gui = new InstallApplication();
			gui.run(args);
		}
		stop();
	}

	protected void stop() {
		System.exit(0);
	}
}
