package org.cyk.utility.__kernel__.script;

import java.io.Serializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ScriptEngineGetter {

	ScriptEngine get(Arguments arguments);
	ScriptEngine get();
	
	public static abstract class AbstractImpl extends AbstractObject implements ScriptEngineGetter,Serializable {				
		
		public static String DEFAULT_NAME = "js";
		
		@Override
		public ScriptEngine get(Arguments arguments) {
			if(arguments == null)
				arguments = new Arguments();
			String name = arguments.name;
			if(StringHelper.isBlank(name))
				name = ValueHelper.defaultToIfBlank(ValueHelper.defaultToIfBlank(name, DEFAULT_NAME),"js");
			ScriptEngineManager scriptEngineManager = ScriptEngineManagerGetter.getInstance().get();
	        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName(name);
	        return scriptEngine;
		}
		
		@Override
		public ScriptEngine get() {
			return get(new Arguments().setName(DEFAULT_NAME));
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public class Arguments extends AbstractObject implements Serializable {
		private String name;
		
	}
	
	/**/
	
	static ScriptEngineGetter getInstance() {
		return Helper.getInstance(ScriptEngineGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}