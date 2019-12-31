package org.cyk.utility.__kernel__.string;

import java.io.File;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface StringGenerator {

	String generate(String templateString,Map<String,Object> arguments);
	
	String generate(File templateFile,Map<String,Object> arguments);
	
	/**/
	
	static StringGenerator getInstance() {
		return Helper.getInstance(StringGenerator.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
