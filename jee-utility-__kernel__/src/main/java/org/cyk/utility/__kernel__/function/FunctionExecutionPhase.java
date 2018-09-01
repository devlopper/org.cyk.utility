package org.cyk.utility.__kernel__.function;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface FunctionExecutionPhase extends Objectable {

	FunctionExecutionPhaseMomentBegin getBegin();
	FunctionExecutionPhaseMomentBegin getBegin(Boolean injectIfNull);
	FunctionExecutionPhase setBegin(FunctionExecutionPhaseMomentBegin begin);
	FunctionExecutionPhaseMomentBegin begin();
	
	FunctionExecutionPhaseMomentRun getRun();
	FunctionExecutionPhaseMomentRun getRun(Boolean injectIfNull);
	FunctionExecutionPhase setRun(FunctionExecutionPhaseMomentRun run);
	FunctionExecutionPhaseMomentRun run();
	
	FunctionExecutionPhaseMomentEnd getEnd();
	FunctionExecutionPhaseMomentEnd getEnd(Boolean injectIfNull);
	FunctionExecutionPhase setEnd(FunctionExecutionPhaseMomentEnd end);
	FunctionExecutionPhaseMomentEnd end();
	
	Boolean getIsCodeFromFunctionExecutable();
	FunctionExecutionPhase setIsCodeFromFunctionExecutable(Boolean value);
	
	@Override Function<?,?> getParent();
}
