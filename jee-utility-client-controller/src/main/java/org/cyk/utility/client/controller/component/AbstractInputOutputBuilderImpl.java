package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

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
	
}
