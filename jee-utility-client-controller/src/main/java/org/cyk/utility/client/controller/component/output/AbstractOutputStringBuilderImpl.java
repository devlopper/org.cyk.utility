package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationString;
import org.cyk.utility.__kernel__.string.StringHelper;

public abstract class AbstractOutputStringBuilderImpl<OUTPUT extends OutputString> extends AbstractOutputBuilderImpl<OUTPUT,String> implements OutputStringBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	protected InternationalizationString valueInternationalizationString;
	
	@Override
	protected void __execute__(OUTPUT output, Object object, Field field) {
		super.__execute__(output, object, field);
		String value = output.getValue();
		if(StringHelper.isBlank(value)) {
			InternationalizationString valueInternationalizationString = getValueInternationalizationString();
			if(valueInternationalizationString!=null) {
				InternationalizationHelper.processStrings(valueInternationalizationString);
				output.setValue(valueInternationalizationString.getValue());
			}
		}
	}
	
	@Override
	public InternationalizationString getValueInternationalizationString() {
		return valueInternationalizationString;
	}
	
	@Override
	public InternationalizationString getValueInternationalizationString(Boolean injectIfNull) {
		if(valueInternationalizationString == null && Boolean.TRUE.equals(injectIfNull)) {
			valueInternationalizationString = __inject__(InternationalizationString.class);
		}
		return valueInternationalizationString;
	}
	
	@Override
	public OutputStringBuilder<OUTPUT> setValueInternationalizationString(InternationalizationString valueInternationalizationString) {
		this.valueInternationalizationString = valueInternationalizationString;
		return this;
	}
	
	@Override
	protected String __getValue__(Object object, Field field, Object value) {
		return value == null ? null : value.toString();
	}
	
}
