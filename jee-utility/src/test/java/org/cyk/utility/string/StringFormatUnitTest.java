package org.cyk.utility.string;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StringFormatUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test 
	public void evaluate_one_argument(){
		StringFormat stringFormat = __inject__(StringFormat.class);
		stringFormat.setValue("%s").setArguments(0,"a1");
		assertThat(stringFormat.evaluate()).isEqualTo("a1");
	}
	
	@Test 
	public void evaluate_two_ordered_arguments(){
		StringFormat stringFormat = __inject__(StringFormat.class);
		stringFormat.setValue("%1s%2s").setArguments(0,"a1",1,"a2");
		assertThat(stringFormat.evaluate()).isEqualTo("a1a2");
	}
	
	@Test 
	public void evaluate_two_unordered_arguments(){
		StringFormat stringFormat = __inject__(StringFormat.class);
		stringFormat.setValue("%1s%2s").setArguments(1,"a2",0,"a1");
		assertThat(stringFormat.evaluate()).isEqualTo("a1a2");
	}
}
