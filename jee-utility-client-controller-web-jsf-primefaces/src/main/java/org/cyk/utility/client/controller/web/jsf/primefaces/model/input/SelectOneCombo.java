package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Collection;
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
	
	@Override
	public SelectOneCombo enableValueChangeListener(Collection<Object> updatables) {
		return (SelectOneCombo) super.enableValueChangeListener(updatables);
	}
	
	@Override
	public SelectOneCombo selectFirstChoice() {
		return (SelectOneCombo) super.selectFirstChoice();
	}
	
	@Override
	public void setReadOnly(Boolean readOnly) {
		super.setReadOnly(readOnly);
		setDisabled(readOnly);
	}
	
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
			selectOneCombo.addAjaxes(Map.of(Ajax.FIELD_EVENT,"valueChange",Ajax.FIELD_DISABLED,Boolean.TRUE)
					,Map.of(Ajax.FIELD_EVENT,"itemSelect",Ajax.FIELD_DISABLED,Boolean.TRUE));
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
	
	public static SelectOneCombo buildUnknownYesNoOnly(Boolean value,String outputLabelValue) {
		SelectOneCombo input = SelectOneCombo.build(SelectOneCombo.FIELD_VALUE,value,SelectOneCombo.ConfiguratorImpl.FIELD_CHOICES_ARE_UNKNOWN_YES_NO_ONLY,Boolean.TRUE
				,SelectOneCombo.ConfiguratorImpl.FIELD_OUTPUT_LABEL_VALUE,outputLabelValue);
		//input.setValueAsFirstChoiceIfNull();
		return input;
	}
	
	public static SelectOneCombo buildYesNoOnly(Boolean value,String outputLabelValue) {
		SelectOneCombo input = SelectOneCombo.build(SelectOneCombo.FIELD_VALUE,value,SelectOneCombo.ConfiguratorImpl.FIELD_CHOICES_ARE_YES_NO_ONLY,Boolean.TRUE
				,SelectOneCombo.ConfiguratorImpl.FIELD_OUTPUT_LABEL_VALUE,outputLabelValue);
		//input.setValueAsFirstChoiceIfNull();
		return input;
	}
	
	/**/

	static {
		Configurator.set(SelectOneCombo.class, new ConfiguratorImpl());
	}
}