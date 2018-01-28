package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.InstanceHelper.Listener.FieldValueGenerator;
import org.cyk.utility.common.helper.InstanceHelper.Lookup.Source;
import org.cyk.utility.common.helper.InstanceHelper.Pool;
import org.cyk.utility.common.helper.ListenerHelper.Executor.ResultMethod;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.helper.ClassHelper.Listener.IdentifierType;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unchecked")
public class InstanceHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		ClassHelper.getInstance().map(InstanceHelper.Listener.class, Listener.class);
		InstanceHelper.Lookup.Source.Adapter.Default.RESULT_METHOD_CLASS = (Class<ResultMethod<Object, Source<?, ?>>>) ClassHelper.getInstance().getByName(MySource.class);
		//InstanceHelper.Setter.ProcessValue.CLASSES.add(A.StringProcessor.class);
		/*InstanceHelper.Setter.ProcessValue.Adapter.Default.RESULT_METHOD = new InstanceHelper.Setter.ProcessValue.ResultMethod(){
			private static final long serialVersionUID = 1L;

			@Override
			protected java.lang.Object __execute__() {
				if("f2".equals(getProperty(InstanceHelper.Setter.ProcessValue.PROPERTY_FIELD_NAME)))
					return Integer.parseInt(getProperty(InstanceHelper.Setter.ProcessValue.PROPERTY_VALUE).toString());
				return super.__execute__();
			}
		};*/
		
		InstanceHelper.getInstance().setFieldValueGenerator(A.class, "f1", new FieldValueGenerator.Adapter.Default<String>(String.class){
			private static final long serialVersionUID = 1L;
			@Override
			protected String __execute__(Object instance, String fieldName,Class<String> outputClass) {
				if(instance instanceof A)
					return (((A)instance).getF1()+"_I_"+RandomHelper.getInstance().get(String.class));
				return super.__execute__(instance, fieldName, outputClass);
			}
		});

	}
	
	@InjectMocks private InstanceHelper instanceHelper; 
	
	@Override
	protected void _execute_() {}
	
	@Test
	public void getEnumValues(){
		assertList(new ArrayList<MyEnum>(InstanceHelper.getInstance().get(MyEnum.class)), Arrays.asList(MyEnum.E1,MyEnum.E2,MyEnum.E3));
	}
	
	@Test
	public void getIdentifiers(){
		assertEquals(MyEnum.E1.name(), InstanceHelper.getInstance().getIdentifier(MyEnum.E1));
	}
	
	@Test
	public void getBooleanValues(){
		assertList(new ArrayList<Boolean>(InstanceHelper.getInstance().get(Boolean.class)), Arrays.asList(Boolean.TRUE,Boolean.FALSE));
	}
	
	@Test
	public void generateFieldValue(){
		A a = new A();
		a.setF1("a1code1");
		AssertionHelper.getInstance().assertEquals(Boolean.TRUE,InstanceHelper.getInstance().generateFieldValue(a, "f1", String.class).startsWith(a.getF1()+"_I_"));
	}
	
	@Test
	public void pool(){
		AssertionHelper.getInstance().assertEquals(null, Pool.getInstance().get(A.class, 181818));
		Collection<A> instances = new ArrayList<>();
		Pool.getInstance().add(A.class, instances);
		AssertionHelper.getInstance().assertEquals(null, Pool.getInstance().get(A.class, 181818));
		A a = new A();
		a.setF1("From My Source _ 159");
		a.setF2(147);
		Pool.getInstance().add(A.class, a);
		AssertionHelper.getInstance().assertEquals(null, Pool.getInstance().get(A.class, 181818));
		A a888 = new A();
		a888.setF1("From My Source _ 888");
		a888.setF2(181818);
		Pool.getInstance().add(A.class, a888);
		AssertionHelper.getInstance().assertEquals(a888, Pool.getInstance().get(A.class, 181818));
		A a777 = new A();
		a777.setF1("From My Source _ 777");
		a777.setF2(475747);
		Pool.getInstance().add(A.class, a777);
		AssertionHelper.getInstance().assertEquals(a888, Pool.getInstance().get(A.class, 181818));
		AssertionHelper.getInstance().assertEquals(a777, Pool.getInstance().get(A.class, 475747));
		AssertionHelper.getInstance().assertEquals(null, Pool.getInstance().get(A.class, 111));
		AssertionHelper.getInstance().assertEquals(a, Pool.getInstance().get(A.class, 147));

	}
	
	@Test
	public void lookup(){
		A a = new A();
		a.setF1("From My Source _ 159");
		a.setF2(147);
		Pool.getInstance().add(A.class, a);
		assertA(new InstanceHelper.Lookup.Adapter.Default<>(Integer.class, 147, A.class).execute(),"From My Source _ 159", 147);
	}
	
	@Test
	public void set(){
		new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(new Object[]{"","","","",""}, A.class)
			.addParameterArrayElementString("f1","f2","f4","f5","subAEntity").execute();
		
		new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(new Object[]{"MyString","1547","true","12/11/2015","18"}, A.class)
		.addParameterArrayElementString("f1","f2","f4","f5","subAEntity").execute();
		
		A a = new A();
		assertA(a,new Object[]{"f1","name","f2",12},"name", 12);
		assertA(a,new Object[]{"f2",12,"f1","name"},"name", 12);
		
		a = new A();
		a.setF1("From My Source _ 159");
		a.setF2(147);
		Pool.getInstance().add(A.class, a);
		assertA(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(new Object[]{}, A.class).setKeyBuilder(
				new ArrayHelper.Dimension.Key.Builder.Adapter.Default(){
					private static final long serialVersionUID = 1L;

					protected org.cyk.utility.common.helper.ArrayHelper.Dimension.Key __execute__() {
						return new ArrayHelper.Dimension.Key(147);
					}
				} ).execute(),"From My Source _ 159", 147);
		
		Pool.getInstance().clear();
	}
	
	@Test
	public void buildFromOneDimensionArray(){
		assertA(new Object[]{"name",12},"name", 12);
		
		assertA(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(new Object[]{"c0","c1",13,14,15}, A.class)
				.addParameterArrayElementStringIndexInstance(1, "f1",4, "f2").execute(),"c1", 15);
		
		assertA(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(new Object[]{"c0","c1",13,14,15}, A.class)
				.addParameterArrayElementStringIndexInstance(1, "f1",4, "f2", 0, "subAEntity.ff1").execute(),"c1", 15);
	}
	
	@Test
	public void buildFromTwoDimensionArray(){
		contains(A.class, new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<A>(new Object[][]{{"string one",1},{"string 2",2}})
				.setOneDimensionArray(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(A.class).addParameterArrayElementString("f1","f2"))
				.execute(), new Object[]{"f1","f2"}, new Object[][]{ {"string one",1},{"string 2",2}  });
		
		contains(A.class, new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<A>(new Object[][]{{"string one","1"},{"string 2","2"}})
				.setOneDimensionArray(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(A.class).addParameterArrayElementString("f1","f2"))
				.execute(), new Object[]{"f1","f2"}, new Object[][]{ {"string one",1},{"string 2",2}  });
		
		contains(A.class, new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<A>(new Object[][]{{1,"string one"},{2,"string 2"}})
				.setOneDimensionArray(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(A.class).addParameterArrayElementString("f2","f1"))
				.execute(), new Object[]{"f1","f2"}, new Object[][]{ {"string one",1},{"string 2",2}  });
		
		contains(A.class, new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<A>(new Object[][]{{"1","string one"},{"2","string 2"}})
				.setOneDimensionArray(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(A.class).addParameterArrayElementString("f2","f1"))
				.execute(), new Object[]{"f1","f2"}, new Object[][]{ {"string one",1},{"string 2",2}  });
		
		/**/
		
		contains(A.class, new InstanceHelper.Builder.TwoDimensionArray.Adapter.Default<A>(new Object[][]{{"string one","1","ss1"},{"string 2","2","ss2"}})
				.setOneDimensionArray(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(A.class).addParameterArrayElementString("f1","f2","subA.ff1"))
				.execute(), new Object[]{"f1","f2"/*,"subA.ff1"*/}, new Object[][]{ {"string one",1/*,"ss1"*/},{"string 2",2/*,"ss2"*/}  });
		
	}
	
	@Override
	protected Field getField(Class<?> aClass, String name) {
		return FieldHelper.getInstance().get(aClass, name);
	}
	
	@Test
	public void copy(){
		A a1 = new A();
		a1.setF1("inF1");
		a1.setF2(15);
		
		assertA(new InstanceHelper.Copy.Adapter.Default<A>(a1).execute(), "inF1", 15);
		
		assertA(new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldNames("f1").execute(), null, 15);
		
		A a4 = new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldNames("f2").execute();
		assertA(a4, "inF1", null);
		
		A a5 = new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldNames("f1","f2").execute();
		assertA(a5, null, null);
		
		A a6 = new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldAnnotationClasses(NotNull.class).execute();
		assertA(a6, null, 15);
		
		A a7 = new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldAnnotationClasses(Digits.class).execute();
		assertA(a7, "inF1", null);
		
		A a8 = new InstanceHelper.Copy.Adapter.Default<A>(a1).addIgnoredFieldAnnotationClasses(NotNull.class,Digits.class).execute();
		assertA(a8, null, null);
	}
	
	private void assertA(A a,String f1,Integer f2){
		assertEquals("field value f1 is not correct",f1, a.getF1());
		assertEquals("field value f2 is not correct",f2, a.getF2());
	}
	
	private void assertA(A a,Object[] values,String f1,Integer f2){
		new InstanceHelper.Setter.Adapter.Default<A>(A.class, a).setManyProperties(values).execute();
		assertA(a, f1, f2);
	}
	
	private void assertA(Object[] values,String f1,Integer f2){
		assertA(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(values, A.class).addParameterArrayElementString("f1","f2").execute(),f1, f2);
		assertA(new InstanceHelper.Builder.OneDimensionArray.Adapter.Default<A>(ArrayHelper.getInstance().reverse(values), A.class).addParameterArrayElementString("f2","f1")
				.execute(),f1, f2);
	}
	
	/**/
	
	@Getter @Setter
	public static class A implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@NotNull private String f1;
		@Digits(integer=2,fraction=2) private Integer f2;
		private double f3;
		private Boolean f4;
		private Date f5;
		private SubA subA = new SubA();
		private SubAEntity subAEntity;
		
		public String hi(String name){
			return "Hi "+name+"!";
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
	@Getter @Setter
	public static class SubA {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		private SubAA subAA = new SubAA();
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
	@Getter @Setter
	public static class SubAA {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
	@Getter @Setter
	public static class SubAEntity {
		
		private String ff1;
		private Integer ff2;
		private double ff3;
		private Boolean ff4;
		
		private SubAA subAA = new SubAA();
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		
	}
	
	public static class MySource extends InstanceHelper.Lookup.Source.Adapter.Default.ResultMethod {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected java.lang.Object __execute__() {
			//if("159".equals(getInput()))
			//	return A_159;
			return super.__execute__();
		}
		
	}
	
	public static class Listener extends InstanceHelper.Listener.Adapter.Default implements Serializable{
		private static final long serialVersionUID = 1L;
		
		@Override
		public Object getIdentifier(Object instance) {
			if(instance instanceof A)
				return((A)instance).getF2();
			return super.getIdentifier(instance);
		}
		
		@Override
		public <T> T getByIdentifier(Class<T> aClass, Object identifier, IdentifierType identifierType) {
			if(aClass.equals(A.class))
				if(identifier.equals("12"))
					return null;
			return super.getByIdentifier(aClass, identifier, identifierType);
		}
		
		/*@Override
		public <T> T generateFieldValue(Object instance, String name,Class<T> valueClass) {
			if(instance instanceof A)
				return (T) (((A)instance).getF1()+"_I_"+RandomHelper.getInstance().get(String.class));
			return super.generateFieldValue(instance, name, valueClass);
		}*/
		
	}
	
	public static enum MyEnum {
		E1,E2,E3
	}
}
