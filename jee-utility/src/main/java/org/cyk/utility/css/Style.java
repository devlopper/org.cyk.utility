package org.cyk.utility.css;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.string.Strings;

public interface Style extends Objectable {

	Strings getClasses();
	Strings getClasses(Boolean injectIfNull);
	Style setClasses(Strings classes);
	String getClassesAsString();
	
	Strings getValues();
	Strings getValues(Boolean injectIfNull);
	Style setValues(Strings values);
	String getValuesAsString();
}
