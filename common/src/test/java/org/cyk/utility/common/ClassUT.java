package org.cyk.utility.common;

import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class ClassUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {}
	
	@Test
	public void getClasses(){
		System.out.println( new ClassHelper().get("org.cyk.utility.common",AbstractHelper.class) );
	}
	
}
