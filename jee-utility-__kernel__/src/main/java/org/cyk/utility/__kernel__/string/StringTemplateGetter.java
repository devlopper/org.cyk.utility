package org.cyk.utility.__kernel__.string;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface StringTemplateGetter {

	Object get(String identifier);
	
	/**/
	
	static StringTemplateGetter getInstance() {
		return Helper.getInstance(StringTemplateGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
