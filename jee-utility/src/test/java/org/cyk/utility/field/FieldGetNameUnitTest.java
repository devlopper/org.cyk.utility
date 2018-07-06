package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class FieldGetNameUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getSystemFieldNameIdentifierOfMyEntity(){
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM).execute().getOutput())
			.isEqualTo("identifier");
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyEntity(){
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS).execute().getOutput())
			.isEqualTo("code");
	}
	
	@Test
	public void getSystemFieldNameIdentifierOfMyOtherEntity(){
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM).execute().getOutput())
			.isEqualTo("sysId");
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyOtherEntity(){
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS).execute().getOutput())
			.isEqualTo("matricule");
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment("org/cyk/utility/field/beans-with-alternatives.xml");
	}
	
}
