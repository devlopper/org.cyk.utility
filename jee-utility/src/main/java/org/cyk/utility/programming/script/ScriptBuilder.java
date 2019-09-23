package org.cyk.utility.programming.script;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.__kernel__.string.Strings;

public interface ScriptBuilder extends FunctionWithPropertiesAsInput<Script> {

	Strings getInstructions();
	Strings getInstructions(Boolean injectIfNull);
	ScriptBuilder setInstructions(Strings instructions);
	
}
