package org.cyk.utility.bean;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.string.Strings;

public interface PropertyValueGetter extends FunctionWithPropertiesAsInput<Object> {

	PropertyValueGetter setObject(Object object);
	Object getObject();
	
	PropertyValueGetter setPathStrings(Strings pathStrings);
	PropertyValueGetter addPathStrings(Collection<String> strings);
	PropertyValueGetter addPathStrings(String...strings);
	Strings getPathStrings();
	Strings getPathStrings(Boolean injectIfNull);
	
	PropertyValueGetter setProperty(Property property);
	Property getProperty();
}
