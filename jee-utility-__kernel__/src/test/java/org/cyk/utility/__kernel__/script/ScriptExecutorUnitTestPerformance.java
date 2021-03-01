package org.cyk.utility.__kernel__.script;

import javax.script.ScriptException;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ScriptExecutorUnitTestPerformance extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void many10000() throws ScriptException{
		for(Integer index = 0 ; index < 10000; index++)
			ScriptExecutor.getInstance().execute("print('Hello, World'+i)","i",index);
	}
}