package org.cyk.utility.common;

import java.lang.reflect.Modifier;

import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ClassHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {}
	
	@Test
	public void getClasses(){
		System.out.println( new ClassHelper().get("org.cyk.utility.common",AbstractHelper.class) );
		System.out.println( new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class).execute());
		System.out.println( new ClassHelper.Get.Adapter.Default(Package.getPackage("org.cyk.utility.common")).setBaseClass(AbstractHelper.class)
				.addModifiers(Modifier.ABSTRACT).execute());
	}
	
}
