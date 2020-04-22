package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectOneCombo extends AbstractInputChoiceOne implements Serializable {

	private Boolean filter;
	private String filterMatchMode;
	/**/
	
	public static final String FIELD_FILTER = "filter";
	public static final String FIELD_FILTER_MATCH_MODE = "filterMatchMode";
	/**/
	
	/**/

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectOneCombo> implements Serializable {

		protected Object autoComplete;

		@SuppressWarnings("unchecked")
		@Override
		public void configure(SelectOneCombo selectOneCombo, Map<Object, Object> arguments) {
			super.configure(selectOneCombo, arguments);
			if(selectOneCombo.filter == null)
				selectOneCombo.filter = Boolean.TRUE;
			if(StringHelper.isBlank(selectOneCombo.filterMatchMode))
				selectOneCombo.filterMatchMode = "contains";			
			selectOneCombo.addAjaxes(Map.of(Ajax.FIELD_EVENT,"itemSelect"));
		}
		
		@Override
		protected Class<SelectOneCombo> __getClass__() {
			return SelectOneCombo.class;
		}
		
		@Override
		protected String __getTemplate__(SelectOneCombo selectOneCombo, Map<Object, Object> arguments) {
			return "/input/select/one/selectOneMenu.xhtml";
		}
	}
	
	public static SelectOneCombo build(Map<Object, Object> arguments) {
		return Builder.build(SelectOneCombo.class,arguments);
	}
	
	public static SelectOneCombo build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	/**/

	static {
		Configurator.set(SelectOneCombo.class, new ConfiguratorImpl());
	}
}