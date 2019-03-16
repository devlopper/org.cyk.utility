package org.cyk.utility.client.controller.event;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.programming.script.Script;

public class EventImpl extends AbstractObject implements Event,Serializable {
	private static final long serialVersionUID = 1L;

	private EventName name;
	private Script script;
	private Function<?, ?> function;
	
	@Override
	public EventName getName() {
		return name;
	}

	@Override
	public Event setName(EventName name) {
		this.name = name;
		return this;
	}

	@Override
	public Script getScript() {
		return script;
	}

	@Override
	public Event setScript(Script script) {
		this.script = script;
		return this;
	}

	@Override
	public Function<?, ?> getFunction() {
		return function;
	}

	@Override
	public Event setFunction(Function<?, ?> function) {
		this.function = function;
		return this;
	}

}
