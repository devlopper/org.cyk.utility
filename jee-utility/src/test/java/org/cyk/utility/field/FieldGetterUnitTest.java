package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class FieldGetterUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getAllFieldOfMyClass01(){
		Collection<Field> fields = __inject__(FieldGetter.class).execute(MyClass01.class).getOutput().get();
		assertThat(fields).hasSize(3).contains(FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE));
	}
	
	@Test
	public void getAllFieldOfMyClass01Sub01(){
		Collection<Field> fields = __inject__(FieldGetter.class).execute(MyClass01Sub01.class).getOutput().get();
		assertThat(fields).hasSize(5).contains(FieldUtils.getField(MyClass01Sub01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01Sub01.class, "f01Sub01",Boolean.TRUE),FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE)
				,FieldUtils.getField(MyClass01.class, "f02",Boolean.TRUE),FieldUtils.getField(MyClass01Sub01.class, "f01Sub02",Boolean.TRUE));
	}
	
	@Test
	public void getFieldByNameOfMyClass01(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(MyClass01.class).setToken("f01").execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(FieldUtils.getField(MyClass01.class, "f01",Boolean.TRUE));
	}
	
	@Test
	public void getSystemFieldNameIdentifierOfMyEntity(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM)
				.execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(FieldUtils.getField(MyEntity.class, "identifier",Boolean.TRUE));
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyEntity(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(MyEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS)
				.execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(FieldUtils.getField(MyEntity.class, "code",Boolean.TRUE));
	}
	
	@Test
	public void getSystemFieldNameIdentifierOfMyOtherEntity(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM)
				.execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(FieldUtils.getField(MyOtherEntity.class, "sysId",Boolean.TRUE));
	}
	
	@Test
	public void getBusinessFieldNameIdentifierOfMyOtherEntity(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(MyOtherEntity.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS)
				.execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(FieldUtils.getField(MyOtherEntity.class, "matricule",Boolean.TRUE));
	}
	
	@Test
	public void getFieldsOfInterfaceI01(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(I01.class).execute().getOutput().get();
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "NOT_PROPERTY_F01",Boolean.TRUE)
				);
	}
	
	@Test
	public void getFieldsWhereNameStartWithPropertyOfInterfaceI01(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(I01.class).setToken("PROPERTY_").setTokenLocation(StringLocation.START).execute().getOutput().get();
		assertThat(fields).hasSize(3).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				);
	}
	
	@Test
	public void getFieldsOfInterfaceI01Child(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(I01Child.class).execute().getOutput().get();
		assertThat(fields).hasSize(6).contains(
				FieldUtils.getField(I01Child.class, "PROPERTY_F04",Boolean.TRUE)
				,FieldUtils.getField(I01Child.class, "NOT_PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "NOT_PROPERTY_F01",Boolean.TRUE)
				
				);
	}
	
	@Test
	public void getFieldsWhereNameStartWithPropertyOfInterfaceI01Child(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(I01Child.class).setToken("PROPERTY_").setTokenLocation(StringLocation.START).execute().getOutput().get();
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01Child.class, "PROPERTY_F04",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				
				);
	}
	
	@Test
	public void get_name_fromC01(){
		Fields fields = __inject__(FieldGetter.class).setClazz(C01.class).addNameToken("name").execute().getOutput();
		assertThat(fields).isNull();
	}
	
	@Test
	public void get_name_code_fromC01(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(C01.class).addNameToken("name").addNameToken("code").execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(
				FieldUtils.getField(C01.class, "code",Boolean.TRUE)
				);
	}
	
	@Test
	public void get_name_code_fromC03(){
		Collection<Field> fields = __inject__(FieldGetter.class).setClazz(C03.class).addNameToken("name").addNameToken("code").execute().getOutput().get();
		assertThat(fields).hasSize(2).contains(
				FieldUtils.getField(C03.class, "name",Boolean.TRUE)
				,FieldUtils.getField(C03.class, "code",Boolean.TRUE)
				);
	}
	
	/**/
	
	@Getter @Setter
	public static class C01 {
		
		private String code;
		
	}
	
	@Getter @Setter
	public static class C02 {
		
		private String name;
		
	}
	
	@Getter @Setter
	public static class C03 {
		
		private String code;
		private String name;
		
	}
	
	public static interface I01 {
		String PROPERTY_F01 = "v01";
		String PROPERTY_F02 = "v02";
		String PROPERTY_F03 = "v03";
		
		String NOT_PROPERTY_F01 = "notproperty";
	}
	
	public static interface I01Child extends I01 {
		String PROPERTY_F04 = "v04";
		
		String NOT_PROPERTY_F02 = "notproperty";
	}
	
	/* Deployment */
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment("org/cyk/utility/field/beans-with-alternatives.xml");
	}
	
}
