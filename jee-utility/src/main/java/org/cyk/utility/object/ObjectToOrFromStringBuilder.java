package org.cyk.utility.object;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.string.Strings;

public interface ObjectToOrFromStringBuilder<OUTPUT> extends FunctionWithPropertiesAsInput<OUTPUT> {

	Strings getFieldNamesStrings();
	Strings getFieldNamesStrings(Boolean injectIfNull);
	ObjectToOrFromStringBuilder<OUTPUT> setFieldNamesStrings(Strings fieldNamesStrings);
	ObjectToOrFromStringBuilder<OUTPUT> addFieldNamesStrings(Collection<String> fieldNamesStrings);
	ObjectToOrFromStringBuilder<OUTPUT> addFieldNamesStrings(String...fieldNamesStrings);
}
