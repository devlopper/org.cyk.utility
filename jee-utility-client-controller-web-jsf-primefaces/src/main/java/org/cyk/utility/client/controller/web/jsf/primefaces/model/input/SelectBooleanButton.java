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
public class SelectBooleanButton extends AbstractInput<Boolean> implements Serializable {

	private String offLabel,onLabel,offIcon,onIcon;
	
	/**/
	
	public static final String FIELD_OFF_LABEL = "offLabel";
	public static final String FIELD_ON_LABEL = "onLabel";
	public static final String FIELD_OFF_ICON = "offIcon";
	public static final String FIELD_ON_ICON = "onIcon";
	
	/**/
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectBooleanButton> implements Serializable {

		@Override
		public void configure(SelectBooleanButton selectBooleanButton, Map<Object, Object> arguments) {
			super.configure(selectBooleanButton, arguments);
			if(StringHelper.isBlank(selectBooleanButton.offLabel))
				selectBooleanButton.offLabel = "Non";
			if(StringHelper.isBlank(selectBooleanButton.onLabel))
				selectBooleanButton.onLabel = "Oui";
			if(StringHelper.isBlank(selectBooleanButton.offIcon))
				selectBooleanButton.offIcon = "fa fa-minus";
			if(StringHelper.isBlank(selectBooleanButton.onIcon))
				selectBooleanButton.onIcon = "fa fa-check";
		}
		
		@Override
		protected String __getTemplate__(SelectBooleanButton selectBooleanButton, Map<Object, Object> arguments) {
			return "/input/boolean/selectbooleanbutton/default.xhtml";
		}
		
		@Override
		protected Class<SelectBooleanButton> __getClass__() {
			return SelectBooleanButton.class;
		}
		
		/**/
		
	}
	
	public static SelectBooleanButton build(Map<Object, Object> arguments) {
		return Builder.build(SelectBooleanButton.class,arguments);
	}
	
	public static SelectBooleanButton build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static SelectBooleanButton buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(SelectBooleanButton.class, new ConfiguratorImpl());
	}
}