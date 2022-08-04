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
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Event;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Dialog extends AbstractObject implements Serializable {
	
	private String header,__containerIdentifier__,appendTo;
	private Boolean modal,fitViewport,closeOnEscape,draggable,dynamic,resizable;
	private Integer minWidth,minHeight,width,height;
	private CommandButton closeCommandButton,executeCommandButton;
	private Collection<CommandButton> commandButtons;
	private Ajax closeAjax;
	
	/**/
	
	public Dialog buildExecuteCommandButton() {
		((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).buildExecuteCommandButton(this);
		return this;
	}
	
	public Dialog buildCommandButtons() {
		((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).buildCommandButtons(this);
		return this;
	}
	
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
	
	public static final String FIELD___CONTAINER_IDENTIFIER__ = "__containerIdentifier__";
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_MODAL = "modal";
	public static final String FIELD_FIT_VIEWPORT = "fitViewport";
	public static final String FIELD_CLOSE_ON_ESCAPE = "closeOnEscape";
	public static final String FIELD_DRAGGABLE = "draggable";
	public static final String FIELD_DYNAMIC = "dynamic";
	public static final String FIELD_RESIZABLE = "resizable";
	public static final String FIELD_MIN_WIDTH = "minWidth";
	public static final String FIELD_MIN_HEIGHT = "minHeight";
	public static final String FIELD_WIDTH = "width";
	public static final String FIELD_HEIGHT = "height";
	public static final String FIELD_APPEND_TO = "appendTo";
	
	/**/
	
	public static interface Listener {
		Map<Object,Object> getExecuteCommandButtonArguments(Dialog dialog);
		
		void buildExecuteCommandButton(Dialog dialog);
		
		void buildCommandButtons(Dialog dialog);
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {
			@Override
			public Map<Object, Object> getExecuteCommandButtonArguments(Dialog dialog) {
				return MapHelper.instantiate(CommandButton.FIELD_VALUE,"Exécuter",CommandButton.FIELD_ICON,"fa fa-check");
			}
			@Override
			public void buildExecuteCommandButton(Dialog dialog) {
				Map<Object, Object> arguments = getExecuteCommandButtonArguments(dialog);
				if(MapHelper.isNotEmpty(arguments))
					dialog.executeCommandButton = CommandButton.build(arguments);
			}
			
			@Override
			public void buildCommandButtons(Dialog dialog) {
				dialog.buildExecuteCommandButton();
				
				dialog.setCloseCommandButton(CommandButton.build(CommandButton.FIELD_VALUE,"Fermer",CommandButton.FIELD_ICON,"fa fa-remove"));
				dialog.getCloseCommandButton().getEventScripts(Boolean.TRUE).write(Event.CLICK, "PF('"+dialog.getWidgetVar()+"').hide();");
				dialog.getCloseCommandButton().getRunnerArguments().setSuccessMessageArguments(null);
				dialog.getCloseCommandButton().setType("button");
				
				dialog.addCommandButtons(dialog.getExecuteCommandButton(),dialog.getCloseCommandButton());
			}
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {
				public static final Listener INSTANCE = new DefaultImpl();
			}
		}
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<DIALOG extends Dialog> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<DIALOG> implements Serializable {
		
		@Override
		public void configure(DIALOG dialog, Map<Object, Object> arguments) {
			super.configure(dialog, arguments);
			dialog.set__containerIdentifier__(dialog.getIdentifier()+"_container");
			if(ValueHelper.defaultToIfNull((Boolean)MapHelper.readByKey(arguments, FIELD_COMMAND_BUTTONS_BUILDABLE),Boolean.TRUE))
				dialog.buildCommandButtons();
			
			dialog.setCloseAjax(Ajax.build(Ajax.FIELD_EVENT,"close"));
			dialog.getCloseAjax().getRunnerArguments().setSuccessMessageArguments(null);
			
			if(dialog.getMinHeight() == null)
				dialog.setMinHeight(0);
			if(dialog.getMinWidth() == null)
				dialog.setMinWidth(150);
		}
		
		public static final String FIELD_COMMAND_BUTTONS_BUILDABLE = "commandButtonsBuildable";
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Dialog> implements Serializable {

		@Override
		protected String __getTemplate__(Dialog dialog, Map<Object, Object> arguments) {
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