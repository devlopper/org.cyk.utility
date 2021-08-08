package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SelectOneRadio extends AbstractInputChoiceOne implements Serializable {

	private Boolean unselectable;
	
	/**/
	
	@Override
	public void setReadOnly(Boolean readOnly) {
		super.setReadOnly(readOnly);
		setDisabled(readOnly);
	}
		
	@Override
	protected void writeValueToObjectField(Object value) {
		if(ClassHelper.isInstanceOf(field.getType(), Boolean.class) && value instanceof String)
			value = ValueConverter.getInstance().convertToBoolean(value);
		super.writeValueToObjectField(value);
	}
	
	/**/
	
	public static final String FIELD_UNSELECTABLE = "unselectable";
	
	/**/

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<SelectOneRadio> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(SelectOneRadio selectOneRadio, Map<Object, Object> arguments) {
			super.configure(selectOneRadio, arguments);
			selectOneRadio.addAjaxes(Map.of(Ajax.FIELD_EVENT,"change",Ajax.FIELD_DISABLED,Boolean.TRUE));
		}
		
		@Override
		protected Class<SelectOneRadio> __getClass__() {
			return SelectOneRadio.class;
		}
		
		@Override
		protected String __getTemplate__(SelectOneRadio selectOneRadio, Map<Object, Object> arguments) {
			return "/input/select/one/selectOneRadio.xhtml";
		}
	}
	
	public static SelectOneRadio build(Map<Object, Object> arguments) {
		return Builder.build(SelectOneRadio.class,arguments);
	}
	
	public static SelectOneRadio build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	/**/

	static {
		Configurator.set(SelectOneRadio.class, new ConfiguratorImpl());
	}
}