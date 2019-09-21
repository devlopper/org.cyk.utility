package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void nullify(){
		MyClass01 object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l);
		assertThat(object.getIntegerField()).isEqualTo(1);
		assertThat(object.getStringField()).isEqualTo("a");
		assertThat(object.getLongValue2()).isEqualTo(2l);
		
		object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l);
		__inject__(FieldHelper.class).nullify(object,Boolean.TRUE, "integerField");
		assertThat(object.getIntegerField()).isNull();
		assertThat(object.getStringField()).isEqualTo("a");
		assertThat(object.getLongValue2()).isEqualTo(2l);
		
		object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l);
		__inject__(FieldHelper.class).nullify(object,Boolean.TRUE, "stringField");
		assertThat(object.getIntegerField()).isEqualTo(1);
		assertThat(object.getStringField()).isNull();
		assertThat(object.getLongValue2()).isEqualTo(2l);
		
		object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l);
		__inject__(FieldHelper.class).nullify(object,Boolean.TRUE, "longValue2");
		assertThat(object.getIntegerField()).isEqualTo(1);
		assertThat(object.getStringField()).isEqualTo("a");
		assertThat(object.getLongValue2()).isNull();
		
		object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l);
		__inject__(FieldHelper.class).nullify(object,Boolean.TRUE, "longValue2","stringField");
		assertThat(object.getIntegerField()).isEqualTo(1);
		assertThat(object.getStringField()).isNull();
		assertThat(object.getLongValue2()).isNull();
		
		object = new MyClass01().setIntegerField(1).setStringField("a").setLongValue2(2l).setIntField(1489);
		__inject__(FieldHelper.class).nullify(object,Boolean.FALSE, "intField");
		assertThat(object.getIntField()).isEqualTo(1489);
		assertThat(object.getIntegerField()).isNull();
		assertThat(object.getStringField()).isNull();
		assertThat(object.getLongValue2()).isNull();
	}	
	
	/**/

	@Test
	public void getFieldsOfInterfaceI01(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(I01.class).execute().getOutput().get();
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "NOT_PROPERTY_F01",Boolean.TRUE)
				);
	}
	
	@Test
	public void getFieldsWhereNameStartWithPropertyOfInterfaceI01(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(I01.class).setToken("PROPERTY_").setTokenLocation(StringLocation.START).execute().getOutput().get();
		assertThat(fields).hasSize(3).contains(
				FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				);
	}
	
	@Test
	public void getFieldsOfInterfaceI01Child(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(I01Child.class).execute().getOutput().get();
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
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(I01Child.class).setToken("PROPERTY_").setTokenLocation(StringLocation.START).execute().getOutput().get();
		assertThat(fields).hasSize(4).contains(
				FieldUtils.getField(I01Child.class, "PROPERTY_F04",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F01",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F02",Boolean.TRUE)
				,FieldUtils.getField(I01.class, "PROPERTY_F03",Boolean.TRUE)
				
				);
	}
	
	@Test
	public void get_name_fromC01(){
		Fields fields = __inject__(FieldsGetter.class).setClazz(C01.class).addNameToken("name").execute().getOutput();
		assertThat(fields).isNull();
	}
	
	@Test
	public void get_name_code_fromC01(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(C01.class).addNameToken("name").addNameToken("code").execute().getOutput().get();
		assertThat(fields).hasSize(1).contains(
				FieldUtils.getField(C01.class, "code",Boolean.TRUE)
				);
	}
	
	@Test
	public void get_name_code_fromC03(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(C03.class).addNameToken("name").addNameToken("code").execute().getOutput().get();
		assertThat(fields).hasSize(2).contains(
				FieldUtils.getField(C03.class, "name",Boolean.TRUE)
				,FieldUtils.getField(C03.class, "code",Boolean.TRUE)
				);
	}
	
	@Test
	public void get_identifier_system_fromC04_isNull(){
		FieldsGetter fieldGetter = __inject__(FieldsGetter.class);
		fieldGetter.setLoggable(Boolean.TRUE).getLog(Boolean.TRUE).setLevel(LogLevel.INFO);
		fieldGetter.setClazz(C04.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.SYSTEM).execute();
		Fields fields = fieldGetter.getOutput();
		assertThat(fields).isNull();
		System.out.println(fieldGetter.getProperties());
	}
	
	@Test
	public void get_code_fromC01(){
		Collection<Field> fields = __inject__(FieldsGetter.class).setClazz(C03.class).addNameToken("name").addNameToken("code").execute().getOutput().get();
		assertThat(fields).hasSize(2).contains(
				FieldUtils.getField(C03.class, "name",Boolean.TRUE)
				,FieldUtils.getField(C03.class, "code",Boolean.TRUE)
				);
	}
	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 extends AbstractObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String code;
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longValue1;
		private Long longValue2;
		private Date dateField;
		private Object x;
		private MyClass01Sub sub;
		
		public MyClass01 setLongValue1(long value) {
			this.longValue1 = value * 2;
			return this;
		}
		
		public MyClass01 setMyProperty(Object value) {
			this.x = value;
			return this;
		}
		
		public Object getMyProperty() {
			return x;
		}
		
		public static final String STATIC_FIELD_01 = "staticValue01";
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01Sub {
		
		private int intField;
		private Integer integerField;
		private String stringField;
		private long sLongValue1;
		private Long sLongValue2;
		private Date sDateField;
		private Object sX;
		
	}

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
	
	@Getter @Setter
	public static class C04 {
		
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
}
