package org.adempiere.webui.desktop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.zkoss.zk.ui.Component;

public final class WindowRegistry {

    private static final String ATTR_MENU_MAP = "NTIER_MENU_WINDOW_MAP";
    private static final String ATTR_APP_MAP  = "NTIER_APP_WINDOW_MAP";

    private WindowRegistry() {}

    @SuppressWarnings("unchecked")
    public static Map<Integer, Integer> menuMap(DefaultDesktop desktop) {
        Component root = desktop.getComponent();
        Map<Integer, Integer> map = (Map<Integer, Integer>) root.getAttribute(ATTR_MENU_MAP);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            root.setAttribute(ATTR_MENU_MAP, map);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Map<Object, Integer> appMap(DefaultDesktop desktop) {
        Component root = desktop.getComponent();
        Map<Object, Integer> map = (Map<Object, Integer>) root.getAttribute(ATTR_APP_MAP);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            root.setAttribute(ATTR_APP_MAP, map);
        }
        return map;
    }

    public static boolean isWindowAlive(DefaultDesktop desktop, Integer winNo) {
        if (winNo == null) return false;
        Object winObj = desktop.findWindow(winNo);
        if (!(winObj instanceof Component c)) return false;
        return c.getDesktop() != null && c.getPage() != null;
    }

    public static Integer getWindowNo(Component win) {
        Object winNoAttr = win.getAttribute(IDesktop.WINDOWNO_ATTRIBUTE);
        return (winNoAttr instanceof Integer) ? (Integer) winNoAttr : null;
    }
}

