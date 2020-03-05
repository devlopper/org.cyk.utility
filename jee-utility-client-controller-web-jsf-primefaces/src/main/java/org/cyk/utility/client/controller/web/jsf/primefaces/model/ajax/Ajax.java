package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import java.io.Serializable;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

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
			
		}

		@Override
		protected Class<Ajax> __getClass__() {
			return Ajax.class;
		}
	}
	
	public static Ajax build(Map<Object,Object> arguments) {
		return Builder.build(Ajax.class,arguments);
	}
	
	public static Ajax build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Ajax.class, new ConfiguratorImpl());
	}
}