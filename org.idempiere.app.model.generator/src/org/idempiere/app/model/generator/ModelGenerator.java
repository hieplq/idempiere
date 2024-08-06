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
package org.idempiere.app.model.generator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.adempiere.util.ModelClassGenerator;
import org.adempiere.util.ModelGeneratorDialog;
import org.adempiere.util.ModelInterfaceGenerator;
import org.compiere.Adempiere;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true)
public class ModelGenerator {
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
			stop();
		}
	}

	private void start(ComponentContext context, String[] args) {
		Adempiere.startup(false);
		// IDEMPIERE-1686 - GenerateModel does not take commandline arguments
		String commandlineArgs[] = args != null ? args : new String[0];
		if (commandlineArgs.length >= 4) {
			String folder = commandlineArgs[0];
			String packageName = commandlineArgs[1];
			String entityType = commandlineArgs[2];
			String tableName = commandlineArgs[3];
			String columnEntityType = null;
			if (commandlineArgs.length >= 5)
				columnEntityType = commandlineArgs[4];
			ModelInterfaceGenerator.generateSource(folder, packageName, entityType, tableName, columnEntityType);
			ModelClassGenerator.generateSource(folder, packageName, entityType, tableName, columnEntityType);
		} else if (commandlineArgs.length != 0) {
			System.out.println("usage: ModelGenerator folder packageName tableEntityType tableName columnEntityType");
		} else {
			ModelGeneratorDialog dialog = new ModelGeneratorDialog();
			dialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					stop();
				}

			});
			//dialog.setModal(true);
			dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dialog.pack();
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);

		}
	}

	protected void stop() {
		System.exit(0);
	}
}
