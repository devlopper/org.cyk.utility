package org.cyk.utility.programming.script;

import java.io.Serializable;

import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

public class ScriptBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Script> implements ScriptBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private Strings instructions;
	
	@Override
	protected Script __execute__() throws Exception {
		Script script = __inject__(Script.class);
		Strings instructions = getInstructions();
		if(instructions!=null)
			script.setCodeSource(instructions.concatenate(ConstantCharacter.SEMI_COLON));
		return script;
	}
	
	@Override
	public Strings getInstructions() {
		return instructions;
	}

	@Override
	public Strings getInstructions(Boolean injectIfNull) {
		return (Strings) __getInjectIfNull__(FIELD_INSTRUCTIONS, injectIfNull);
	}

	@Override
	public ScriptBuilder setInstructions(Strings instructions) {
		this.instructions = instructions;
		return this;
	}

	public static final String FIELD_INSTRUCTIONS = "instructions";
	
}
