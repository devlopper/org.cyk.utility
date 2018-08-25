package org.cyk.utility.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.string.repository.StringRepository;
import org.cyk.utility.string.repository.StringRepositoryMapUser;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StringHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test 
	public void getAllRepositories(){
		assertThat(DependencyInjection.injectAll(StringRepository.class)).hasSize(4);
		assertThat(DependencyInjection.injectAll(StringRepositoryMapUser.class)).hasSize(1);
	}
	
	
}
