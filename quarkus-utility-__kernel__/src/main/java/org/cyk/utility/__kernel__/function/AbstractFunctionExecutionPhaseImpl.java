package org.cyk.utility.__kernel__.function;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractFunctionExecutionPhaseImpl extends AbstractObject implements FunctionExecutionPhase, Serializable {
	private static final long serialVersionUID = 1L;

	private FunctionExecutionPhaseMomentBegin begin;
	private FunctionExecutionPhaseMomentRun run;
	private FunctionExecutionPhaseMomentEnd end;
	private Boolean isCodeFromFunctionExecutable;
	
	@Override
	public FunctionExecutionPhaseMomentBegin getBegin() {
		return begin;
	}
	
	@Override
	public FunctionExecutionPhaseMomentBegin getBegin(Boolean injectIfNull) {
		FunctionExecutionPhaseMomentBegin begin = getBegin();
		if(begin == null && Boolean.TRUE.equals(injectIfNull))
			setBegin(begin = ____inject____(FunctionExecutionPhaseMomentBegin.class));
		return begin;
	}
	
	@Override
	public FunctionExecutionPhase setBegin(FunctionExecutionPhaseMomentBegin begin) {
		this.begin = begin;
		if(this.begin!=null)
			this.begin.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMomentBegin begin() {
		return getBegin(Boolean.TRUE);
	}
	
	@Override
	public FunctionExecutionPhaseMomentRun getRun() {
		return run;
	}
	
	@Override
	public FunctionExecutionPhaseMomentRun getRun(Boolean injectIfNull) {
		FunctionExecutionPhaseMomentRun run = getRun();
		if(run == null && Boolean.TRUE.equals(injectIfNull))
			setRun(run = ____inject____(FunctionExecutionPhaseMomentRun.class));
		return run;
	}
	
	@Override
	public FunctionExecutionPhase setRun(FunctionExecutionPhaseMomentRun run) {
		this.run = run;
		if(this.run!=null)
			this.run.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMomentRun run() {
		return getRun(Boolean.TRUE);
	}
	
	@Override
	public FunctionExecutionPhaseMomentEnd getEnd() {
		return end;
	}
	
	@Override
	public FunctionExecutionPhaseMomentEnd getEnd(Boolean injectIfNull) {
		FunctionExecutionPhaseMomentEnd end = getEnd();
		if(end == null && Boolean.TRUE.equals(injectIfNull))
			setEnd(end = ____inject____(FunctionExecutionPhaseMomentEnd.class));
		return end;
	}
	
	@Override
	public FunctionExecutionPhase setEnd(FunctionExecutionPhaseMomentEnd end) {
		this.end = end;
		if(this.end!=null)
			this.end.setParent(this);
		return this;
	}
	
	@Override
	public FunctionExecutionPhaseMomentEnd end() {
		return getEnd(Boolean.TRUE);
	}
	
	@Override
	public Boolean getIsCodeFromFunctionExecutable() {
		return isCodeFromFunctionExecutable;
	}
	
	@Override
	public FunctionExecutionPhase setIsCodeFromFunctionExecutable(Boolean value) {
		this.isCodeFromFunctionExecutable = value;
		return this;
	}
	
	@Override
	public Function<?, ?> getParent() {
		return (Function<?, ?>) super.getParent();
	}
}
