package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Dialog extends AbstractObject implements Serializable {
	
	private String header;
	private Boolean modal,fitViewport,closeOnEscape,draggable,dynamic,resizable;
	
	/**/
	
	public static final String FIELD_HEADER = "header";
	public static final String FIELD_MODAL = "modal";
	public static final String FIELD_FIT_VIEWPORT = "fitViewport";
	public static final String FIELD_CLOSE_ON_ESCAPE = "closeOnEscape";
	public static final String FIELD_DRAGGABLE = "draggable";
	public static final String FIELD_DYNAMIC = "dynamic";
	public static final String FIELD_RESIZABLE = "resizable";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<OBJECT extends Dialog> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<OBJECT> implements Serializable {
		
		@Override
		public void configure(OBJECT dialog, Map<Object, Object> arguments) {
			super.configure(dialog, arguments);
			
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
