package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectBooleanCheckbox extends AbstractInput<Boolean> implements Serializable {

	private String itemLabel;
	
	/**/
	
	public static final String FIELD_ITEM_LABEL = "itemLabel";
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectBooleanCheckbox> implements Serializable {

		@Override
		public void configure(SelectBooleanCheckbox selectBooleanCheckbox, Map<Object, Object> arguments) {
			super.configure(selectBooleanCheckbox, arguments);
			if(StringHelper.isBlank(selectBooleanCheckbox.itemLabel))
				selectBooleanCheckbox.itemLabel = "Oui";
		}
		
		@Override
		protected String __getTemplate__(SelectBooleanCheckbox selectBooleanCheckbox, Map<Object, Object> arguments) {
			return "/input/boolean/selectbooleancheckbox/default.xhtml";
		}
		
		@Override
		protected Class<SelectBooleanCheckbox> __getClass__() {
			return SelectBooleanCheckbox.class;
		}
		
		/**/
		
	}
	
	public static SelectBooleanCheckbox build(Map<Object, Object> arguments) {
		return Builder.build(SelectBooleanCheckbox.class,arguments);
	}
	
	public static SelectBooleanCheckbox build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static SelectBooleanCheckbox buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(SelectBooleanCheckbox.class, new ConfiguratorImpl());
	}
}