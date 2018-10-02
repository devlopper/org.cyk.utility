package org.cyk.utility.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.string.StringLocation;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class TestExpectedStringUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void inside(){
		TestExpectedString expectedString = __inject__(TestExpectedString.class);
		expectedString.getLocationStrings(StringLocation.INSIDE, Boolean.TRUE).add("e1");
		
		assertThat(expectedString.getLocationStrings(StringLocation.INSIDE).get()).isNotNull().hasSize(1);
	}
	
}
