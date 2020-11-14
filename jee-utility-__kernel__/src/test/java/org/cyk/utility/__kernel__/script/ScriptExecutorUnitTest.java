package org.cyk.utility.__kernel__.script;

import static org.assertj.core.api.Assertions.assertThat;

import javax.script.ScriptException;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ScriptExecutorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void print_hello_world() throws ScriptException{
		assertThat(ScriptExecutor.getInstance().execute("print('Hello, World')")).isEqualTo(null);
	}
	
	@Test
	public void variable() throws ScriptException{
		assertThat(ScriptExecutor.getInstance().execute(String.class, "functionCode+scopeCode", "functionCode","GC","scopeCode", "13010222")).isEqualTo("GC13010222");
	}
	
	@Test
	public void formattedFunction() throws ScriptException{
		String script = ScriptHelper.join(ScriptHelper.formatFunction("generateCode", "return functionCode+scopeCode", "functionCode","scopeCode"),"generateCode(f,s)");
		assertThat(ScriptExecutor.getInstance().execute(String.class, script, "f","GC","s", "13010222")).isEqualTo("GC13010222");
	}
}