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
package org.adempiere.webui.editor;


import java.util.logging.Level;

import org.adempiere.util.GridRowCtx;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.ZkCssHelper;
import org.adempiere.webui.event.DialogEvents;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.adempiere.webui.window.WImageURLDialog;
import org.compiere.model.GridField;
import org.compiere.model.MAttachment;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Html;
import org.zkoss.zul.Image;

/**
 * Default editor for {@link DisplayType#ImageURL}.<br/>
 * Implemented with {@link Image} component and {@link WImageURLDialog}.
 * 
 * @author Low Heng Sin
 */
public class WImageURLEditor extends WEditor
{
    private static final String[] LISTENER_EVENTS = {Events.ON_CLICK};
    
    private boolean m_mandatory;

	private boolean readwrite;

	private String oldValue;
	
	private String contextPath = null;
	
    /**	Logger			*/
	private static final CLogger log = CLogger.getCLogger(WImageEditor.class);
    
	/**
	 * 
	 * @param gridField
	 */
	public WImageURLEditor(GridField gridField)
	{
		this(gridField, false, null);
	}
	
	/**
	 * 
	 * @param gridField
	 * @param tableEditor
	 * @param editorConfiguration
	 */
    public WImageURLEditor(GridField gridField, boolean tableEditor, IEditorConfiguration editorConfiguration)
    {
        super(new Image() {
			private static final long serialVersionUID = 8492629361709791256L;

			@Override
			public void onPageAttached(Page newpage, Page oldpage) {
				super.onPageAttached(newpage, oldpage);
				if (newpage != null && getParent() != null) {
					Component p = getParent();
					if (p instanceof Cell) {
						Cell cell = (Cell) p;
						LayoutUtils.addSclass("image-field-cell", cell);
					}
				}
			}        	
        }, gridField, tableEditor, editorConfiguration);
        getComponent().addCallback(AbstractComponent.AFTER_PAGE_ATTACHED, t -> {
        	if (contextPath == null && Executions.getCurrent() != null)
	        	contextPath = Executions.getCurrent().getContextPath();
        });
        init();
    }

    @Override
    public Image getComponent() {
    	return (Image) component;
    }
    
    /**
     * Init component
     */
    private void init()
    {
    	getComponent().setSrc(null);
        getComponent().setSclass("image-field");
        if (Executions.getCurrent() != null)
        	contextPath = Executions.getCurrent().getContextPath();
    }

     @Override
    public String getDisplay()
    {
    	 return getValue() != null ? getValue().toString() : null;
    }

    @Override
    public Object getValue()
    {
		return oldValue;
    }

    @Override
    public boolean isMandatory()
    {
        return m_mandatory;
    }
   
    
    @Override
    public void setMandatory(boolean mandatory)
    {
        m_mandatory = mandatory;
    }
    
    @Override
	public boolean isReadWrite() {
		return readwrite;
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		this.readwrite = readWrite;
		if (readwrite) {
			LayoutUtils.removeSclass("image-field-readonly", getComponent());
		} else {
			LayoutUtils.addSclass("image-field-readonly", getComponent());
		}
	}

