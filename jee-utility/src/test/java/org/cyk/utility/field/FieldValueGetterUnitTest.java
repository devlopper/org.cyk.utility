package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FieldValueGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getMyEntityIdentifier(){
		assertThat(__inject__(FieldValueGetter.class).setObject(new MyEntity().setCode("c01")).setField("code").execute().getOutput())
			.isEqualTo("c01");
	}
	
	@Test
	public void getStringNotField(){
		assertThat(__inject__(FieldValueGetter.class).setObject(new MyEntity().setIdentifier(27).setCode("c01")).setField("identifierAndCode").execute().getOutput())
			.isEqualTo("27c01");
	}
}
