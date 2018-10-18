package org.cyk.utility.client.controller.component;

import java.lang.reflect.Field;

public interface InputOutputBuilder<INPUT_OUTPUT extends InputOutput<VALUE>,VALUE> extends VisibleComponentBuilder<INPUT_OUTPUT> {

	Object getObject();
	InputOutputBuilder<INPUT_OUTPUT,VALUE> setObject(Object object);
	
	Field getField();
	InputOutputBuilder<INPUT_OUTPUT,VALUE> setField(Field field);
	
	VALUE getValue();
	InputOutputBuilder<INPUT_OUTPUT, VALUE> setValue(VALUE value);
	
}