	@Override
    public void setValue(Object value)
    {
    	String newValue = value != null ? value.toString() : null;
		if (Util.isEmpty(newValue, true))
		{
			oldValue = null;
			getComponent().setSrc(null);
			ZkCssHelper.removeStyle(getComponent(), "width");
			ZkCssHelper.removeStyle(getComponent(), "height");
			LayoutUtils.removeSclass("thumbnail", getComponent());
			LayoutUtils.removeSclass("image-fit", getComponent());
			getComponent().setClientAttribute("onmouseenter", null);
			getComponent().setClientAttribute("onmouseleave", null);
			//invalidate necessary for setClientAttribute to work
			getComponent().invalidate();
			return;			
		}
		else
		{
			String width = MSysConfig.getIntValue(MSysConfig.ZK_THUMBNAIL_IMAGE_WIDTH, 100, Env.getAD_Client_ID(Env.getCtx()))+"px";
			String height = MSysConfig.getIntValue(MSysConfig.ZK_THUMBNAIL_IMAGE_HEIGHT, 100, Env.getAD_Client_ID(Env.getCtx()))+"px";
			String style = "width:"+width+";height:"+height;
			ZkCssHelper.appendStyle(getComponent(), style);
			LayoutUtils.addSclass("thumbnail", getComponent());
			LayoutUtils.addSclass("image-fit", getComponent());
			getComponent().setClientAttribute("onmouseenter", "idempiere.showFullSizeImage(event)");
			getComponent().setClientAttribute("onmouseleave", "idempiere.hideFullSizeImage(event)");
			//invalidate necessary for setClientAttribute to work
			getComponent().invalidate();
		}
		//
		if (log.isLoggable(Level.FINE)) log.fine(value.toString());
		if (MAttachment.isAttachmentURLPath(newValue))
		{
			String url = MAttachment.getImageAttachmentURLFromPath(null, newValue);
			getComponent().setSrc(url);
		}
		else
		{
			getComponent().setSrc(newValue);
		}
		oldValue = newValue;
    }
    	
	@Override
    public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

	@Override
	public void onEvent(Event event) throws Exception 
	{
		if (Events.ON_CLICK.equals(event.getName()) && readwrite)
		{
			String script = "jq('#"+getComponent().getUuid()+"').trigger('mouseleave');";
			Clients.evalJavaScript(script);
			final WImageURLDialog dialog = new WImageURLDialog(this);
			dialog.addEventListener(DialogEvents.ON_WINDOW_CLOSE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					if (!dialog.isCancel()) {
						String newValue = dialog.getValue();
						setValue(newValue);	//	set explicitly
						//
						ValueChangeEvent vce = new ValueChangeEvent(WImageURLEditor.this, gridField.getColumnName(), oldValue, newValue);
						fireValueChange(vce);
						oldValue = newValue;
					}
					
				}
			});		
			dialog.setPage(this.getComponent().getPage());
			dialog.setVflex("min");
			ZKUpdateUtil.setWindowWidthX(dialog, 500);
			LayoutUtils.openPopupWindow(getComponent(), dialog, "after_start");
			dialog.focus();
		}
	}

	/**
	 * No op., doesn't support stretch of component.
	 */
	@Override
	public void fillHorizontal() {
	}

	@Override
	public Component getDisplayComponent() {
		return new Html();
	}

	@Override
	public String getDisplayTextForGridView(GridRowCtx gridRowCtx, Object value) {
		String url = value != null ? value.toString() : null;
		if (!Util.isEmpty(url, true)) {
			String width = MSysConfig.getIntValue(MSysConfig.ZK_THUMBNAIL_IMAGE_WIDTH, 100, Env.getAD_Client_ID(Env.getCtx()))+"px";
			String height = MSysConfig.getIntValue(MSysConfig.ZK_THUMBNAIL_IMAGE_HEIGHT, 100, Env.getAD_Client_ID(Env.getCtx()))+"px";
			if (MAttachment.isAttachmentURLPath(url))
			{
				if (contextPath == null && Executions.getCurrent() != null)
		        	contextPath = Executions.getCurrent().getContextPath();
				url = MAttachment.getImageAttachmentURLFromPath(contextPath, url);
			}
			StringBuilder builder = new StringBuilder("<img src='");
			builder.append(url)
				.append("' style='width:").append(width).append(";")
				.append("height:").append(height).append("' ")
				.append("class='thumbnail image-fit' ")
				.append("onmouseenter='idempiere.showFullSizeImage(event)' onmouseleave='idempiere.hideFullSizeImage(event)'/>");
			return builder.toString();
		} else {
			return "<span class='no-image'/>";
		}
	}		
}
