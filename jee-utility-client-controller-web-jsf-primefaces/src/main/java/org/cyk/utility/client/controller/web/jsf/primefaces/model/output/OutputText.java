package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputText extends AbstractOutput<String> implements Serializable {

	private Boolean escape;
	private String title;
	
	/**/
	
	/**/
	
	public static final String FIELD_ESCAPE = "escape";
	public static final String FIELD_TITLE = "title";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputText> implements Serializable {

		@Override
		public void configure(OutputText outputText, Map<Object, Object> arguments) {
			super.configure(outputText, arguments);
			if(outputText.title == null)
				outputText.title = outputText.value;
			if(outputText.escape == null)
				outputText.escape = Boolean.TRUE;
		}
		
		@Override
		protected String __getTemplate__(OutputText outputText, Map<Object, Object> arguments) {
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
	
	public static OutputText buildFromValue(String value) {
		return OutputText.build(OutputText.FIELD_VALUE,value);
	}

	static {
		Configurator.set(OutputText.class, new ConfiguratorImpl());
	}
}
