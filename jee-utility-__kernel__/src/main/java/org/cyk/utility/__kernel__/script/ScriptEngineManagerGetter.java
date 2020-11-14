package org.cyk.utility.__kernel__.script;

import java.io.Serializable;

import javax.script.ScriptEngineManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface ScriptEngineManagerGetter {

	ScriptEngineManager get();
	
	public static abstract class AbstractImpl extends AbstractObject implements ScriptEngineManagerGetter,Serializable {				
		private static ScriptEngineManager INSTANCE;
		
		@Override
		public ScriptEngineManager get() {
			if(INSTANCE == null)
				INSTANCE = new ScriptEngineManager();
			return INSTANCE;
		}
	}
	
	/**/
	
	static ScriptEngineManagerGetter getInstance() {
		return Helper.getInstance(ScriptEngineManagerGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
