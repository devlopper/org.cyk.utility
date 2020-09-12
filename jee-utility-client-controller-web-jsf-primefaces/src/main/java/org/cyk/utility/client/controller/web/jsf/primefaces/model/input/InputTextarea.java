package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputTextarea extends AbstractInput<String> implements Serializable {

	/**/
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<InputTextarea> implements Serializable {

		@Override
		public void configure(InputTextarea inputText, Map<Object, Object> arguments) {
			super.configure(inputText, arguments);
			
		}
		
		@Override
		protected String __getTemplate__(InputTextarea inputText, Map<Object, Object> arguments) {
			return "/input/textarea/default.xhtml";
		}
		
		@Override
		protected Class<InputTextarea> __getClass__() {
			return InputTextarea.class;
		}
		
		/**/
		
	}
	
	public static InputTextarea build(Map<Object, Object> arguments) {
		return Builder.build(InputTextarea.class,arguments);
	}
	
	public static InputTextarea build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static InputTextarea buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(InputTextarea.class, new ConfiguratorImpl());
	}
}