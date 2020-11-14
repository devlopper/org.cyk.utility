package org.cyk.utility.__kernel__.script;

import java.io.Serializable;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ScriptExecutor {

	<RESULT> RESULT execute(Class<RESULT> resultClass,Arguments<RESULT> arguments);
	<RESULT> RESULT execute(Class<RESULT> resultClass,String script,Object...variables);
	Object execute(String script,Object...variables);
	
	public static abstract class AbstractImpl extends AbstractObject implements ScriptExecutor,Serializable {
		
		public static String ENGINE_NAME = "";
		
		@Override
		public <RESULT> RESULT execute(Class<RESULT> resultClass, Arguments<RESULT> arguments) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("arguments", arguments);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("script", arguments.script);
			 ScriptEngine scriptEngine = ScriptEngineGetter.getInstance().get();
	        if(MapHelper.isNotEmpty(arguments.variables))
	        	for(Map.Entry<String, Object> entry : arguments.variables.entrySet())
	        		if(StringHelper.isNotBlank(entry.getKey()))
	        			scriptEngine.put(entry.getKey(), entry.getValue());
	        RESULT result;
			try {
				result = (RESULT) scriptEngine.eval(arguments.script);
			} catch (ScriptException exception) {
				throw new RuntimeException(exception);
			}
			return result;
		}
		
		@Override
		public <RESULT> RESULT execute(Class<RESULT> resultClass, String script, Object... variables) {
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("result class", resultClass);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("script", script);
			return execute(resultClass, new Arguments<RESULT>().setScript(script).setVariables(MapHelper.instantiateStringObject(variables)));
		}
		
		@Override
		public Object execute(String script, Object... variables) {
			return execute(Object.class, script, variables);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public class Arguments<RESULT> extends AbstractObject implements Serializable {
		private String engineName;
		private String script;
		private RESULT result;
		private Map<String,Object> variables;
	}
	
	/**/
	
	static ScriptExecutor getInstance() {
		return Helper.getInstance(ScriptExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
