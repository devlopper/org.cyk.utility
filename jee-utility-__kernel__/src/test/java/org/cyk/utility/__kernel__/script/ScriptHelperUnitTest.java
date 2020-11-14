package org.cyk.utility.__kernel__.script;

import javax.script.ScriptException;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScriptHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void formatFunction() throws ScriptException{
		assertThat(ScriptHelper.formatFunction("generateCode", "code_fonction+code_ua", "code_fonction","code_ua"))
		.isEqualTo("function generateCode(code_fonction,code_ua){code_fonction+code_ua}");
	}
	
	@Test
	public void formatLoopForCollection() throws ScriptException{
		String script = ScriptHelper.formatLoopForCollection("list", "i", "//do something");
		assertThat(script).isEqualTo("var i;for (i = 0; i < list.length; i++) {//do something}");
	}
}