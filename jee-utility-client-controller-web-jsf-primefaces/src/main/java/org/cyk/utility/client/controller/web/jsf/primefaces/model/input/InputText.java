package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputText extends AbstractInput<String> implements Serializable {

	
	/**/
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<InputText> implements Serializable {

		@Override
		public void configure(InputText inputText, Map<Object, Object> arguments) {
			super.configure(inputText, arguments);
			
		}
		
		@Override
		protected String __getTemplate__() {
			return "/input/text/default.xhtml";
		}
		
		@Override
		protected Class<InputText> __getClass__() {
			return InputText.class;
		}
		
		/**/
		
	}
	
	public static InputText build(Map<Object, Object> arguments) {
		return Builder.build(InputText.class,arguments);
	}
	
	public static InputText build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static InputText buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(InputText.class, new ConfiguratorImpl());
	}
}
