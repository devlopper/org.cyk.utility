package org.cyk.utility.__kernel__.function;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class FunctionExecutionMessageImpl extends AbstractObject implements FunctionExecutionMessage,Serializable {
	private static final long serialVersionUID = 1L;

	private String function;
	private Map<String, String> inputs,outputs;
	
	@Override
	public String getFunction() {
		return function;
	}

	@Override
	public FunctionExecutionMessage setFunction(String function) {
		this.function = function;
		return this;
	}

	@Override
	public Map<String, String> getInputs() {
		return inputs;
	}

	@Override
	public FunctionExecutionMessage setInputs(Map<String, String> inputs) {
		this.inputs = inputs;
		return this;
	}

	@Override
	public Map<String, String> getOutputs() {
		return outputs;
	}

	@Override
	public FunctionExecutionMessage setOutputs(Map<String, String> outputs) {
		this.outputs = outputs;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, getFunction(),inputs == null ? ConstantEmpty.STRING : inputs.toString()
				,outputs == null ? ConstantEmpty.STRING : ">"+outputs.toString());
	}

	/**/
	
	private static final String TO_STRING_FORMAT = "%s(%s)%s";
}
