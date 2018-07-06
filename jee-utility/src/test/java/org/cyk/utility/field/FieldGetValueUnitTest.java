package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FieldGetValueUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getMyEntityIdentifier(){
		assertThat(__inject__(FieldValueGetter.class).setObject(new MyEntity().setCode("c01")).setField("code").execute().getOutput())
			.isEqualTo("c01");
	}
	

}
