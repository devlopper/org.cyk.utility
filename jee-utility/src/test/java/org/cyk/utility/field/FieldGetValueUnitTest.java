package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class FieldGetValueUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getMyEntityIdentifier(){
		assertThat(__inject__(FieldGetValue.class).setObject(new MyEntity().setCode("c01")).setField("code").execute().getOutput())
			.isEqualTo("c01");
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment().addPackage(FieldGetValueUnitTest.class.getPackage());
	}
	
}
