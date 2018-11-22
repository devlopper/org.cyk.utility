package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.Case;

public abstract class AbstractOutputStringBuilderImpl<OUTPUT extends OutputString> extends AbstractOutputBuilderImpl<OUTPUT,String> implements OutputStringBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	private InternalizationStringBuilder valueInternalization;
	protected Case valueInternalizationCase;
	
	@Override
	protected void __execute__(OUTPUT output, Object object, Field field) {
		super.__execute__(output, object, field);
		String value = output.getValue();
		if(__injectStringHelper__().isBlank(value)) {
			InternalizationStringBuilder valueInternalization = getValueInternalization();
			if(valueInternalization!=null)
				output.setValue(valueInternalization.execute().getOutput());
		}
	}
	
	@Override
	public InternalizationStringBuilder getValueInternalization() {
		return valueInternalization;
	}
	
	@Override
	public InternalizationStringBuilder getValueInternalization(Boolean injectIfNull) {
		InternalizationStringBuilder internalization = (InternalizationStringBuilder) __getInjectIfNull__(FIELD_VALUE_INTERNALIZATION, injectIfNull);
		internalization.setCase(valueInternalizationCase);
		return internalization;
	}
	
	@Override
	public OutputStringBuilder<OUTPUT> setValueInternalization(InternalizationStringBuilder valueInternalization) {
		this.valueInternalization = valueInternalization;
		return this;
	}
	
	@Override
	public OutputStringBuilder<OUTPUT> setValueInternalizationKeyValue(String valueInternalizationKeyValue) {
		getValueInternalization(Boolean.TRUE).setKeyValue(valueInternalizationKeyValue);
		return this;
	}
	
	@Override
	protected String __getValue__(Object object, Field field, Object value) {
		return value == null ? null : value.toString();
	}
	
	private static final String FIELD_VALUE_INTERNALIZATION = "valueInternalization";
	
}
