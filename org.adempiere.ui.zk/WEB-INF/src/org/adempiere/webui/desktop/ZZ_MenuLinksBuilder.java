package org.adempiere.webui.desktop;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import org.adempiere.webui.component.ToolBarButton;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.MDashboardContent;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Style;
import org.zkoss.zul.Vlayout;

public final class ZZ_MenuLinksBuilder {

    private static final CLogger log = CLogger.getCLogger(ZZ_MenuLinksBuilder.class);

    private ZZ_MenuLinksBuilder() {}

    // ============================================================
    // NEW PUBLIC ENTRY: add header + sticky menu in one call
    // ============================================================
    /**
     * Creates and adds the header panel (year/title/date range) and the fixed,
     * scrollable menu to the provided components list. Minimal controller code.
     */
    public static void attachHeaderAndMenu(List<Component> components,
                                           MDashboardContent dashboardContent,
                                           EventListener<Event> clickListener) {

        // 1) Header first (added to components)
        Div header = buildHeaderPanel();
        components.add(header);

        // 2) Menu list (query + buttons)
        Vlayout list = fromQuery(dashboardContent, clickListener, "0");
        list.setSclass("menu-links");
        list.setStyle("overflow-y:auto;margin:0;");

        // 3) Fixed wrapper around list + CSS
        Div fixed = new Div();
        fixed.setSclass("zz-fixedmenu");
        fixed.appendChild(list);

        // Keep a CSS var for top; it will be adjusted in JS below (fallback 280px)
        //fixed.setStyle("--menuTop:280px;");
        fixed.setId("zzFixedMenu");

     
        
        Style css = new Style();
        css.setContent(
        	    ".zz-fixedmenu{position:fixed; top:var(--menuTop, 280px); left:60px !important; z-index:2000;}" +
        	    ".zz-fixedmenu .menu-links{max-height:calc(100vh - var(--menuTop, 280px) - 12px); overflow-y:auto;}" +
        	    ".dashboard-widget .z-panelchildren{overflow:visible!important; padding-top:0!important;}" +
        	    ".dashboard-widget.dashboard-widget-max .z-panelchildren{overflow:visible!important; padding-top:0!important;}" +
        	    ".zz-headerwrap{position:relative; transform:translateY(-76px);}"
        	);


        fixed.appendChild(css);




        components.add(fixed);

        
     // AFTER you add `header` and `fixed` to components:
        components.add(header);
        components.add(fixed);

        // Install the auto top calculation safely
        installMenuTopAutoCalc(header); // or install on 'fixed' — either works since both are on the page
        
     // --- Bottom-right "Funding Policy" link ---
        Div policyFooter = new Div();
        policyFooter.setId("zzPolicyFooter");
        
        policyFooter.setStyle(
        	    "position:fixed; right:72px; bottom:24px; z-index:2000;" + // was right:24px
        	    "display:flex; align-items:center; gap:8px;"
        	);


        // Optional caption to match the spec

        // The hyperlink
        A fundingLink = new A("Funding Policy");
        fundingLink.setStyle("text-decoration: underline; cursor: pointer; font-weight:600; color: #2d2c72;");

        fundingLink.addEventListener(Events.ON_CLICK, ev -> downloadFundingPolicy());

        // Compose and add to the page
        policyFooter.appendChild(fundingLink);
        components.add(policyFooter);


    }

