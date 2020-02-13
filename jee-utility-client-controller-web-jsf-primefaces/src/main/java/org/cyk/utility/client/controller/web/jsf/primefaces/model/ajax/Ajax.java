package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import java.io.Serializable;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Ajax extends AbstractAjax<AjaxBehaviorEvent> implements Serializable {

	@Override
	public Ajax setEvent(String event) {
		return (Ajax) super.setEvent(event);
	}
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Ajax,AjaxBehaviorEvent> implements Serializable {

		@Override
		public void configure(Ajax ajax, Map<Object, Object> arguments) {
			super.configure(ajax, arguments);
			if(StringHelper.isBlank(ajax.getEvent()))
				ajax.setEvent("click");
		}

		@Override
		protected Class<Ajax> __getClass__() {
			return Ajax.class;
		}
	}
	
	static {
		Configurator.set(Ajax.class, new ConfiguratorImpl());
	}
}