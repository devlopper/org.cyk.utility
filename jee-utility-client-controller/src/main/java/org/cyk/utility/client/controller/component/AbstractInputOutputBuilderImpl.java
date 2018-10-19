package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.properties.Properties;

public abstract class AbstractInputOutputBuilderImpl<INPUT_OUTPUT extends InputOutput<VALUE>,VALUE> extends AbstractVisibleComponentBuilderImpl<INPUT_OUTPUT> implements InputOutputBuilder<INPUT_OUTPUT, VALUE> {
	private static final long serialVersionUID = 1L;

	protected Object object;
	protected Field field;
	protected VALUE value;
	
	@Override
	public Object getObject() {
		return object;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setObject(Object object) {
		this.object = object;
		return this;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setField(Field field) {
		this.field = field;
		return this;
	}
	
	@Override
	public VALUE getValue() {
		return value;
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT,VALUE> setValue(VALUE value) {
		this.value = value;
		return this;
	}
	
	@Override
	public Object getOutputPropertyValue() {
		return getOutputProperty(Properties.VALUE);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setOutputPropertyValue(Object value) {
		return setOutputProperty(Properties.VALUE,value);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setOutputProperty(Object key, Object value) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setOutputProperty(key, value);
	}

	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setAreaWidthProportions(Integer _default, Integer television,Integer desktop, Integer tablet, Integer phone) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setAreaWidthProportions(_default, television, desktop, tablet, phone);
	}
	
	@Override
	public InputOutputBuilder<INPUT_OUTPUT, VALUE> setAreaWidthProportionsForNotPhone(Integer width) {
		return (InputOutputBuilder<INPUT_OUTPUT, VALUE>) super.setAreaWidthProportionsForNotPhone(width);
	}
}
