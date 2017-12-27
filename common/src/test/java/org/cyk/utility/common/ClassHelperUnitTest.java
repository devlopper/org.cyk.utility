package org.cyk.utility.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.ClassHelperUnitTest.A.A1.A11.A111;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.AbstractReflectionHelper;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class ClassHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void buildName(){
		ClassHelper.NameBuilder nameBuilder = ClassHelper.getInstance().instanciateOne(ClassHelper.NameBuilder.class);
		Properties properties = nameBuilder.getPropertiesMap();
		properties.setPackageBaseNameSet(new LinkedHashSet<String>(Arrays.asList("pack01")));
		properties.setPrefixSet(new LinkedHashSet<String>(Arrays.asList("pre01")));
		properties.setClassSimpleNameSet(new LinkedHashSet<String>(Arrays.asList("MyClass")));
		properties.setSuffixSet(new LinkedHashSet<String>(Arrays.asList("$suf01")));
		Set<String> names = nameBuilder.execute();
		assertCollectionContains(names, "pack01.pre01MyClass$suf01");
		
		nameBuilder = ClassHelper.getInstance().instanciateOne(ClassHelper.NameBuilder.class);
		properties = nameBuilder.getPropertiesMap();
		properties.setClass(MyClass.class);
		properties.setPrefixSet(new LinkedHashSet<String>(Arrays.asList("pre01")));
		properties.setSuffixSet(new LinkedHashSet<String>(Arrays.asList("$suf01")));
		names = nameBuilder.execute();
		assertCollectionContains(names, MyClass.class.getPackage().getName()+".pre01MyClass$suf01");
		
	}
	
	@Test
	public void getIdentifier(){
		assertEquals("myclass", ClassHelper.getInstance().getIdentifier(MyClass.class));
	}
	
	@Test
	public void getClassByIdentifier(){
		assertEquals("MyClass", ClassHelper.getInstance().getClassByIdentifier("myclass").getSimpleName());
		//assertEquals(MyClass.class, ClassHelper.getInstance().getClassByIdentifier("myclass"));
	}
	
	@Test
	public void getParameterAt(){
		assertEquals(String.class, ClassHelper.getInstance().getParameterAt(FieldHelper.getInstance().get(ParamClass.class, "strings"), 0, String.class));
	}
	
	@Test
	public void getContainerNames(){
		assertList(ClassHelper.getInstance().getContainerNames(A111.class), Arrays.asList("A11","A1","A","ClassHelperUnitTest"));
	}
	
	@Test
	public void getByName(){
		AssertionHelper.getInstance().assertEquals(ClassHelper.class, ClassHelper.getInstance().getByName(ClassHelper.class.getName()));
		AssertionHelper.getInstance().assertEquals(null,ClassHelper.getInstance().getByName(ClassHelper.class.getName()+"XXX",Boolean.TRUE));
	}
	
	@Test(expected=RuntimeException.class)
	public void getByNameException(){
		ClassHelper.getInstance().getByName(ClassHelper.class.getName()+"XXX",Boolean.FALSE);
	}
	
	@Test
	public void getClasses(){
		assertCollectionContains(ClassHelper.getInstance().get("org.cyk",AbstractHelper.class), ClassHelper.class);
		assertCollectionContains(new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class).execute()
				, ClassHelper.class);
		assertCollectionContains(new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class)
				.addModifiers(java.lang.reflect.Modifier.ABSTRACT).execute(), AbstractReflectionHelper.class);
		assertCollectionContains(new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class)
				.addAnnotationClasses(Singleton.class).execute(), ClassHelper.class);
		assertCollectionContains(new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class)
				.addAnnotationClasses(Named.class).execute(), MyAnnotatedHelper2.class);
		
		System.out.println(ClassHelper.getInstance().getClasses());
		
	}
	
	@Test
	public void getConstructor(){
		assertNotNull(ClassHelper.getInstance().getConstructor(MyClass.class, new Class[]{Number.class}));
		assertNotNull(ClassHelper.getInstance().getConstructor(MyClass.class, new Class[]{Integer.class}));
		assertNotNull(ClassHelper.getInstance().getConstructor(MyClass.class, new Class[]{Long.class}));
		assertNotNull(ClassHelper.getInstance().getConstructor(MyClass.class, new Class[]{BigDecimal.class}));
		assertNull(ClassHelper.getInstance().getConstructor(MyClass.class, new Class[]{Boolean.class}));
	}
	
	@Test
	public void instanciate(){
		assertNotNull(new ClassHelper.Instanciation.Adapter.Default<ClassHelper>(ClassHelper.class).execute());
		assertThat("instance is not a class helper", new ClassHelper.Instanciation.Adapter.Default<ClassHelper>(ClassHelper.class).execute().getClass().equals(ClassHelper.class));
	}
	
	/**/
	
	public static class MyHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
		
	}
	
	@Singleton
	public static class MyAnnotatedHelper extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
	@Named
	public static class MyAnnotatedHelper2 extends AbstractHelper {
		private static final long serialVersionUID = 1L;
	}
	
	public static class A  {
		public static class A1  {
			public static class A11  {
				public static class A111  {
					
					
				}	
				
			}	
			
		}	
		
	}

	public static class MyClass {
		public MyClass(Number number) {}
	}
	
	@Getter @Setter
	public static class ParamClass {
		
		private List<String> strings;
		
	}
}
