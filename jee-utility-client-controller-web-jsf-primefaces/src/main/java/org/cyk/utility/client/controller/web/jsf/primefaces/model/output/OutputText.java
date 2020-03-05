package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OutputText extends AbstractOutput<String> implements Serializable {

	/**/
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputText> implements Serializable {

		@Override
		public void configure(OutputText outputLabel, Map<Object, Object> arguments) {
			super.configure(outputLabel, arguments);
			
		}
		
		@Override
		protected String __getTemplate__() {
			return "/output/text/default.xhtml";
		}
		
		@Override
		protected Class<OutputText> __getClass__() {
			return OutputText.class;
		}
		
		/**/
		
	}
	
	public static OutputText build(Map<Object, Object> arguments) {
		return Builder.build(OutputText.class,arguments);
	}
	
	public static OutputText build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}

	static {
		Configurator.set(OutputText.class, new ConfiguratorImpl());
	}
}
