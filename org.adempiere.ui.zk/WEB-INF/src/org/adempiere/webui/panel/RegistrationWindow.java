package org.adempiere.webui.panel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.adempiere.webui.window.FDialog;
import org.compiere.model.MClient;
import org.compiere.model.MMailText;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 * RegistrationWindow - pure Java ZK Window that handles:
 * - User self-registration
 * - Email OTP (via R_MailText template)
 * - Welcome / temp-password email (via R_MailText template)
 * Validation errors are thrown as IllegalArgumentException with Msg.getMsg(...)
 */
public class RegistrationWindow extends Window implements org.zkoss.zk.ui.event.EventListener<Event> {

    private static final long serialVersionUID = 1L;
    private static final CLogger log = CLogger.getCLogger(RegistrationWindow.class);

    // ---- Mail template names (R_MailText.Name) ----
    private static final String OTP_MAIL_TEXT_NAME     = "REGISTRATION_OTP";
    private static final String WELCOME_MAIL_TEXT_NAME = "REGISTRATION_WELCOME";

    // ---- Defaults: change to your IDs ----
    private static final int DEFAULT_CLIENT_ID = 1000000; // <-- set your AD_Client_ID
    private static final int DEFAULT_ROLE_ID   = 1000023; // <-- set a default AD_Role_ID

    // ---- UI ----
    private Textbox txtName;
    private Textbox txtIDNo;
    private Textbox txtPassportNo;
    private Textbox txtCellNo;
    private Textbox txtEmail;
    private Textbox txtOtp;
    private Button  btnSendOtp;
    private Button  btnRegisterUser;

    public RegistrationWindow() {
        setTitle(Msg.getMsg(Env.getCtx(), "UserRegistrationTitle")); // AD_Message
        setWidth("700px");
        setClosable(true);
        setSizable(false);
        setBorder("normal");
        setId("registrationWindow");

        buildUI();
        wireEvents();
    }
    
    /*

    
    private void buildUI() {
        
        Vbox form = new Vbox();
        form.setSpacing("6px");
       // form.setWidth("100%");     // already present
        form.setHflex("1");  
        
        
     // Registration form components 
        Textbox txtName = new Textbox(); 
        txtName.setPlaceholder("Full Name"); 
        // txtName.setWidth("300px"); 
        //txtName.setHflex("1"); 
       // Hbox nameBox = new Hbox(); 
       // nameBox.setSpacing("5px"); 
       // nameBox.appendChild(txtName); 
      //  nameBox.setHflex("2");
        //txtName.setWidth("610px");
        txtName.setWidth("4");
        


        txtIDNo = new Textbox();
        txtIDNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "IDNumber"));
        txtIDNo.setHflex("1");

        txtPassportNo = new Textbox();
        txtPassportNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "PassportNumber"));
        txtPassportNo.setHflex("1");

        txtCellNo = new Textbox();
        txtCellNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "CellNumber"));
        txtCellNo.setHflex("1");

        txtEmail = new Textbox();
        txtEmail.setPlaceholder(Msg.getMsg(Env.getCtx(), "Email"));
        txtEmail.setHflex("2");

        txtOtp = new Textbox();
        txtOtp.setPlaceholder(Msg.getMsg(Env.getCtx(), "EnterOTP"));
        txtOtp.setHflex("1");

        btnSendOtp = new Button(Msg.getMsg(Env.getCtx(), "SendOtp"));
        btnRegisterUser = new Button(Msg.getMsg(Env.getCtx(), "RegisterMe"));

        // rows: [ID | Passport], [Send OTP | OTP]
       // Hlayout idRow = new Hlayout();
       // idRow.appendChild(txtIDNo);
       // idRow.appendChild(txtPassportNo);
     
        
        //Hlayout otpRow = new Hlayout();
        btnSendOtp.setHflex(null);       // don’t stretch
        txtOtp.setHflex("1");            // take the remaining space
        //otpRow.appendChild(btnSendOtp);
        //otpRow.appendChild(txtOtp);

        form.appendChild(txtName);
        //form.appendChild(nameBox);
        form.appendChild(txtIDNo);
        form.appendChild(txtPassportNo);
        form.appendChild(txtCellNo);
        form.appendChild(txtEmail);
        form.appendChild(btnSendOtp); 
        form.appendChild(txtOtp); 
        form.appendChild(btnRegisterUser);

        this.appendChild(form);
    }
    */
    
