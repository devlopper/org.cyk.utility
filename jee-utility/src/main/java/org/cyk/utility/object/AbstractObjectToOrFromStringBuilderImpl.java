package org.cyk.utility.object;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

public abstract class AbstractObjectToOrFromStringBuilderImpl<OUTPUT> extends AbstractFunctionWithPropertiesAsInputImpl<OUTPUT> implements ObjectToOrFromStringBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

	private Strings fieldNamesStrings;
	
	@Override
	protected OUTPUT __execute__() throws Exception {
		Strings fieldNamesStrings = getFieldNamesStrings();
		return __execute__(fieldNamesStrings);
	}
	
	protected abstract OUTPUT __execute__(Strings fieldNamesStrings) throws Exception;
	
	@Override
	public Strings getFieldNamesStrings() {
		return fieldNamesStrings;
	}

	@Override
	public Strings getFieldNamesStrings(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_FIELD_NAMES_STRINGS, injectIfNull);
	}

	@Override
	public ObjectToOrFromStringBuilder<OUTPUT> setFieldNamesStrings(Strings fieldNamesStrings) {
		this.fieldNamesStrings = fieldNamesStrings;
		return this;
	}

	@Override
	public ObjectToOrFromStringBuilder<OUTPUT> addFieldNamesStrings(Collection<String> fieldNamesStrings) {
		getFieldNamesStrings(Boolean.TRUE).add(fieldNamesStrings);
		return this;
	}

	@Override
	public ObjectToOrFromStringBuilder<OUTPUT> addFieldNamesStrings(String... fieldNamesStrings) {
		getFieldNamesStrings(Boolean.TRUE).add(fieldNamesStrings);
		return this;
	}
	
	/**/
	
	public static final String FIELD_FIELD_NAMES_STRINGS = "fieldNamesStrings";
	
}
