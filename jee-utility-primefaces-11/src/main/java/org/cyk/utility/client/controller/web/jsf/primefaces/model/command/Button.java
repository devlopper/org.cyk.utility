package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Button extends AbstractCommand implements Serializable {

	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Button> implements Serializable {
		@Override
		protected Class<Button> __getClass__() {
			return Button.class;
		}
		
		@Override
		protected String __getTemplate__(Button button, Map<Object, Object> arguments) {
			return "/command/button/default.xhtml";
		}
	}

	public static Button build(Map<Object,Object> arguments) {
		return Builder.build(Button.class,arguments);
	}
	
	public static Button build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Button.class, new ConfiguratorImpl());
	}
}