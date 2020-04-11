package org.cyk.utility.__kernel__.string;

import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface StringGenerator {

	String generate(Object template,Map<String,Object> arguments);
	
	/**/
	
	static StringGenerator getInstance() {
		return Helper.getInstance(StringGenerator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
