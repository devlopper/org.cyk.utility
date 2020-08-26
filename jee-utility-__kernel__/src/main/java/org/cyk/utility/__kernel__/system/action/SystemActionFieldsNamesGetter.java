package org.cyk.utility.__kernel__.system.action;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface SystemActionFieldsNamesGetter extends SystemActionXXXGetter<String> {
	
	static SystemActionFieldsNamesGetter getInstance() {
		return Helper.getInstance(SystemActionFieldsNamesGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
