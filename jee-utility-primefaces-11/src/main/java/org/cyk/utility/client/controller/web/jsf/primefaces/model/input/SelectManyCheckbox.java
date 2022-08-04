package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectManyCheckbox extends AbstractInputChoiceMany implements Serializable {

	/**/
	
	/**/

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectManyCheckbox> implements Serializable {

		@Override
		public void configure(SelectManyCheckbox selectManyCheckbox, Map<Object, Object> arguments) {
			super.configure(selectManyCheckbox, arguments);
		
		}
		
		@Override
		protected Class<SelectManyCheckbox> __getClass__() {
			return SelectManyCheckbox.class;
		}
		
		@Override
		protected String __getTemplate__(SelectManyCheckbox selectManyCheckbox, Map<Object, Object> arguments) {
			return "/input/select/many/selectManyCheckbox.xhtml";
		}
	}
	
	public static SelectManyCheckbox build(Map<Object, Object> arguments) {
		return Builder.build(SelectManyCheckbox.class,arguments);
	}
	
	public static SelectManyCheckbox build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	/**/

	static {
		Configurator.set(SelectManyCheckbox.class, new ConfiguratorImpl());
	}
}