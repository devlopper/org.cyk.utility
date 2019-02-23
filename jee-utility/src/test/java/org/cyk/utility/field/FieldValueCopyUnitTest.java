package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.ApplicationScopeLifeCycleListener;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.instance.InstanceGetter;
import org.cyk.utility.instance.InstanceGetterImpl;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldValueCopyUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
		__inject__(FieldNameValueUsageMap.class).set(MyPersistenceType.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyDataTransferObjectType.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
	}
	
	@Test
	public void one_2_int_string(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 instance01 = new MyClass01().setIntField(2);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"intField");
		assertThat(instance02.getIntField()).isEqualTo("2");
	}
	
	@Test
	public void one_5_long_string(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 instance01 = new MyClass01().setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"longField1");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void many_copy_2_int_and_5_long(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("intField").setFieldName("longField1").execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void many_copy_all(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 instance01 = new MyClass01().setIntField(2).setLongField1(5l);
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).execute();
		assertThat(instance02.getIntField()).isEqualTo("2");
		assertThat(instance02.getLongField1()).isEqualTo("5");
	}
	
	@Test
	public void one_copy_myData_MyDataToString(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		__inject__(FieldNameValueUsageMap.class).set(MyData.class, FieldName.IDENTIFIER, ValueUsageType.SYSTEM, "id");
		__inject__(FieldNameValueUsageMap.class).set(MyData.class, FieldName.IDENTIFIER, ValueUsageType.BUSINESS, "num");
		
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class);
		
		MyClass01 instance01 = new MyClass01().setMyData(new MyData().setId("159").setNum("a001"));
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("myData").execute();
		assertThat(instance02.getMyData()).isEqualTo("a001");
	}
	
	@Test
	public void one_copy_myData_StringToMyData(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass02 instance02 = new MyClass02().setMyData("a001");
		MyClass01 instance01 = new MyClass01();
		
		__inject__(FunctionRunnableMap.class).set(InstanceGetterImpl.class, InstanceGetterFunctionRunnableImpl.class);
		
		__inject__(FieldValueCopy.class).setSource(instance02).setDestination(instance01).setFieldName("myData").execute();
		assertThat(instance01.getMyData()).isNotNull();
		assertThat(instance01.getMyData().getId()).isEqualTo("159");
		assertThat(instance01.getMyData().getNum()).isEqualTo("a001");
	}
	
	@Test
	public void one_copy_myData_StringToMyData_null(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass02 instance02 = new MyClass02();
		MyClass01 instance01 = new MyClass01();
		__inject__(FieldValueCopy.class).setSource(instance02).setDestination(instance01).setFieldName("myData").execute();
		assertThat(instance01.getMyData()).isNull();
	}
	
	@Test
	public void one_copy_myData_MyDataToString_null(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyClass01 instance01 = new MyClass01();
		MyClass02 instance02 = new MyClass02();
		__inject__(FieldValueCopy.class).setSource(instance01).setDestination(instance02).setFieldName("myData").execute();
		assertThat(instance02.getMyData()).isNull();
	}
	
	@Test
	public void one_persistenceType_to_dtoType_as_object(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
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
	public void one_dtoType_to_persistenceType_with_system_identifier(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyDataTransferObject instance01 = new MyDataTransferObject().setId("2").setCode("p01").setName("my persistence 01").setType(new MyDataTransferObjectType().setId("17"));
		MyPersistence instance02 = new MyPersistence();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isNotNull();
		assertThat(instance02.getType().getId()).isEqualTo(17l);
		assertThat(instance02.getType().getCode()).isEqualTo("mpt01");	
	}
	
	@Test
	public void one_dtoType_to_persistenceType_with_business_identifier(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyDataTransferObject instance01 = new MyDataTransferObject().setId("2").setCode("p01").setName("my persistence 01").setType(new MyDataTransferObjectType().setCode("mpt01"));
		MyPersistence instance02 = new MyPersistence();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isNotNull();
		assertThat(instance02.getType().getId()).isEqualTo(17l);
		assertThat(instance02.getType().getCode()).isEqualTo("mpt01");	
	}
	
	@Test
	public void one_persistenceType_to_dtoType_as_single_value_data(){
		__inject__(FunctionRunnableMap.class).set(FieldValueCopyImpl.class, FieldValueCopyFunctionRunnableImpl.class);
		MyPersistence instance01 = new MyPersistence().setId(2l).setCode("p01").setName("my persistence 01")
				.setType(new MyPersistenceType().setId(5l).setCode("mpt01").setName("my persistence type 01"));
		MyDataTransferObjectSimple instance02 = new MyDataTransferObjectSimple();
		__inject__(FieldValueCopy.class).execute(instance01,instance02,"type");
		assertThat(instance02.getType()).isEqualTo("mpt01");
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