    // ============================================================
    // HEADER BUILDER (year/title/dates)
    // ============================================================
    private static Div buildHeaderPanel() {
        HeaderData data = fetchHeaderData();

        Div wrapper = new Div();
        wrapper.setId("zzHeaderPanel");  
        wrapper.setSclass("zz-headerwrap");  
       
        
        wrapper.setStyle(
        	    "background: transparent;" +          // was the dark gradient
        	    "padding:24px 28px;" +                // keep your spacing
        	    "border-radius:16px;" +               // harmless with transparent bg
        	    "margin:0 0 16px 0;" +
        	    "box-shadow:none;" +                  // remove dark bar look
        	    "color:#fff;"
        	);


        Vlayout v = new Vlayout();
        v.setSpacing("2px");
        wrapper.appendChild(v);

        // Line 1: Year (C_Year.description)
        Label lblYear = new Label(data.yearText == null || data.yearText.isBlank() ? "—" : data.yearText.trim());
        //lblYear.setStyle("display:block;font-size:48px;font-weight:800;letter-spacing:1px;line-height:1.0;margin:0 0 6px 0;");  Not so white
        lblYear.setStyle(
        	    "display:block;font-size:48px;font-weight:800;letter-spacing:1px;line-height:1.0;margin:0 0 6px 0;"
        	  + "color:rgba(255,255,255,0.96);"
        	  + "text-shadow:0 1px 2px rgba(0,0,0,.55);"
        	);
        v.appendChild(lblYear);

        // Line 2: Orange title (ZZ_Menu_Title)
        String title = (data.menuTitle == null || data.menuTitle.isBlank())
                ? "DISCRETIONARY GRANT APPLICATIONS" : data.menuTitle.trim();
        Label lblTitle = new Label(title);
        lblTitle.setStyle("display:block;font-size:36px;font-weight:900;text-transform:uppercase;line-height:1.0;color:#ff6a00;margin:0 0 8px 0;");
        v.appendChild(lblTitle);

        // Line 3: "1st Window | dd MMMM yyyy - dd MMMM yyyy"
        String windowLine = data.window + " Window | —";
        if (data.start != null && data.end != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM uuuu");
            windowLine = data.window + " Window | " + fmt.format(data.start) + " - " + fmt.format(data.end);
        }
        Label lblWindow = new Label(windowLine);
        //lblWindow.setStyle("display:block;font-size:18px;font-weight:500;opacity:0.9;letter-spacing:0.3px;margin:0;");
        lblWindow.setStyle(
        	    "display:block;font-size:18px;font-weight:600;letter-spacing:0.3px;margin:0;"
        	  + "color:rgba(255,255,255,0.92);"
        	  + "text-shadow:0 1px 2px rgba(0,0,0,.5);"
        	);
        v.appendChild(lblWindow);

        return wrapper;
    }

    private static class HeaderData {
    	String window;  // 1st ,2nd , 3rd ...
        String yearText;
        String menuTitle;
        LocalDate start;
        LocalDate end;
    }

