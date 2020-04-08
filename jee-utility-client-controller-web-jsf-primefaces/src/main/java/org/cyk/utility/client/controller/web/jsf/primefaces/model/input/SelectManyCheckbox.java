package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectManyCheckbox extends AbstractInput<Collection<Object>> implements Serializable {

	private Integer columns;
	private Boolean disabled;
	private String layout;
	private Collection<Object> choices;
	
	/**/
	
	public static final String FIELD_COLUMNS = "columns";
	public static final String FIELD_DISBALED = "disabled";
	public static final String FIELD_LAYOUT = "layout";
	public static final String FIELD_CHOICES = "choices";
	
	/**/
	
	public Object getChoiceValue(Object choice) {
		return choice;
	}
	
	public Object getChoiceLabel(Object choice) {
		return choice;
	}
	
	public Object getChoiceDescription(Object choice) {
		return null;
	}
	
	public Object getIsChoiceDisabled(Object choice) {
		return Boolean.FALSE;
	}
	
	public Boolean getIsChoiceLabelEscaped(Object choice) {
		return Boolean.FALSE;
	}
	
	/**/
	
	public static interface Listener {
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {
			
		}
	}

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectManyCheckbox> implements Serializable {

		@Override
		public void configure(SelectManyCheckbox selectManyCheckbox, Map<Object, Object> arguments) {
			super.configure(selectManyCheckbox, arguments);
			if(StringHelper.isBlank(selectManyCheckbox.layout))
				selectManyCheckbox.layout = "lineDirection";
			if(selectManyCheckbox.columns == null)
				selectManyCheckbox.columns = 0;
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