package org.cyk.utility.__kernel__.test.arquillian;

import java.io.Serializable;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public abstract class AbstractArquillianUnitTestWithDefaultDeployment extends AbstractArquillianUnitTest implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * We provided a default deployment method because in general deployment of unit test does not required too much customization.
	 * If a subclass need to provide their own method then it must extend AbstractArquillianUnitTest.
	 */
	
	/* Deployment */

	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTestWithDefaultDeployment.createJavaArchiveDeployment();
	}
}
