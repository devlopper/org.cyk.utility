package org.cyk.utility.client.controller.event;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.programming.script.ScriptBuilder;

public interface EventBuilder extends FunctionWithPropertiesAsInput<Event> {

	EventName getName();
	EventBuilder setName(EventName name);
	
	ScriptBuilder getScript();
	ScriptBuilder getScript(Boolean injectIfNull);
	EventBuilder setScript(ScriptBuilder script);
	
	EventBuilder addScriptInstructions(Collection<String> strings);
	EventBuilder addScriptInstructions(String...strings);
	
}
