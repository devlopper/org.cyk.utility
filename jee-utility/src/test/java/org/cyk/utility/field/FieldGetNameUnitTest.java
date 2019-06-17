package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class FieldGetNameUnitTest extends AbstractWeldUnitTest {
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
	
	@Test
	public void getSystemFieldNameIdentifierOfMyOtherEntity02(){
		__inject__(FieldNameValueUsageMap.class).set(MyOtherEntity02.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "sysId");
		__inject__(FieldNameValueUsageMap.class).set(MyOtherEntity02.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, "matricule");
		
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyOtherEntity02.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM).execute().getOutput())
			.isEqualTo("sysId");
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyOtherEntity02(){
		__inject__(FieldNameValueUsageMap.class).set(MyOtherEntity02.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "sysId");
		__inject__(FieldNameValueUsageMap.class).set(MyOtherEntity02.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, "matricule");
		
		assertThat(__inject__(FieldNameGetter.class).setClazz(MyOtherEntity02.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS).execute().getOutput())
			.isEqualTo("matricule");
	}
	
	/*
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment("org/cyk/utility/field/beans-with-alternatives.xml");
	}
	*/
}
