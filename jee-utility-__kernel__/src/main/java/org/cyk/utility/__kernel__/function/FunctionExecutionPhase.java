package org.cyk.utility.__kernel__.function;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionExecutionPhase extends Objectable {

	FunctionExecutionPhaseMomentBegin getBegin();
	FunctionExecutionPhase setBegin(FunctionExecutionPhaseMomentBegin begin);
	
	FunctionExecutionPhaseMomentEnd getEnd();
	FunctionExecutionPhase setEnd(FunctionExecutionPhaseMomentEnd end);
}
