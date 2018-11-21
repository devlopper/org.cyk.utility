package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.internationalization.InternalizationStringBuilder;

public abstract class AbstractOutputStringBuilderImpl<OUTPUT extends OutputString> extends AbstractOutputBuilderImpl<OUTPUT,String> implements OutputStringBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	private String valueInternalizationIdentifier;
	
	@Override
	protected void __execute__(OUTPUT output, Object object, Field field) {
		super.__execute__(output, object, field);
		String value = output.getValue();
		if(__injectStringHelper__().isBlank(value)) {
			String valueInternalizationIdentifier = getValueInternalizationIdentifier();
			if(__injectStringHelper__().isNotBlank(valueInternalizationIdentifier)) {
				output.setValue(__inject__(InternalizationStringBuilder.class).setKeyValue(valueInternalizationIdentifier).execute().getOutput());
			}
		}
	}
	
	@Override
	public String getValueInternalizationIdentifier() {
		return valueInternalizationIdentifier;
	}
	
	@Override
	public OutputStringBuilder<OUTPUT> setValueInternalizationIdentifier(String valueInternalizationIdentifier) {
		this.valueInternalizationIdentifier = valueInternalizationIdentifier;
		return this;
	}
	
	@Override
	protected String __getValue__(Object object, Field field, Object value) {
		return value == null ? null : value.toString();
	}
	
}
