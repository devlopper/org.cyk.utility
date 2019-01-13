package org.cyk.utility.thread;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ExecutorServiceBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void build() {
		assertionHelper.assertNotNull(__inject__(ExecutorServiceBuilder.class).execute().getOutput());
	}
	
	/**/
	
	
}
