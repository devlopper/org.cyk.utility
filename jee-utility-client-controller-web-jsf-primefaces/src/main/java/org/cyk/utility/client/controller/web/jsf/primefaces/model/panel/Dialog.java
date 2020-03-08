package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Event;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Dialog extends AbstractObject implements Serializable {
	
	private String header;
	private Boolean modal,fitViewport,closeOnEscape,draggable,dynamic,resizable;
	private CommandButton closeCommandButton,executeCommandButton;
	private Collection<CommandButton> commandButtons;
	private Ajax closeAjax;
	
	/**/
	
	public Collection<CommandButton> getCommandButtons(Boolean injectIfNull) {
		if(commandButtons == null && Boolean.TRUE.equals(injectIfNull))
			commandButtons = new ArrayList<>();
		return commandButtons;
	}
	
	public Dialog addCommandButtons(Collection<CommandButton> commandButtons) {
		if(CollectionHelper.isEmpty(commandButtons))
			return this;
		getCommandButtons(Boolean.TRUE).addAll(commandButtons);
		return this;
	}
	
	public Dialog addCommandButtons(CommandButton...commandButtons) {
		if(ArrayHelper.isEmpty(commandButtons))
			return this;
		return addCommandButtons(CollectionHelper.listOf(commandButtons));
	}
	
	/**/
	
	/**/
	
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_MODAL = "modal";
	public static final String FIELD_FIT_VIEWPORT = "fitViewport";
	public static final String FIELD_CLOSE_ON_ESCAPE = "closeOnEscape";
	public static final String FIELD_DRAGGABLE = "draggable";
	public static final String FIELD_DYNAMIC = "dynamic";
	public static final String FIELD_RESIZABLE = "resizable";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<DIALOG extends Dialog> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<DIALOG> implements Serializable {
		
		@Override
		public void configure(DIALOG dialog, Map<Object, Object> arguments) {
			super.configure(dialog, arguments);
			dialog.setExecuteCommandButton(CommandButton.build(CommandButton.FIELD_VALUE,"Exécuter",CommandButton.FIELD_ICON,"fa fa-check"));
			
			dialog.setCloseCommandButton(CommandButton.build(CommandButton.FIELD_VALUE,"Fermer",CommandButton.FIELD_ICON,"fa fa-remove"));
			dialog.getCloseCommandButton().getEventScripts(Boolean.TRUE).write(Event.CLICK, "PF('"+dialog.getWidgetVar()+"').hide();");
			dialog.getCloseCommandButton().getRunnerArguments().setSuccessMessageArguments(null);
			dialog.getCloseCommandButton().setType("button");
			
			dialog.addCommandButtons(dialog.getExecuteCommandButton(),dialog.getCloseCommandButton());
			
			dialog.setCloseAjax(Ajax.build(Ajax.FIELD_EVENT,"close"));
			dialog.getCloseAjax().getRunnerArguments().setSuccessMessageArguments(null);
		}
		
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Dialog> implements Serializable {

		@Override
		protected String __getTemplate__() {
			return "/dialog/default.xhtml";
		}
		
		@Override
		protected Class<Dialog> __getClass__() {
			return Dialog.class;
		}

	}
	
	public static Dialog build(Map<Object,Object> arguments) {
		return Builder.build(Dialog.class,arguments);
	}
	
	public static Dialog build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Dialog.class, new ConfiguratorImpl());
	}
}
