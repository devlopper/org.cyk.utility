package org.cyk.utility.client.controller.event;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.programming.script.ScriptBuilder;

public abstract class AbstractEventBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Event> implements EventBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private EventName name;
	private ScriptBuilder script;
	private CommandFunction function;
	private Objects updatables;
	
	@Override
	protected Event __execute__() throws Exception {
		Event event = __inject__(Event.class);
		EventName name = getName();
		event.setName(name);
		ScriptBuilder script = getScript();
		if(script!=null)
			event.setScript(script.execute().getOutput());
		event.getProperties().copyNonNullKeysFrom(getOutputProperties());
		Function<?, ?> function = getFunction();
		event.setFunction(function);
		Objects updatables = getUpdatables();
		event.setUpdatables(updatables);
		return event;
	}

	@Override
	public EventName getName() {
		return name;
	}

	@Override
	public EventBuilder setName(EventName name) {
		this.name = name;
		return this;
	}
	
	@Override
	public ScriptBuilder getScript() {
		return script;
	}
	
	@Override
	public ScriptBuilder getScript(Boolean injectIfNull) {
		if(script == null && Boolean.TRUE.equals(injectIfNull))
			script = __inject__(ScriptBuilder.class);
		return script;
	}
	
	@Override
	public EventBuilder setScript(ScriptBuilder script) {
		this.script = script;
		return this;
	}
	
	@Override
	public EventBuilder addScriptInstructions(Collection<String> strings) {
		getScript(Boolean.TRUE).getInstructions(Boolean.TRUE).add(strings);
		return this;
	}
	
	@Override
	public EventBuilder addScriptInstructions(String... strings) {
		getScript(Boolean.TRUE).getInstructions(Boolean.TRUE).add(strings);
		return this;
	}
	
	@Override
	public CommandFunction getFunction() {
		return function;
	}
	
	@Override
	public CommandFunction getFunction(Boolean injectIfNull) {
		if(function == null && Boolean.TRUE.equals(injectIfNull))
			function = __inject__(CommandFunction.class);
		return function;
	}

	@Override
	public EventBuilder setFunction(CommandFunction function) {
		this.function = function;
		return this;
	}
	
	@Override
	public Objects getUpdatables() {
		return updatables;
	}
	
	@Override
	public Objects getUpdatables(Boolean injectIfNull) {
		if(updatables == null && Boolean.TRUE.equals(injectIfNull))
			updatables = __inject__(Objects.class);
		return updatables;
	}
	
	@Override
	public EventBuilder setUpdatables(Objects updatables) {
		this.updatables = updatables;
		return this;
	}
	
	@Override
	public EventBuilder addUpdatables(Collection<Object> updatables) {
		getUpdatables(Boolean.TRUE).add(updatables);
		return this;
	}
	
	@Override
	public EventBuilder addUpdatables(Object... updatables) {
		getUpdatables(Boolean.TRUE).add(updatables);
		return this;
	}
	
	/**/
	
	public static final String FIELD_SCRIPT = "script";
	public static final String FIELD_FUNCTION = "function";
	public static final String FIELD_UPDATABLES = "updatables";
	
}
