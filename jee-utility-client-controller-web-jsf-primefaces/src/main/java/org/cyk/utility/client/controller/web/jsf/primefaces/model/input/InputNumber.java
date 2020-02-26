package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.converter.NumberConverter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputNumber extends AbstractInput<Number> implements Serializable {

	private Integer decimalPlaces;
	private String decimalSeparator,thousandSeparator;
	private Number minValue,maxValue;
	
	/**/
	
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
		protected String __getTemplate__() {
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

	static {
		Configurator.set(InputNumber.class, new ConfiguratorImpl());
	}
}
