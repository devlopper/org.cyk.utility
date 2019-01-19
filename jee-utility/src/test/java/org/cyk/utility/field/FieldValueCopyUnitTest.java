package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

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
	
	public static class InstanceGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceGetter> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public InstanceGetterFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					if(MyData.class.equals( getFunction().getClazz() )) {
						if("a001".equals(getFunction().getValue()))
							setOutput(Arrays.asList(new MyData().setId("159").setNum("a001")));
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
