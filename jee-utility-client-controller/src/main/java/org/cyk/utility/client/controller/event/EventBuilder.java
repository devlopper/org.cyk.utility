package org.cyk.utility.client.controller.event;

import java.util.Collection;

import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.programming.script.ScriptBuilder;

public interface EventBuilder extends FunctionWithPropertiesAsInput<Event> {

	EventName getName();
	EventBuilder setName(EventName name);
	
	ScriptBuilder getScript();
	ScriptBuilder getScript(Boolean injectIfNull);
	EventBuilder setScript(ScriptBuilder script);
	
	EventBuilder addScriptInstructions(Collection<String> strings);
	EventBuilder addScriptInstructions(String...strings);
	
	CommandFunction getFunction();
	CommandFunction getFunction(Boolean injectIfNull);
	EventBuilder setFunction(CommandFunction function);
	
	Objects getUpdatables();
	Objects getUpdatables(Boolean injectIfNull);
	EventBuilder setUpdatables(Objects updatables);
	EventBuilder addUpdatables(Collection<Object> updatables);
	EventBuilder addUpdatables(Object...updatables);
}
