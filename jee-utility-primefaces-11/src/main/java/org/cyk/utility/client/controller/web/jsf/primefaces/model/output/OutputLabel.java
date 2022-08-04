package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputLabel extends AbstractOutput<String> implements Serializable {

	private String for_,title;
	
	/**/
	
	/**/
	
	public static final String FIELD_FOR = "for_";
	public static final String FIELD_TITLE = "title";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputLabel> implements Serializable {

		@Override
		public void configure(OutputLabel outputLabel, Map<Object, Object> arguments) {
			super.configure(outputLabel, arguments);
			if(StringHelper.isBlank(outputLabel.title))
				outputLabel.title = outputLabel.value;
		}
		
		@Override
		protected String __getTemplate__(OutputLabel outputLabel, Map<Object, Object> arguments) {
			return "/output/label/default.xhtml";
		}
		
		@Override
		protected Class<OutputLabel> __getClass__() {
			return OutputLabel.class;
		}
		
		/**/
		
	}
	
	public static OutputLabel build(Map<Object, Object> arguments) {
		return Builder.build(OutputLabel.class,arguments);
	}
	
	public static OutputLabel build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static OutputLabel buildFromValueFor(String value,String for_) {
		return OutputLabel.build(OutputLabel.FIELD_VALUE,value,OutputLabel.FIELD_FOR,for_);
	}

	static {
		Configurator.set(OutputLabel.class, new ConfiguratorImpl());
	}
}
