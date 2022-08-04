package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.client.controller.web.jsf.converter.NumberConverter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputNumber extends AbstractInput<Number> implements Serializable {

	private Integer decimalPlaces;
	private String decimalSeparator,thousandSeparator,inputStyle,inputStyleClass;
	private Number minValue,maxValue;
	
	/**/
	
	@Override
	public Object deriveBinding(String beanPath) {
		org.primefaces.component.inputnumber.InputNumber component = new org.primefaces.component.inputnumber.InputNumber();
		if(StringHelper.isNotBlank(styleClass))
			component.setStyleClass(styleClass);
		if(StringHelper.isNotBlank(style))
			component.setStyle(style);
		
		if(StringHelper.isNotBlank(inputStyleClass))
			component.setInputStyleClass(inputStyleClass);
		if(StringHelper.isNotBlank(inputStyle))
			component.setInputStyle(inputStyle);
		
		if(disabled != null)
			component.setDisabled(disabled);
		if(readOnly != null)
			component.setReadonly(readOnly);
		
		if(decimalPlaces != null)
			component.setDecimalPlaces(decimalPlaces.toString());
		if(minValue != null)
			component.setMinValue(minValue.toString());
		if(maxValue != null)
			component.setMaxValue(maxValue.toString());
		if(StringHelper.isNotBlank(decimalSeparator))
			component.setDecimalSeparator(decimalSeparator);
		if(StringHelper.isNotBlank(thousandSeparator))
			component.setThousandSeparator(thousandSeparator);
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
	
	public static final String FIELD_INPUT_STYLE = "inputStyle";
	public static final String FIELD_INPUT_STYLE_CLASS = "inputStyleClass";
	public static final String FIELD_DECIMAL_PLACES = "decimalPlaces";
	public static final String FIELD_DECIMAL_SEPARATOR = "decimalSeparator";
	public static final String FIELD_THOUSAND_SEPARATOR = "thousandSeparator";
	public static final String FIELD_MIN_VALUE = "minValue";
	public static final String FIELD_MAX_VALUE = "maxValue";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<InputNumber> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(InputNumber inputNumber, Map<Object, Object> arguments) {
			super.configure(inputNumber, arguments);
			if(inputNumber.decimalPlaces == null) {
				if(inputNumber.field != null) {
					if(inputNumber.field.getType().equals(Byte.class) || inputNumber.field.getType().equals(Short.class)
							|| inputNumber.field.getType().equals(Integer.class) || inputNumber.field.getType().equals(Long.class))
						inputNumber.decimalPlaces = 0;
					else
						inputNumber.decimalPlaces = 2;
				}
			}
			
			if(StringHelper.isBlank(inputNumber.decimalSeparator)) {
				inputNumber.decimalSeparator = ".";
			}
			
			if(StringHelper.isBlank(inputNumber.thousandSeparator)) {
				inputNumber.thousandSeparator = " ";
			}
			
			if(inputNumber.minValue == null)
				inputNumber.minValue = 0;
			
			if(inputNumber.maxValue == null)
				inputNumber.maxValue = Integer.MAX_VALUE;
			
			if(inputNumber.converter == null) {
				Class<?> klass = null;
				if(inputNumber.field != null) {
					klass = inputNumber.field.getType();
				}
				if(klass == null)
					klass = (Class<?>) MapHelper.readByKey(arguments, FIELD_NUMBER_CLASS);
				if(ClassHelper.isInstanceOfNumber(klass))
					inputNumber.converter = new NumberConverter((Class<? extends Number>) klass);
			}
			
		}
		
		@Override
		protected String __getTemplate__(InputNumber inputNumber, Map<Object, Object> arguments) {
			return "/input/number/default.xhtml";
		}
		
		@Override
		protected Class<InputNumber> __getClass__() {
			return InputNumber.class;
		}
		
		/**/
		
		public static final String FIELD_NUMBER_CLASS = "numberClass";
	}
	
	public static InputNumber build(Map<Object, Object> arguments) {
		return Builder.build(InputNumber.class,arguments);
	}
	
	public static InputNumber build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	public static InputNumber buildFromArray(Object...objects) {
		return build(objects);
	}

	static {
		Configurator.set(InputNumber.class, new ConfiguratorImpl());
	}
}