    private void buildUI() {
        Vbox form = new Vbox();
        form.setSpacing("8px");
        // Do NOT set hflex on form when you use explicit widths on children
        form.setWidth("100%");           // fine to let the form fill the window

        // NAME (large)
        txtName = new Textbox();
        txtName.setPlaceholder(Msg.getMsg(Env.getCtx(), "FullName"));
        txtName.setWidth("600px");       // large, but not full width
        form.appendChild(txtName);

        // ID NUMBER (smaller, on its own line)
        txtIDNo = new Textbox();
        txtIDNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "IDNumber"));
        txtIDNo.setWidth("300px");
        form.appendChild(txtIDNo);

        // PASSPORT NUMBER (smaller, stacked under ID)
        txtPassportNo = new Textbox();
        txtPassportNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "PassportNumber"));
        txtPassportNo.setWidth("300px");
        form.appendChild(txtPassportNo);

        // CELL NUMBER (smaller)
        txtCellNo = new Textbox();
        txtCellNo.setPlaceholder(Msg.getMsg(Env.getCtx(), "CellNumber"));
        txtCellNo.setWidth("300px");
        form.appendChild(txtCellNo);

        // EMAIL (large)
        txtEmail = new Textbox();
        txtEmail.setPlaceholder(Msg.getMsg(Env.getCtx(), "Email"));
        txtEmail.setWidth("600px");
        form.appendChild(txtEmail);

        // SEND OTP (button), then OTP BELOW it (smaller)
        btnSendOtp = new Button(Msg.getMsg(Env.getCtx(), "SendOtp"));
        form.appendChild(btnSendOtp);

        txtOtp = new Textbox();
        txtOtp.setPlaceholder(Msg.getMsg(Env.getCtx(), "EnterOTP"));
        txtOtp.setWidth("220px");        // smaller than other fields
        form.appendChild(txtOtp);

        // REGISTER
        btnRegisterUser = new Button(Msg.getMsg(Env.getCtx(), "RegisterMe"));
        form.appendChild(btnRegisterUser);

        this.appendChild(form);
    }



    /** Wire listeners */
    private void wireEvents() {
        // Mutually exclusive ID/Passport UX
        txtIDNo.addEventListener(Events.ON_CHANGE, ev -> {
	        if (!txtIDNo.getValue().trim().isEmpty()) {
	            txtPassportNo.setDisabled(true);
	        } else {
	            txtPassportNo.setDisabled(false);
	        }
	    });

	    txtPassportNo.addEventListener(Events.ON_CHANGE, ev -> {
	        if (!txtPassportNo.getValue().trim().isEmpty()) {
	            txtIDNo.setDisabled(true);
	        } else {
	            txtIDNo.setDisabled(false);
	        }
	    });
        //txtIDNo.addEventListener(Events.ON_CHANGE, e -> txtPassportNo.setDisabled(!isEmpty(txtIDNo)));
        //txtPassportNo.addEventListener(Events.ON_CHANGE, e -> txtIDNo.setDisabled(!isEmpty(txtPassportNo)));

        btnSendOtp.addEventListener(Events.ON_CLICK, this);
        btnRegisterUser.addEventListener(Events.ON_CLICK, this);
    }

    @Override
    public void onEvent(Event event) throws Exception {
        if (event.getTarget() == btnSendOtp) {
            onSendOtp();
        } else if (event.getTarget() == btnRegisterUser) {
            onRegister();
        }
    }

    // ----------------------- OTP SEND -----------------------

    private void onSendOtp() {
        String email = nvl(txtEmail.getValue());
        if (email.isEmpty())
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "FillEmailFirst"));

        // generate + store OTP in session (or switch to DB if you need multi-device)
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        Executions.getCurrent().getSession().setAttribute("OTP_CODE", otp);

        Map<String,String> vars = new HashMap<>();
        vars.put("OTP", otp);
        vars.put("EMail", email);
        vars.put("FullName", nvl(txtName.getValue()));

        boolean ok = sendWithTemplate(email, OTP_MAIL_TEXT_NAME, vars, null);
        if (!ok)
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "OtpSendFailed"));

        // Optional success info (not an error)
        FDialog.info(0, this, Msg.getMsg(Env.getCtx(), "OtpSent", new Object[]{ email }));
    }

    // ----------------------- REGISTER -----------------------

    private void onRegister() {
        String name       = nvl(txtName.getValue());
        String idNo       = nvl(txtIDNo.getValue());
        String passportNo = nvl(txtPassportNo.getValue());
        String cellNo     = nvl(txtCellNo.getValue());
        String email      = nvl(txtEmail.getValue());
        String otp        = nvl(txtOtp.getValue());

        // Required (except ID/Passport special rule)
        if (name.isEmpty() || cellNo.isEmpty() || email.isEmpty() || otp.isEmpty()) {
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "FillRequiredFields"));
        }
        // Exactly one of ID or Passport
        if (idNo.isEmpty() && passportNo.isEmpty()) {
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "EnterIdOrPassport"));
        }
        if (!idNo.isEmpty() && !passportNo.isEmpty()) {
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "OnlyOneIdOrPassport"));
        }
        // ID must be 13 digits if provided
        if (!idNo.isEmpty() && !idNo.matches("\\d{13}")) {
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "InvalidIdNumber"));
        }
        // OTP check
        String storedOtp = (String) Executions.getCurrent().getSession().getAttribute("OTP_CODE");
        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "InvalidOtp"));
        }

        // ---- Create User ----
        MUser user = new MUser(Env.getCtx(), 0, null);
        user.setName(name);
        user.setPhone(cellNo);
        user.setEMail(email);
        user.setIsActive(true);
        user.set_ValueNoCheck(MUser.COLUMNNAME_AD_Client_ID, DEFAULT_CLIENT_ID);

        // Custom columns (adjust names to match your dictionary)
        user.set_ValueOfColumn("ZZ_ID_Passport_No", idNo);
        user.set_ValueOfColumn("ZZ_Passport_No",   passportNo);

        // Temp password & force change
        String tempPwd = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(tempPwd);
        user.setIsExpired(true);
        user.saveEx();

        // ---- Link default role ----
        MUserRoles ur = new MUserRoles(Env.getCtx(), 0, null);
        ur.setAD_User_ID(user.getAD_User_ID());
        ur.setAD_Role_ID(DEFAULT_ROLE_ID);
        ur.setIsActive(true);
        ur.set_ValueNoCheck(MUserRoles.COLUMNNAME_AD_Client_ID, DEFAULT_CLIENT_ID);
        ur.saveEx();

        // ---- Welcome email via template ----
        Map<String,String> vars = new HashMap<>();
        vars.put("FullName",    user.getName());
        vars.put("TempPassword", tempPwd);
        vars.put("EMail",       user.getEMail());

        boolean ok = sendWithTemplate(user.getEMail(), WELCOME_MAIL_TEXT_NAME, vars, user);
        if (!ok) {
            // Decide policy: warn (don’t block registration) or throw
            log.warning("Welcome email failed to send to " + user.getEMail());
        }

        // Optional success info (not an error)
        FDialog.info(0, this, Msg.getMsg(Env.getCtx(), "RegistrationSuccess"));
        detach();
    }

    // ----------------------- Mail helper using R_MailText -----------------------

    /**
     * Send an email using an R_MailText template.
     * Variables are provided via ctxVars and available as #Key# in the template body.
     * If userOrNull != null, mailText.setUser(user) is applied for @User@ tokens.
     */
    private boolean sendWithTemplate(String toEMail, String templateName,
                                     Map<String, String> ctxVars,
                                     MUser userOrNull) {
        int clientId = userOrNull != null ? userOrNull.getAD_Client_ID() : Env.getAD_Client_ID(Env.getCtx());
        int mailTextId = findMailTextId(templateName, clientId);
        if (mailTextId <= 0)
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "MailTextNotFound"));

        MMailText mailText = new MMailText(Env.getCtx(), mailTextId, null);
        mailText.setLanguage(Env.getContext(Env.getCtx(), Env.LANGUAGE));
        if (userOrNull != null) {
            mailText.setUser(userOrNull);
        }

        // Push variables into context  as #Key
        if (ctxVars != null) {
            for (Map.Entry<String, String> e : ctxVars.entrySet()) {
                Env.setContext(Env.getCtx(), "#" + e.getKey(), safeTrim(e.getValue()));
            }
        }

        String body = mailText.getMailText(true, true, true);
        body = Env.parseVariable(body, userOrNull, null, true);

        // Clean up context variables 
        if (ctxVars != null) {
            for (String k : ctxVars.keySet()) {
                Env.setContext(Env.getCtx(), "#" + k, "");
            }
        }

        MClient client = MClient.get(Env.getCtx());
        EMail email = client.createEMail(toEMail, mailText.getMailHeader(), body, mailText.isHtml());
        if (mailText.isHtml()) {
            email.setMessageHTML(mailText.getMailHeader(), body);
        } else {
            email.setSubject(mailText.getMailHeader());
            email.setMessageText(body);
        }

        if (!email.isValid() && !email.isValid(true))
            throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "EmailNotValid"));

        return EMail.SENT_OK.equals(email.send());
    }

    /** Find R_MailText_ID by Name for client or system */
    private int findMailTextId(String name, int adClientId) {
        String sql =
            "SELECT R_MailText_ID " +
            "FROM R_MailText " +
            "WHERE IsActive='Y' AND Name=? " +
            "AND AD_Client_ID IN (?, 0) " +
            "ORDER BY AD_Client_ID";
        return DB.getSQLValue(null, sql, name, adClientId);
    }

    // ----------------------- Utilities -----------------------

    private static boolean isEmpty(Textbox tb) {
        String v = tb.getValue();
        return v == null || v.trim().isEmpty();
    }

    private static String nvl(String s) {
        return s == null ? "" : s.trim();
    }

    /** Helper to show modally and handle attachment correctly */
    public static void show(Component attachTo) {
        RegistrationWindow w = new RegistrationWindow();
        if (attachTo != null && attachTo.getPage() != null) {
            attachTo.appendChild(w);      // attach first
            w.setMode(Window.MODAL);      // then modal
        } else {
            // fallback: attach to the first page in the desktop
            Page p = Executions.getCurrent().getDesktop().getPages().iterator().next();
            w.setPage(p);                 // attach to page
            w.setMode(Window.MODAL);      // then modal
        }
    }
    
    private static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }
}

