package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.instance.InstanceGetterImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.value.ValueUsageType;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueCopyUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Override
	protected void __listenBefore__()  {
		super.__listenBefore__();
		__inject__(FieldNameValueUsageMap.class).set(MyPersistenceType.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyDataTransferObjectType.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyPersistenceReflexive.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyDataTransferObjectReflexive.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class);
	}
	
	@Test
	public void int_to_string(){
		MyClass01 instance01 = new MyClass01().setIntField(2);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"intField");
		assertThat(instance02.getIntField()).isEqualTo("2");
	}
	
	@Test
	public void long_to_string(){
		MyClass01 instance01 = new MyClass01().setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"longField1");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void long_to_string_wrapper(){
		MyClass01 instance01 = new MyClass01().setLongField2(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"longField2");
		assertThat(instance02.getLongField2()).isEqualTo("5");
	}
	
	@Test
	public void int_and_long_to_string(){
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("intField").setFieldName("longField1").execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void bytes_to_bytes(){
		MyClass01 instance01 = new MyClass01().setBytes("Hello".getBytes());
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"bytes");
		assertThat(instance02.getBytes()).isNotNull();
		assertThat(new String(instance02.getBytes())).isEqualTo("Hello");
	}
	
	@Test
	public void date_to_date(){
		Date date = new Date();
		MyClass01 instance01 = new MyClass01().setDate(date);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"date");
		assertThat(instance02.getDate()).isNotNull();
		assertThat(instance02.getDate()).isEqualTo(date);
	}
	
	@Test
	public void all(){
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02().setIntField("a").setLongField1("b");
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void allNonNull(){
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02().setLongField1("b");
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setIsOverridable(Boolean.FALSE).execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("b");
	}
	
	@Test
	public void allWithCustomFieldsGetter(){
		DependencyInjection.setQualifierClass(FieldValueCopyFieldsGetter.class, Default.class);
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).execute();
		assertThat(instance02.getIntField()).isNull();
		assertThat(instance02.getLongField1()).isEqualTo("5");
		DependencyInjection.setQualifierClass(FieldValueCopyFieldsGetter.class, null);
	}
	
	@Test
	public void myData_to_string(){
		__inject__(FieldNameValueUsageMap.class).set(MyData.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyData.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, "num");
		MyClass01 instance01 = new MyClass01().setMyData(new MyData().setId("159").setNum("a001"));
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("myData").execute();
		assertThat(instance02.getMyData()).isEqualTo("a001");
	}
	
	@Test
	public void string_to_myData(){
		MyClass02 instance02 = new MyClass02().setMyData("a001");
		MyClass01 instance01 = new MyClass01();
		__inject__(FieldValueCopy.class).setSource(instance02).setDestination(instance01).setFieldName("myData").execute();
		assertThat(instance01.getMyData()).isNotNull();
		assertThat(instance01.getMyData().getId()).isEqualTo("159");
		assertThat(instance01.getMyData().getNum()).isEqualTo("a001");
	}
	
	@Test
	public void string_to_myData_null(){
		MyClass02 instance02 = new MyClass02();
		MyClass01 instance01 = new MyClass01();
		__inject__(FieldValueCopy.class).setSource(instance02).setDestination(instance01).setFieldName("myData").execute();
		assertThat(instance01.getMyData()).isNull();
	}
	
	@Test
	public void myData_to_string_null(){
		MyClass01 instance01 = new MyClass01();
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("myData").execute();
		assertThat(instance02.getMyData()).isNull();
	}
	
	@Test
	public void persistence_to_dto_as_object(){
		MyPersistence instance01 = new MyPersistence().setId(2l).setCode("p01").setName("my persistence 01")
				.setType(new MyPersistenceType().setId(5l).setCode("mpt01").setName("my persistence type 01"))
				.setBytes("Hello".getBytes());
		MyDataTransferObject instance02 = new MyDataTransferObject();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"code");
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"name");
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"bytes");
		assertThat(instance02).isNotNull();
		assertThat(instance02.getCode()).isEqualTo("p01");
		assertThat(instance02.getName()).isEqualTo("my persistence 01");
		assertThat(instance02.getBytes()).isNotNull();
		assertThat(new String(instance02.getBytes())).isEqualTo("Hello");
	}
	
	@Test
	public void persistenceType_to_dtoType_as_object(){
		MyPersistence instance01 = new MyPersistence().setId(2l).setCode("p01").setName("my persistence 01")
				.setType(new MyPersistenceType().setId(5l).setCode("mpt01").setName("my persistence type 01"));
		MyDataTransferObject instance02 = new MyDataTransferObject();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isNotNull();
		assertThat(instance02.getType().getId()).isEqualTo("5");
		assertThat(instance02.getType().getCode()).isEqualTo("mpt01");
		assertThat(instance02.getType().getName()).isEqualTo("my persistence type 01");
	}
	
	@Test
	public void dtoType_to_persistenceType_with_system_identifier(){
		MyDataTransferObject instance01 = new MyDataTransferObject().setId("2").setCode("p01").setName("my persistence 01")
				.setType(new MyDataTransferObjectType().setId("17")).setBytes("Hello".getBytes());
		MyPersistence instance02 = new MyPersistence();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isNotNull();
		assertThat(instance02.getType().getId()).isEqualTo(17l);
		assertThat(instance02.getType().getCode()).isEqualTo("mpt01");
		
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"bytes");
		assertThat(instance02.getBytes()).isNotNull();
		assertThat(new String(instance02.getBytes())).isEqualTo("Hello");
	}
	
	@Test
	public void dtoType_to_persistenceType_with_business_identifier(){
		MyDataTransferObject instance01 = new MyDataTransferObject().setId("2").setCode("p01").setName("my persistence 01").setType(new MyDataTransferObjectType().setCode("mpt01"));
		MyPersistence instance02 = new MyPersistence();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isNotNull();
		assertThat(instance02.getType().getId()).isEqualTo(17l);
		assertThat(instance02.getType().getCode()).isEqualTo("mpt01");	
	}
	
	@Test
	public void persistenceType_to_dtoType_as_single_value_data(){
		MyPersistence instance01 = new MyPersistence().setId(2l).setCode("p01").setName("my persistence 01")
				.setType(new MyPersistenceType().setId(5l).setCode("mpt01").setName("my persistence type 01"));
		MyDataTransferObjectSimple instance02 = new MyDataTransferObjectSimple();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isEqualTo("mpt01");
	}
		
	@Test
	public void one_persistenceReflexive_to_dtoReflexive_as_object(){
		MyPersistenceReflexive instanceL01 = new MyPersistenceReflexive().setId(1l).setCode("l01").setName("my persistence reflexive l01");
		MyPersistenceReflexive instanceL02 = new MyPersistenceReflexive().setId(2l).setCode("l02").setName("my persistence reflexive l02").setParent(instanceL01);
		MyPersistenceReflexive instanceL03 = new MyPersistenceReflexive().setId(3l).setCode("l03").setName("my persistence reflexive l03").setParent(instanceL02);
		MyPersistenceReflexive instanceL04 = new MyPersistenceReflexive().setId(4l).setCode("l04").setName("my persistence reflexive l04").setParent(instanceL03);
		
		MyDataTransferObjectReflexive representation = new MyDataTransferObjectReflexive();
		__inject__(FieldValueCopy.class).execute(instanceL04,representation);
		
		assertThat(representation.getId()).isEqualTo("4");
		assertThat(representation.getCode()).isEqualTo("l04");
		assertThat(representation.getName()).isEqualTo("my persistence reflexive l04");
		assertThat(representation.getParent()).isNotNull();
		
		assertThat(representation.getParent()).isNotNull();
		//assertThat(representation.getParent().getCode()).isEqualTo("l03"); //By default identifier is prefer over code
		assertThat(representation.getParent().getName()).isNull();
		
		/*
		assertThat(representation.getParent().getParent()).isNotNull();
		assertThat(representation.getParent().getParent().getId()).isEqualTo("2");
		assertThat(representation.getParent().getParent().getCode()).isEqualTo("l02");
		assertThat(representation.getParent().getParent().getName()).isEqualTo("my persistence reflexive l02");
		
		assertThat(representation.getParent().getParent().getParent()).isNotNull();
		assertThat(representation.getParent().getParent().getParent().getId()).isEqualTo("1");
		assertThat(representation.getParent().getParent().getParent().getCode()).isEqualTo("l01");
		assertThat(representation.getParent().getParent().getParent().getName()).isEqualTo("my persistence reflexive l01");
		*/
	}
	
	//@Test
	public void one_persistenceReflexive_to_dtoReflexive_as_object_not_deeper(){
		MyPersistenceReflexive instanceL01 = new MyPersistenceReflexive().setId(1l).setCode("l01").setName("my persistence reflexive l01");
		MyPersistenceReflexive instanceL02 = new MyPersistenceReflexive().setId(2l).setCode("l02").setName("my persistence reflexive l02").setParent(instanceL01);
		MyPersistenceReflexive instanceL03 = new MyPersistenceReflexive().setId(3l).setCode("l03").setName("my persistence reflexive l03").setParent(instanceL02);
		
		MyDataTransferObjectReflexive representation = new MyDataTransferObjectReflexive();
		__inject__(FieldValueCopy.class).execute(instanceL03,representation,"parent");
		assertThat(representation.getParent()).isNotNull();
		assertThat(representation.getParent().getId()).isEqualTo("2");
		assertThat(representation.getParent().getCode()).isEqualTo("l02");
		assertThat(representation.getParent().getName()).isNull();
		
		assertThat(representation.getParent().getParent()).isNull();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass01 implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private int intField;
		private Integer integerField;
		private String stringField;
		private long longField1;
		private Long longField2;
		private Date dateField;
		private MyData myData;
		private byte[] bytes;
		private Date date;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass02 implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String intField;
		private String integerField;
		private String stringField;
		private String longField1;
		private String longField2;
		private String dateField;
		private String myData;
		private byte[] bytes;
		private Date date;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyData implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String num;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class MyPersistenceType implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private String code;
		private String name;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyPersistence implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private String code;
		private String name;
		private byte[] bytes;
		@ManyToOne private MyPersistenceType type;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyDataTransferObjectType implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String code;
		private String name;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyDataTransferObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String code;
		private String name;
		private byte[] bytes;
		private MyDataTransferObjectType type;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyDataTransferObjectSimple implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String code;
		private String name;
		private String type;
	}
	
	@Getter @Setter @Accessors(chain=true) @Entity
	public static class MyPersistenceReflexive implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private String code;
		private String name;
		@ManyToOne private MyPersistenceReflexive parent;
	}
	
	@Getter @Setter @Accessors(chain=true) @XmlRootElement
	public static class MyDataTransferObjectReflexive implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String code;
		private String name;
		private String type;
		private MyDataTransferObjectReflexive parent;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyCollections implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Collection<Object> objects;
		private String code;
		private String name;
		private String type;
		private MyDataTransferObjectReflexive parent;
	}
	
	public static class InstanceGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public InstanceGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(MyData.class.equals( getFunction().getClazz() )) {
						if("a001".equals(getFunction().getValue()))
							setOutput(Arrays.asList(new MyData().setId("159").setNum("a001")));
					}else if(MyPersistenceType.class.equals( getFunction().getClazz() )) {
						if(getFunction().getValueUsageType().equals(ValueUsageType.SYSTEM)) {
							if("17".equals(getFunction().getValue()))
								setOutput(Arrays.asList(new MyPersistenceType().setId(17l).setCode("mpt01").setName("nf")));
						}else if(getFunction().getValueUsageType().equals(ValueUsageType.BUSINESS)) {
							if("mpt01".equals(getFunction().getValue()))
								setOutput(Arrays.asList(new MyPersistenceType().setId(17l).setCode("mpt01").setName("nf")));
						}
					}
				}
			});
		}
		
	}
	
	@Default
	public static class FieldValueCopyFieldsGetterImpl extends AbstractFieldValueCopyFieldsGetterImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected Fields __execute__() throws Exception {
			Fields fields = super.__execute__();
			if(MyClass01.class.equals(getSourceClass()))
				fields.removeByNames("intField");
			return fields;
		}
		
	}

	
	/*@ApplicationScoped
	public static class ApplicationScopeLifeCycleListener extends AbstractApplicationScopeLifeCycleListener implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected void __initialize__(Object object) {
			System.out.println("FieldValueCopyUnitTest.ApplicationScopeLifeCycleListener.__initialize__()");
		}

		@Override
		protected void __destroy__(Object object) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	
	/**/
	
}
