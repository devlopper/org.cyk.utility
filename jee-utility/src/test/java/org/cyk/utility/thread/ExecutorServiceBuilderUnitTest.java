package org.cyk.utility.thread;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class ExecutorServiceBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void build() {
		assertionHelper.assertNotNull(__inject__(ExecutorServiceBuilder.class).execute().getOutput());
	}
	
	/**/
	
	
}
