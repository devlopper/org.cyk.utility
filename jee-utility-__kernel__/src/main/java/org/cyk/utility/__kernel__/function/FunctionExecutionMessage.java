package org.cyk.utility.__kernel__.function;

import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionExecutionMessage extends Objectable {
	
	String getFunction();
	FunctionExecutionMessage setFunction(String function);
	
	Map<String,String> getInputs();
	FunctionExecutionMessage setInputs(Map<String,String> inputs);
	
	Map<String,String> getOutputs();
	FunctionExecutionMessage setOutputs(Map<String,String> outputs);
	
	/**/
	
	String PROPERTY_FUNCTION = "function";
	String PROPERTY_INPUTS = "inputs";
	String PROPERTY_OUTPUTS = "outputs";
	
}
