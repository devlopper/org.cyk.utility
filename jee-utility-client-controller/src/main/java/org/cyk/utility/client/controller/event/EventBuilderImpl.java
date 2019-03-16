package org.cyk.utility.client.controller.event;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.programming.script.ScriptBuilder;

public class EventBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Event> implements EventBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private EventName name;
	private ScriptBuilder script;
	private CommandFunction function;
	
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
		return (ScriptBuilder) __getInjectIfNull__(FIELD_SCRIPT, injectIfNull);
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
		return (CommandFunction) __getInjectIfNull__(FIELD_FUNCTION, injectIfNull);
	}

	@Override
	public EventBuilder setFunction(CommandFunction function) {
		this.function = function;
		return this;
	}
	
	/**/
	
	public static final String FIELD_SCRIPT = "script";
	public static final String FIELD_FUNCTION = "function";
	
}
