package org.cyk.utility.value;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ValueConverterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void execute_2_int_to_string() {
		assertThat(__inject__(ValueConverter.class).execute(2, String.class).getOutput()).isInstanceOf(String.class).isEqualTo("2");
	}
	
	@Test
	public void execute_2_string_to_int() {
		assertThat(__inject__(ValueConverter.class).execute("2", Integer.class).getOutput()).isInstanceOf(Integer.class).isEqualTo(2);
	}
}
