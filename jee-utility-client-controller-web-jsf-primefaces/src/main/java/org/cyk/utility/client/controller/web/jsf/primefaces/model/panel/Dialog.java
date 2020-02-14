package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;
import java.util.Map;

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
	private CommandButton closeCommandButton;
	private Ajax closeAjax;
	
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
			dialog.setCloseCommandButton(Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Fermer",CommandButton.FIELD_ICON,"fa fa-remove")));
			dialog.getCloseCommandButton().getEventScripts(Boolean.TRUE).write(Event.CLICK, "PF('"+dialog.getWidgetVar()+"').hide();");
			dialog.getCloseCommandButton().getRunnerArguments().setSuccessMessageArguments(null);
			dialog.getCloseCommandButton().setType("button");
			
			dialog.setCloseAjax(Builder.build(Ajax.class,Map.of(Ajax.FIELD_EVENT,"close")));
			dialog.getCloseAjax().getRunnerArguments().setSuccessMessageArguments(null);
		}
		
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Dialog> implements Serializable {

		@Override
		protected Class<Dialog> __getClass__() {
			return Dialog.class;
		}

	}
	
	static {
		Configurator.set(Dialog.class, new ConfiguratorImpl());
	}
}
