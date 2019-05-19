package org.cyk.utility.function;

import java.util.Map;

import org.cyk.utility.helper.Helper;

public interface FunctionHelper extends Helper {

	FunctionHelper produce(String function,Map<String,String> inputs,Map<String,String> outputs);
	
}
