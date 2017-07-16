package org.cyk.utility.common;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.AbstractReflectionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ClassHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {}
	
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
	
}
