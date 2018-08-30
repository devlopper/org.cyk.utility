package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractFunctionExecutionPhaseImpl extends AbstractObject implements FunctionExecutionPhase, Serializable {
	private static final long serialVersionUID = 1L;

	private FunctionExecutionPhaseMomentBegin begin;
	private FunctionExecutionPhaseMomentEnd end;
	
	@Override
	public FunctionExecutionPhaseMomentBegin getBegin() {
		return begin;
	}
	
	@Override
	public FunctionExecutionPhase setBegin(FunctionExecutionPhaseMomentBegin begin) {
		this.begin = begin;
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMomentEnd getEnd() {
		return end;
	}
	
	@Override
	public FunctionExecutionPhase setEnd(FunctionExecutionPhaseMomentEnd end) {
		this.end = end;
		return this;
	}
}
