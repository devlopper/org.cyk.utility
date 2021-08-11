package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueConverter;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
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
	
	@Override
	public Object deriveBinding(String beanPath) {
		org.primefaces.component.selectoneradio.SelectOneRadio component = new org.primefaces.component.selectoneradio.SelectOneRadio();
		if(StringHelper.isNotBlank(styleClass))
			component.setStyleClass(styleClass);
		if(StringHelper.isNotBlank(style))
			component.setStyle(style);
		component.setSelectItems((List<SelectItem>)CollectionHelper.cast(SelectItem.class, choices));
		for(Object[] array : new Object[][] {
				/*new Object[] {FIELD_DECIMAL_PLACES,null,String.class}
				,new Object[] {FIELD_DECIMAL_SEPARATOR,null,String.class}
				,new Object[] {FIELD_THOUSAND_SEPARATOR,null,String.class}
				,new Object[] {FIELD_MIN_VALUE,null,String.class}
				,new Object[] {FIELD_MAX_VALUE,null,String.class}
				,*/new Object[] {FIELD_RENDERED,null,Boolean.class}
			}) {
			String property = array[1] == null ? (String) array[0] : (String) array[1];
			Class<?> klass = array[2] == null ? String.class : (Class<?>) array[2];
			__inject__(JavaServerFacesHelper.class).setValueExpression(component, property, JavaServerFacesHelper.buildValueExpression(String.format("#{%s.%s}",beanPath,array[0]), klass));
		}
		return component;
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