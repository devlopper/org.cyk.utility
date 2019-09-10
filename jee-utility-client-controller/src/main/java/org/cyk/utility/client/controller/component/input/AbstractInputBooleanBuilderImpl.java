package org.cyk.utility.client.controller.component.input;

import java.lang.reflect.Field;

public abstract class AbstractInputBooleanBuilderImpl<INPUT extends InputBoolean> extends AbstractInputBuilderImpl<INPUT,Boolean> implements InputBooleanBuilder<INPUT> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(INPUT input, Object object, Field field) {
		super.__execute__(input, object, field);
		input.setNullValue(__inject__(InputBooleanValue.class).setValue(null).setLabel(__buildInternationalizationString__("nospecified")));
		input.setFalseValue(__inject__(InputBooleanValue.class).setValue(Boolean.FALSE).setLabel(__buildInternationalizationString__("no")));
		input.setTrueValue(__inject__(InputBooleanValue.class).setValue(Boolean.TRUE).setLabel(__buildInternationalizationString__("yes")));
	}
	
}
