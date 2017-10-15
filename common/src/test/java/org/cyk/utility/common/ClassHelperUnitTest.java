package org.cyk.utility.common;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.ClassHelperUnitTest.A.A1.A11.A111;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.AbstractReflectionHelper;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ClassHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {}
	
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
}
