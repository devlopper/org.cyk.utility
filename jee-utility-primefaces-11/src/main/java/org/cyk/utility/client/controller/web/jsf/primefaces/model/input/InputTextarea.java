package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputTextarea extends AbstractInput<String> implements Serializable {

	/**/
	
	@Override
	public Object deriveBinding(String beanPath) {
		org.primefaces.component.inputtextarea.InputTextarea component = new org.primefaces.component.inputtextarea.InputTextarea();
		for(Object[] array : new Object[][] {
				new Object[] {FIELD_STYLE,null,String.class}
				,new Object[] {FIELD_STYLE_CLASS,null,String.class}
			}) {
			String property = array[1] == null ? (String) array[0] : (String) array[1];
			Class<?> klass = array[2] == null ? String.class : (Class<?>) array[2];
			__inject__(JavaServerFacesHelper.class).setValueExpression(component, property, JavaServerFacesHelper.buildValueExpression(String.format("#{%s.%s}",beanPath,array[0]), klass));
		}
		return component;
	}
	
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