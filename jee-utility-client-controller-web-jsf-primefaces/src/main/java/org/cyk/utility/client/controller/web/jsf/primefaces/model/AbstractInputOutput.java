package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInputOutput<VALUE> extends AbstractObjectAjaxable implements Serializable {

	protected VALUE value;
	
	protected Object object;
	protected Field field;
	
	/**/
	
	@SuppressWarnings("unchecked")
	public AbstractInputOutput<VALUE> readValueFromField() {
		if(object != null && field != null)
			value = (VALUE) FieldHelper.read(object, field);
		return this;
	}
	
	public AbstractObject setBindingByDerivation(String beanPath,String valuePath) {
		setBindingByDerivation(beanPath);
		UIComponent component = (UIComponent) binding;
		__inject__(JavaServerFacesHelper.class).setValueExpression(component, "value", JavaServerFacesHelper.buildValueExpression("#{"+valuePath+"}", Object.class));
		return this;
	}
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_FIELD = "field";
	public static final String FIELD_OBJECT = "object";
	
	/**/
	
	/**/
	
	@SuppressWarnings("rawtypes")
	public static abstract class AbstractConfiguratorImpl<IO extends AbstractInputOutput> extends AbstractObjectAjaxable.AbstractConfiguratorImpl<IO> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(IO io, Map<Object, Object> arguments) {
			super.configure(io, arguments);
			if(io.field == null) {
				String fieldName = (String) MapHelper.readByKey(arguments, FIELD_FIELD_NAME);
				if(StringHelper.isNotBlank(fieldName)) {
					Object object = MapHelper.readByKey(arguments, FIELD_OBJECT);
					if(object != null) {
						io.object = object;
						io.field = FieldHelper.getByName(object.getClass(), fieldName);
					}
				}
			}			
			io.readValueFromField();
		}
		
		public static final String FIELD_OBJECT = "object";
		public static final String FIELD_FIELD_NAME = "fieldName";
	}
	
	/**/
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.TYPE})
	public static @interface Annotation {
		
	}
}
