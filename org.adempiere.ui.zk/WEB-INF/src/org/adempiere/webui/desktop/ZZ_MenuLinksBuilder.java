package org.adempiere.webui.desktop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import org.adempiere.webui.component.ToolBarButton;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.MDashboardContent;
import org.compiere.util.DB;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Style;
import org.zkoss.zul.Vlayout;

public final class ZZ_MenuLinksBuilder {

    private ZZ_MenuLinksBuilder() {}

    /** Build a vertical list from explicit menu IDs */
    public static Vlayout fromIds(
            MDashboardContent dashboardContent,
            Collection<Integer> menuIds,
            EventListener<Event> clickListener,
            String marginCss // e.g. "260px 0 0 0"
    ) {
        Vlayout list = baseList(marginCss);
        for (int id : menuIds) {
            I_AD_Menu menu = dashboardContent.getAD_Menu(id);
            if (menu == null) continue;
            list.appendChild(makeButton(id, menu.getName(), clickListener));
        }
        return list;
    }

    /** Build a vertical list from a DB query (adjust WHERE as needed) */
    public static Vlayout fromQuery(
            MDashboardContent dashboardContent,
            EventListener<Event> clickListener,
            String marginCss
    ) {
        Vlayout list = baseList(marginCss);
        
        /*
        String sql =
            "SELECT m.AD_Menu_ID " +
            "FROM AD_Menu m " +
            "JOIN AD_Form f ON m.AD_Form_ID = f.AD_Form_ID " +
            "WHERE m.IsActive='Y' AND m.IsSummary='N' " +
            "  AND f.AD_Form_ID = 1000000 " +
            "ORDER BY m.Name";
            */
        String sql =
        	    "WITH open_apps AS (" +
        	    "    SELECT DISTINCT TRIM(both ' ' FROM x)::NUMERIC AS zz_program_master_data_id " +
        	    "    FROM adempiere.zz_open_application oa " +
        	    "         CROSS JOIN LATERAL unnest(string_to_array(oa.zz_programs, ',')) AS t(x) " +
        	    "    WHERE oa.isactive = 'Y' " +
        	    "      AND now() BETWEEN oa.startdate AND oa.enddate " +
        	    "      AND oa.zz_programs IS NOT NULL " +
        	    "), " +
        	    "program_uu AS ( " +
        	    "    SELECT p.zz_program_master_data_uu " +
        	    "    FROM adempiere.zz_program_master_data p " +
        	    "    JOIN open_apps a ON a.zz_program_master_data_id = p.zz_program_master_data_id " +
        	    "    WHERE p.isactive = 'Y' " +
        	    "      AND p.zz_program_master_data_uu IS NOT NULL " +
        	    "), " +
        	    "menus AS ( " +
        	    "    SELECT m.ad_menu_id, m.name, m.predefinedcontextvariables " +
        	    "    FROM adempiere.ad_menu m " +
        	    "    JOIN adempiere.ad_form f ON f.ad_form_id = m.ad_form_id " +
        	    "    WHERE m.isactive = 'Y' " +
        	    "      AND m.issummary = 'N' " +
        	    "      AND f.ad_form_id = 1000000 " +
        	    ") " +
        	    "SELECT DISTINCT m.ad_menu_id " +
        	    "FROM menus m " +
        	    "JOIN program_uu u " +
        	    "  ON m.predefinedcontextvariables ILIKE ('%' || 'ZZ_Program_Master_Data_UU=' || u.zz_program_master_data_uu || '%') " +
        	    "ORDER BY m.ad_menu_id";


        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DB.prepareStatement(sql, null);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                I_AD_Menu menu = dashboardContent.getAD_Menu(id);
                if (menu == null) continue;
                list.appendChild(makeButton(id, menu.getName(), clickListener));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.close(rs, ps);
        }

        return list;
    }

    

    private static Vlayout baseList(String marginCss) {
        Vlayout list = new Vlayout();
        list.setSpacing("2px");
        list.setSclass("menu-links");  // <-- key for the selector
        list.setStyle("margin:" + (marginCss == null ? "0" : marginCss) + ";align-items:flex-start;");

        // Inject CSS for left alignment
        Style st = new Style();
        st.setContent(
            "/* fallback, extra-specific */\n" +
            ".menu-links .z-vlayout-inner .z-toolbarbutton .z-toolbarbutton-content{" +
            "justify-content:flex-start!important;}"
        );
        list.appendChild(st);

        return list;
    }



    private static ToolBarButton makeButton(int id, String label, EventListener<Event> clickListener) {
        ToolBarButton btn = new ToolBarButton(String.valueOf(id));
        btn.setLabel(label);
        btn.setAttribute("AD_Menu_ID", id);
        btn.addEventListener(Events.ON_CLICK, clickListener);
        btn.setStyle(
        	    "display:block;"
        	  + "margin:0;"
        	  + "padding:2px 8px;"
        	  + "text-align:left !important;"     // <-- add !important
        	  + "line-height:1.1;"
        	  + "font-size:18px !important;"
        	  + "color:#fff !important;"
        	

        	);
        	// Optional: avoid stretching if your theme gives it full width
        	// btn.setHflex("min");

        // Optional: make the button only as wide as its text
        btn.setHflex("min");
        return btn;
    }

}

