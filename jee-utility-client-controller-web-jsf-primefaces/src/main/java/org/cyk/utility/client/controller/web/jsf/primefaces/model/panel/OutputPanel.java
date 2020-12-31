package org.cyk.utility.client.controller.web.jsf.primefaces.model.panel;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputPanel extends AbstractObject implements Serializable {

	protected Boolean deferred;
	protected String deferredMode;
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<OBJECT extends OutputPanel> extends org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject.AbstractConfiguratorImpl<OBJECT> implements Serializable {
		
		public void configure(OBJECT object, Map<Object,Object> arguments) {
			super.configure(object, arguments);
			if(object.deferred == null)
				object.deferred = Boolean.FALSE;
			if(object.deferredMode == null)
				object.deferredMode = "load";
		}
		
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputPanel> implements Serializable {

		@Override
		protected Class<OutputPanel> __getClass__() {
			return OutputPanel.class;
		}
	}
	
	static {
		Configurator.set(OutputPanel.class, new ConfiguratorImpl());
	}
}
