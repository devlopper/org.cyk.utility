package org.cyk.utility.string;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.string.repository.StringRepository;
import org.cyk.utility.string.repository.UserRepository;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class StringHelperUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test 
	public void getAllRepositories(){
		System.out.println(DependencyInjection.injectAll(StringRepository.class));
		System.out.println(DependencyInjection.injectAll(UserRepository.class));
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createDeployment();
	}
}
