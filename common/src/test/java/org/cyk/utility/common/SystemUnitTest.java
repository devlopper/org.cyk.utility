package org.cyk.utility.common;

import org.cyk.utility.common.helper.SystemHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class SystemUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void getProperty(){
		assertNull(SystemHelper.getInstance().getProperty("nullsystemproperty"));
		assertEquals("value1",SystemHelper.getInstance().getProperty("property1"));
	}
	
	@Test
	public void getEnvironmentVariable(){
		assertNull(SystemHelper.getInstance().getEnvironmentVariable("nullenvironmentvariable"));
		assertNotNull(SystemHelper.getInstance().getEnvironmentVariable("path"));
	}
	

}