    private static HeaderData fetchHeaderData() {
        HeaderData h = new HeaderData();
        final String sql =
              "SELECT y.description AS year_desc, "
            + "       oa.zz_menu_title, "
            + "       oa.startdate::date AS start_date, "
            + "       oa.enddate::date   AS end_date ,"
            + "       oa.ZZ_Window "
            + "FROM adempiere.zz_open_application oa "
            + "JOIN adempiere.c_year y ON y.c_year_id = oa.c_year_id "
            + "WHERE oa.isactive = 'Y' "
            + "  AND oa.zz_docstatus = 'AP' "
            + "  AND now() BETWEEN oa.startdate AND oa.enddate "
            + "ORDER BY oa.startdate DESC "
            + "LIMIT 1";
        try (PreparedStatement ps = DB.prepareStatement(sql, null);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                h.yearText  = rs.getString("year_desc");
                h.menuTitle = rs.getString("zz_menu_title");
                java.sql.Date sd = rs.getDate("start_date");
                java.sql.Date ed = rs.getDate("end_date");
                if (sd != null) h.start = sd.toLocalDate();
                if (ed != null) h.end   = ed.toLocalDate();
                h.window = rs.getString("ZZ_Window");
            }
        } catch (Exception e) {
            log.warning("Failed to load header data: " + e.getMessage());
        }
        return h;
    }

    // ============================================================
    // EXISTING PUBLIC BUILDERS (unchanged behavior)
    // ============================================================
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

        String sql =
              "WITH open_apps AS ( "
            + "    SELECT DISTINCT oa.ad_org_id, oa.documentno, TRIM(both ' ' FROM x)::NUMERIC AS zz_program_master_data_id "
            + "    FROM adempiere.zz_open_application oa "
            + "    CROSS JOIN LATERAL unnest(string_to_array(oa.zz_programs, ',')) AS t(x) "
            + "    WHERE oa.isactive = 'Y' "
            + "      AND now() BETWEEN oa.startdate AND oa.enddate "
            + "      AND oa.zz_docstatus = 'AP' "
            + "      AND oa.zz_programs IS NOT NULL "
            + "), "
            + "program_uu AS ( "
            + "    SELECT p.zz_program_master_data_uu, a.ad_org_id, a.documentno "
            + "    FROM adempiere.zz_program_master_data p "
            + "    JOIN open_apps a ON a.zz_program_master_data_id = p.zz_program_master_data_id "
            + "    WHERE p.isactive = 'Y' "
            + "      AND p.zz_program_master_data_uu IS NOT NULL "
            + "), "
            + "menus AS ( "
            + "    SELECT m.ad_menu_id, m.name, m.predefinedcontextvariables "
            + "    FROM adempiere.ad_menu m "
            + "    JOIN adempiere.ad_form f ON f.ad_form_id = m.ad_form_id "
            + "    WHERE m.isactive = 'Y' "
            + "      AND m.issummary = 'N' "
            + "      AND f.ad_form_id = 1000000 "
            + ") "
            + "SELECT ad_menu_id "
            + "FROM ( "
            + "    SELECT DISTINCT ON (m.ad_menu_id) "
            + "           m.ad_menu_id, "
            + "           m.name, "
            + "           CASE WHEN m.ad_menu_id = 1000072 THEN 0 ELSE 1 END AS sort_top, "
            + "           ao.name AS org_name, "
            + "           u.documentno AS docno "
            + "    FROM menus m "
            + "    LEFT JOIN program_uu u "
            + "      ON m.predefinedcontextvariables ILIKE ('%' || 'ZZ_Program_Master_Data_UU=' || u.zz_program_master_data_uu || '%') "
            + "    LEFT JOIN adempiere.ad_org ao ON ao.ad_org_id = u.ad_org_id "
            + "    WHERE u.zz_program_master_data_uu IS NOT NULL "
            + "       OR m.ad_menu_id = 1000072 "
            + "    ORDER BY m.ad_menu_id, ao.name, u.documentno DESC NULLS LAST "
            + ") s "
            + "ORDER BY sort_top, "
            + "         name ASC, "
            + "         org_name ASC, "
            + "         docno DESC NULLS LAST, "
            + "         ad_menu_id";

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
        list.setSclass("menu-links");
        list.setStyle("margin:" + (marginCss == null ? "0" : marginCss) + ";align-items:flex-start;");

        // CSS: prevent wrappers/buttons from stretching full width
        Style st = new Style();
        st.setContent(
            ".menu-links .z-vlayout-inner{width:auto !important;}"
          + ".menu-links .z-toolbarbutton{width:auto !important;display:inline-block;}"
          + ".menu-links .z-toolbarbutton .z-toolbarbutton-content{justify-content:flex-start!important;}"
        );
        list.appendChild(st);

        // Run the width-equalizing JS only on the UI thread, after attachment.
        list.addEventListener(Events.ON_CREATE, ev -> runFixMenuLinkWidths((Vlayout) ev.getTarget()));
        list.addEventListener(Events.ON_AFTER_SIZE, ev -> runFixMenuLinkWidths((Vlayout) ev.getTarget()));

        return list;
    }

    /** UI-thread-only: measure max width and set all buttons to that width */
    private static void runFixMenuLinkWidths(Vlayout list) {
        // If not on a UI execution yet, try scheduling on the Desktop
        if (Executions.getCurrent() == null) {
            if (list.getDesktop() != null) {
                Executions.schedule(list.getDesktop(), e -> runFixMenuLinkWidths(list),
                        new org.zkoss.zk.ui.event.Event("onFixMenuLinkWidths", list));
            }
            return;
        }

        final String uuid = list.getUuid();
        final String js =
            "(function(){\n"
          + "  var w = zk.Widget.$('$" + uuid + "'); if(!w) return;\n"
          + "  var n = w.$n(); if(!n) return;\n"
          + "  var btns = n.querySelectorAll('.z-toolbarbutton'); if(!btns.length) return;\n"
          + "  var max = 0;\n"
          + "  btns.forEach(function(b){ b.style.width=''; var rw=b.getBoundingClientRect().width; if(rw>max) max=rw;});\n"
          + "  btns.forEach(function(b){ b.style.width = max + 'px'; });\n"
          + "})();";

        Clients.evalJavaScript(js);
    }

    private static ToolBarButton makeButton(int id, String label, EventListener<Event> clickListener) {
        ToolBarButton btn = new ToolBarButton(String.valueOf(id));
        btn.setLabel(label);
        btn.setAttribute("AD_Menu_ID", id);
        btn.addEventListener(Events.ON_CLICK, clickListener);

        // default style
        String style =
              "display:inline-block;"
            + "margin:0;"
            + "padding:2px 8px;"
            + "text-align:left !important;"
            + "line-height:1.1;"
            + "font-size:18px !important;"
            + "color:#fff !important;";

        // If it's "My Applications", override to orange + bold
        if ("My Applications".equalsIgnoreCase(label)) {
            style += "color:#F27127 !important;font-weight:800;";
        }

        btn.setStyle(style);
        return btn;
    }
    
    private static void installMenuTopAutoCalc(org.zkoss.zul.Div hook) {
        final String INIT_JS =
            "(function(){"
          + "  function alignMenu(){"
          + "    var header = document.getElementById('zzHeaderPanel');"
          + "    var fixed  = document.querySelector('.zz-fixedmenu');"
          + "    if(!fixed) return;"
          + "    var top = 280;"
          + "    var desiredLeft = 0;"
          + "    if(header){"
          + "      var hr = header.getBoundingClientRect();"
          + "      top = hr.bottom + 12;"
          + "      // header text left: first label inside the header"
          + "      var firstHeaderLabel = header.querySelector('.z-label');"
          + "      var headerTextLeft = firstHeaderLabel ? firstHeaderLabel.getBoundingClientRect().left : hr.left;"
          + "      // menu text left: first menu button inner content"
          + "      var firstBtnContent = fixed.querySelector('.z-toolbarbutton .z-toolbarbutton-content');"
          + "      var innerOffset = 0;"
          + "      if(firstBtnContent){"
          + "        // measure how far the inner text is from the fixed wrapper's left"
          + "        var fr = fixed.getBoundingClientRect();"
          + "        var br = firstBtnContent.getBoundingClientRect();"
          + "        innerOffset = br.left - fr.left;"
          + "      }"
          + "      // optional small gutter to move slightly more right (adjust as needed)"
          + "      var tweak = 6;  /* try 6–12 if you want more */"
          + "      desiredLeft = Math.max(0, Math.round(headerTextLeft - innerOffset + tweak));"
          + "    }"
          + "    fixed.style.setProperty('--menuTop', top + 'px');"
          + "    fixed.style.left = desiredLeft + 'px';"
          + "  }"
          + "  if(!window.__zzMenuAlignInstalled){"
          + "    window.__zzMenuAlignInstalled = true;"
          + "    window.addEventListener('resize', alignMenu, {passive:true});"
          + "    window.addEventListener('scroll', alignMenu, {passive:true});"
          + "  }"
          + "  alignMenu();"
          + "})();";

        hook.addEventListener(org.zkoss.zk.ui.event.Events.ON_CREATE,
            ev -> org.zkoss.zk.ui.util.Clients.evalJavaScript(INIT_JS));

        hook.addEventListener(org.zkoss.zk.ui.event.Events.ON_AFTER_SIZE,
            ev -> org.zkoss.zk.ui.util.Clients.evalJavaScript(
                "(function(){ if(window.__zzMenuAlignInstalled){ var e=new Event('resize'); window.dispatchEvent(e);} })();"
            ));

        if (org.zkoss.zk.ui.Executions.getCurrent() == null && hook.getDesktop() != null) {
            org.zkoss.zk.ui.Executions.schedule(
                hook.getDesktop(),
                e -> org.zkoss.zk.ui.util.Clients.evalJavaScript(INIT_JS),
                new org.zkoss.zk.ui.event.Event("onCreate", hook)
            );
        }
    }

    
    private static void downloadFundingPolicy() {
        final String path = "/WEB-INF/mqa/Approved Funding Policy - 2025-2026.pdf"; // adjust name if needed
        try (InputStream is = Executions.getCurrent()
                .getDesktop().getWebApp().getResourceAsStream(path)) {

            if (is == null) {
                Clients.showNotification("Funding Policy file not found.", "warning", null, "top_center", 3000);
                return;
            }

            // Buffer to memory so ZK can serve after the event returns
            byte[] bytes = is.readAllBytes(); // Java 9+
            AMedia media = new AMedia("Approved Funding Policy - 2025-2026.pdf", "pdf", "application/pdf", bytes);
            Filedownload.save(media);

        } catch (Exception e) {
            Clients.showNotification("Unable to open Funding Policy: " + e.getMessage(), "error",
                    null, "top_center", 3500);
        }
    }



}


