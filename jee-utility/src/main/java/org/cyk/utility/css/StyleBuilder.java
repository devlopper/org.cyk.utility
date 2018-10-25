package org.cyk.utility.css;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.string.Strings;

public interface StyleBuilder extends FunctionWithPropertiesAsInput<Style> {

	Strings getClasses();
	Strings getClasses(Boolean injectIfNull);
	StyleBuilder setClasses(Strings classes);
	StyleBuilder addClasses(String...classes);
	
	Strings getValues();
	Strings getValues(Boolean injectIfNull);
	StyleBuilder setValues(Strings values);
	StyleBuilder addValues(String...values);
	
}
