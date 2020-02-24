package org.cyk.utility.client.controller.web.jsf.primefaces.model.output;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OutputLabel extends AbstractOutput<String> implements Serializable {

	private String for_;
	
	/**/
	
	/**/
	
	public static final String FIELD_FOR = "for_";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<OutputLabel> implements Serializable {

		@Override
		public void configure(OutputLabel outputLabel, Map<Object, Object> arguments) {
			super.configure(outputLabel, arguments);
			
		}
		
		@Override
		protected String __getTemplate__() {
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

	static {
		Configurator.set(OutputLabel.class, new ConfiguratorImpl());
	}
}
