package org.adempiere.webui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.http.SimpleUiFactory;
import org.zkoss.zk.ui.metainfo.PageDefinition;
import org.zkoss.zk.ui.sys.RequestInfo;

/**
 * https://www.zkoss.org/wiki/ZK_Developer%27s_Reference/Customization/UI_Factory
 * https://www.zkoss.org/wiki/ZK_Configuration_Reference/zk.xml/The_system-config_Element/The_ui-factory-class_Element
 * @author hieplq
 *
 */
public class ThemeInheritUiFactory extends SimpleUiFactory {
	public static String defaultThemePath = "default";
	public static String prefixThemePath = "~./theme/";
	public static String prefixDefaultThemePath = prefixThemePath + defaultThemePath + "/";
	
	private static final CLogger logger = CLogger.getCLogger(ThemeInheritUiFactory.class.getName());

	@Override
    public PageDefinition getPageDefinition(RequestInfo ri, String path) {
		PageDefinition pageDefine = super.getPageDefinition(ri, path);

		StringBuilder logMsg = null;
		
		if (pageDefine == null)
			logMsg = new StringBuilder();
		
		if (pageDefine == null
				&& path != null
				&& path.startsWith(prefixThemePath)
				&& !path.startsWith(prefixDefaultThemePath)
				) {

			int themeNameLastIndex = path.indexOf("/", prefixThemePath.length());
			String theme = path.substring(prefixThemePath.length(), themeNameLastIndex);
			String themeLastPart = path.substring(themeNameLastIndex);

			logMsg.append("can't lookup resource \"").append(themeLastPart).append("\" in theme \"").append(theme).append("\"");			
			
			List<String> themeParrents = getThemeParrents(theme);
			
			for (String parent : themeParrents) {
				String parentPath = prefixThemePath + parent + themeLastPart;
				pageDefine = super.getPageDefinition(ri, parentPath);
				if (pageDefine != null) {
					logMsg.append("\n	but can find out on parent theme \"").append(parent).append("\"");	
					break;
				}
			}
		}else if (pageDefine == null && !path.startsWith(prefixThemePath)) {
			logMsg.append("follow IDEMPIERE-5013 then resource need to use absolute path start with \"~.\"");
		}
		
		if (logMsg != null && logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, logMsg.toString());
		}
		
		return pageDefine;
	}
	
	private static CCache<String, List<String>> cacheThemeParrents = new CCache<>(ThemeInheritUiFactory.class.getName(), 
			1000, CCache.DEFAULT_EXPIRE_MINUTE, false);
	
	public static List<String> getThemeParrents (String theme){
		List<String> themeParrents = null;
		synchronized(cacheThemeParrents) {
			themeParrents = cacheThemeParrents.get(theme);
			if (themeParrents == null){
				themeParrents = new ArrayList<>();
				themeParrents.add("default");
				cacheThemeParrents.put(theme, themeParrents);
			}
		}
		return themeParrents;		
	}
}
