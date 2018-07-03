package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class FieldGetUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getAllFieldOfMyClass01(){
		Collection<Field> fields = __inject__(FieldGet.class).execute(MyClass01.class).getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(2).contains(FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE));
	}
	
	@Test
	public void getAllFieldOfMyClass01Sub01(){
		Collection<Field> fields = __inject__(FieldGet.class).execute(MyClass01Sub01.class).getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(4).contains(FieldUtils.getField(MyClass01Sub01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01Sub01.class, "f01Sub01",Boolean.TRUE),FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE),FieldUtils.getField(MyClass01Sub01.class, "f01Sub02",Boolean.TRUE));
	}
	
	@Test
	public void getFieldByNameOfMyClass01(){
		Collection<Field> fields = __inject__(FieldGet.class).setClazz(MyClass01.class).setToken("f01").execute().getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(1).contains(FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE));
	}
	
	@Test
	public void getSystemFieldNameIdentifierOfMyEntity(){
		Collection<Field> fields = __inject__(FieldGet.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM)
				.execute().getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(1).contains(FieldUtils.getField(MyEntity.class, "identifier",Boolean.TRUE));
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyEntity(){
		Collection<Field> fields = __inject__(FieldGet.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS)
				.execute().getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(1).contains(FieldUtils.getField(MyEntity.class, "code",Boolean.TRUE));
	}
	
	@Test
	public void getSystemFieldNameIdentifierOfMyOtherEntity(){
		Collection<Field> fields = __inject__(FieldGet.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM)
				.execute().getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(1).contains(FieldUtils.getField(MyOtherEntity.class, "sysId",Boolean.TRUE));
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyOtherEntity(){
		Collection<Field> fields = __inject__(FieldGet.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS)
				.execute().getOutput();
		assertThat(fields).isNotEmpty().asList().hasSize(1).contains(FieldUtils.getField(MyOtherEntity.class, "matricule",Boolean.TRUE));
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment("org/cyk/utility/field/beans-with-alternatives.xml").addPackage(FieldGetUnitTest.class.getPackage());
	}
	
}
