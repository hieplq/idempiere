/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2010 Heng Sin Low                							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.install.console;

import java.io.File;
import java.util.logging.Level;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;

/**
 * @author hengsin
 *
 */
public class ConsoleInstallApplication {

	/**
	 * Run console setup
	 * @param args
	 * @throws Exception
	 */
	public void run(String[] args) throws Exception {
		CLogMgt.initialize(false);

		//	Log Level
		Level logLevel = Level.INFO;
		if (args.length > 0) {
			try {
				logLevel = Level.parse(args[0]);
			} catch (IllegalArgumentException e) {
				CLogger.get().warning("Unrecognized log level: " + args[0] + " defaulting to: " + logLevel);
			}
		}
		CLogMgt.setLevel(logLevel);

		ConfigurationConsole console = new ConfigurationConsole();
		console.doSetup();
		String path = System.getProperty("user.dir");
		if (path.endsWith("org.idempiere.app.install")) {
			path = path.substring(0, path.length() - "org.idempiere.app.install".length())
					+ "org.adempiere.install/build.xml";
		} else {
			path = path + "/org.adempiere.install/build.xml";
		}
		File file = new File(path);
		System.out.println("file="+path+" exists="+file.exists());
		//only exists if it is running from development environment
		if (file.exists()) {
			Project project = new Project();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			project.addReference("ant.projectHelper", helper);
			helper.parse(project, file);
			DefaultLogger logger = new DefaultLogger();
			logger.setOutputPrintStream(System.out);
			logger.setErrorPrintStream(System.err);
			logger.setMessageOutputLevel(Project.MSG_VERBOSE);
			project.addBuildListener(logger);
			project.executeTarget(project.getDefaultTarget());
		}
	}
}
