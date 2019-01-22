package org.cyk.utility.programming.script;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class ScriptImpl extends AbstractObject implements Script,Serializable {
	private static final long serialVersionUID = 1L;

	private String codeSource;
	
	@Override
	public String getCodeSource() {
		return codeSource;
	}

	@Override
	public Script setCodeSource(String codeSource) {
		this.codeSource = codeSource;
		return this;
	}

	
	
}
