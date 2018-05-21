package org.cyk.utility.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper.Location;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;
import org.mockito.InjectMocks;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@InjectMocks private FieldHelper fieldHelper;
	
	//@Test
	public void getShadowed(){
		//TestCase testCase = new TestCase();
		//testCase.assertNotEquals(FieldHelper.getInstance().get(BaseShadow.class, "privateField"), FieldHelper.getInstance().get(ChildShadow.class, "privateField"));
		//testCase.assertNotEquals(FieldHelper.getInstance().get(BaseShadow.class, "protectedField"), FieldHelper.getInstance().get(ChildShadow.class, "protectedField"));
		
		//testCase.assertEquals(FieldHelper.getInstance().get(BaseShadow.class, "privateField"), FieldHelper.getInstance().get(ChildShadow.class, "privateFieldNoShadow"));
		
		assertEquals(String.class, FieldHelper.getInstance().getType(FieldType.class, "f1"));
		assertEquals(Integer.class, FieldHelper.getInstance().getType(FieldType.class, "f2"));
		assertEquals(Collection.class, FieldHelper.getInstance().getType(FieldType.class, "f3"));
		assertEquals(Collection.class, FieldHelper.getInstance().getType(FieldType.class, "f4"));
	}
	
	@Test
	public void getType(){
		assertEquals(String.class, FieldHelper.getInstance().getType(FieldType.class, "f1"));
		assertEquals(Integer.class, FieldHelper.getInstance().getType(FieldType.class, "f2"));
		assertEquals(Collection.class, FieldHelper.getInstance().getType(FieldType.class, "f3"));
		assertEquals(Collection.class, FieldHelper.getInstance().getType(FieldType.class, "f4"));
		
		Field stringListField = FieldHelper.getInstance().get(FieldType.class, "f3");
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
        System.out.println(stringListClass); // class java.lang.String.

        
	}
	
	@Test
	public void fieldOverride(){
		assertEquals(Integer.class, FieldHelper.getInstance().getType(FieldOverride.class, "f1"));
		assertEquals(Integer.class, FieldHelper.getInstance().getType(FieldOverrideChild.class, "f1"));
	}
	
	@Test
	public void getConstraintsDefault(){
		FieldHelper.Field.clear();
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsA.class, "nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsA.class, "notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.notNullableString").getConstraints().getIsNullable()); 
	}
	
	@Test
	public void getConstraintsSet(){
		FieldHelper.Field.clear();
		
		FieldHelper.Field.get(ModelForConstraintsC.class, "a.notNullableString").getConstraints().setIsNullable(Boolean.TRUE);
		FieldHelper.Field.get(ModelForConstraintsD.class, "a.nullableString").getConstraints().setIsNullable(Boolean.FALSE);
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsA.class, "nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsA.class, "notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.notNullableString").getConstraints().getIsNullable()); 
	}
	
	@Test
	public void getConstraintsA_AllNull(){
		FieldHelper.Field.clear();
		
		FieldHelper.Field.get(ModelForConstraintsA.class, "notNullableString").getConstraints().setIsNullable(Boolean.TRUE);
		FieldHelper.Field.get(ModelForConstraintsA.class, "nullableString").getConstraints().setIsNullable(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsA.class, "nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsA.class, "notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsB.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsC.class, "a.notNullableString").getConstraints().getIsNullable()); 
		
		assertEquals(Boolean.TRUE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.nullableString").getConstraints().getIsNullable()); 
		assertEquals(Boolean.FALSE, FieldHelper.Field.get(ModelForConstraintsD.class, "a.notNullableString").getConstraints().getIsNullable()); 
	}
	
	@Test
	public void getConstraintIdentifier(){
		FieldHelper.Field.clear();
		
		assertEquals("ModelForConstraintsA_nullableString_NotNull", FieldHelper.Field.get(ModelForConstraintsA.class, "nullableString").getIdentifier(NotNull.class)); 
		
	}
	
	@Test
	public void getAction(){
		assertEquals(7, new FieldHelper.Get.Adapter.Default(MyClass.class).execute().size());
		assertEquals(0, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.ABSTRACT).execute().size());
		assertEquals(6, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.STATIC).execute().size());
		assertEquals(3, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.PUBLIC).execute().size());
		assertEquals(4, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.PRIVATE).execute().size());
		assertEquals(3, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.PRIVATE,Modifier.STATIC).execute().size());
		assertEquals(3, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(Modifier.PUBLIC,Modifier.STATIC).execute().size());
		//assertEquals(1, new FieldHelper.Get.Adapter.Default(MyClass.class).addModifiers(2).execute().size()); //FIXME
	}
	
	@Test
	public void get(){
		assertEquals(7, fieldHelper.get(MyClass.class).size());
		assertEquals(7, fieldHelper.get(MyClass.class, null, null).size());
		assertEquals(7, fieldHelper.get(MyClass.class, "", null).size());
		assertEquals(7, fieldHelper.get(MyClass.class, null, Location.START).size());
		assertEquals(7, fieldHelper.get(MyClass.class, "", Location.START).size());
		assertEquals(1, fieldHelper.get(MyClass.class, "f", Location.START).size());
		assertEquals(3, fieldHelper.get(MyClass.class, "S", Location.START).size());
		assertEquals(3, fieldHelper.get(MyClass.class, "1", Location.END).size());
		assertEquals(2, fieldHelper.get(MyClass.class, "F2", Location.INSIDE).size());
		assertEquals(0, fieldHelper.get(MyClass.class, "F", Location.EXAT).size());
		
		List<String> names = new ArrayList<>(fieldHelper.getNamesWhereReferencedByStaticField(MyClass.class));
		assertEquals(3, names.size());
		assertEquals("SF1", names.get(0));
		assertEquals("SF2", names.get(1));
		assertEquals("SF3", names.get(2));
		
		assertEquals(14, fieldHelper.get(MyChildClass.class).size());
		assertEquals(7, fieldHelper.get(MyChildClass.class,Boolean.FALSE).size());
		assertEquals(6, fieldHelper.get(MyChildClass.class, "S", Location.START).size());
		assertEquals(3, fieldHelper.get(MyChildClass.class, "S", Location.START,Boolean.FALSE).size());
		
		assertEquals(1, fieldHelper.get(A.class, "af1", Location.EXAT).size());
		assertEquals(1, fieldHelper.get(B.class, "bf1", Location.EXAT).size());
		assertEquals(1, fieldHelper.get(A.class, "b1", Location.EXAT).size());
		assertNotNull(fieldHelper.get(A.class, "b1.bf1"));
	}
	
	@Test
	public void setStatic(){
		fieldHelper.set(MyClass.class, "SF1",5);
		assertEquals("SF1 value cannot be set", 5, MyClass.getSF1());
	}
	
	@Test
	public void set(){
		MyClass a = new MyClass();
		fieldHelper.set(a,(Object)"my value","f1");
		assertEquals("my value", a.getF1());
	}
	
	@Test
	public void copy(){
		MyClass source = new MyClass();
		source.setF1("the val");
		assertEquals("the val", source.getF1());
		
		MyClass destination = new MyClass();
		assertThat("f1 is not null",destination.getF1()==null);
		
		fieldHelper.copy(source, destination, "f1");
		
		assertEquals("the val", source.getF1());
		assertEquals("the val", destination.getF1());
		assertEquals(source.getF1(),destination.getF1());
		
		
	}
	
	/**/
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {
		
		private static Integer SF1;
		private static String SF2;
		private static MyClass SF3;
		
		private String f1;
		
		public static Integer getSF1() {
			return SF1;
		}
		
		public static String getSF2() {
			return SF2;
		}
		
		public static MyClass getSF3() {
			return SF3;
		}
		
		public String get(){
			return null;
		}
		
		public void set_(String var){
			
		}
		
		public static final String FIELD_SF1 = "SF1";
		public static final String FIELD_SF2 = "SF2";
		public static final String FIELD_SF3 = "SF3";
	}
	
	@Getter @Setter
	public static class MyChildClass extends MyClass {
		
		private static Integer S_CC_F1;
		private static String S_CC_F2;
		private static MyClass S_CC_F3;
		
		private String cc_f1;
		
		public static Integer getS_CC_F1() {
			return S_CC_F1;
		}
		
		public static String getS_CC_F2() {
			return S_CC_F2;
		}
		
		public static MyClass getS_CC_F3() {
			return S_CC_F3;
		}
		
		public static final String FIELD_CC_SF1 = "S_CC_F1";
		public static final String FIELD_CC_SF2 = "S_CC_F2";
		public static final String FIELD_CC_SF3 = "S_CC_F3";
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class A {
		
		private String af1;
		private B b1;
		
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class B {
		
		private String bf1;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ModelForConstraintsA {
		
		private String nullableString;
		@NotNull private String notNullableString;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ModelForConstraintsB {
		
		private String nullableString;
		@NotNull private String notNullableString;
		private ModelForConstraintsA a;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ModelForConstraintsC {
		
		private String nullableString;
		@NotNull private String notNullableString;
		private ModelForConstraintsA a;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ModelForConstraintsD {
		
		private String nullableString;
		@NotNull private String notNullableString;
		private ModelForConstraintsA a;
		
	}
	
	@Getter @Setter
	public static class FieldOverrideBase {
		private String f1;
	}
	
	@org.cyk.utility.common.annotation.FieldOverride(name="f1",type=Integer.class)
	public static class FieldOverride extends FieldOverrideBase {
		
	}
	
	public static class FieldOverrideChild extends FieldOverride {
		
	}

	@Getter @Setter @Accessors(chain=true)
	public static class FieldType {
		
		private String f1;
		private Integer f2;
		private Collection<String> f3;
		private Collection<Integer> f4;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class BaseShadow {
		
		private String privateField;
		protected String protectedField;
		String defaultField;
		public String publicField;
		
		private String privateFieldNoShadow;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class ChildShadow extends BaseShadow {
		
		private String privateField;
		protected String protectedField;
		String defaultField;
		public String publicField;	
		
	}
}
