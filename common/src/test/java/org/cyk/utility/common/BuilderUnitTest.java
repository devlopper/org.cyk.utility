package org.cyk.utility.common;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class BuilderUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Test
	public void buildString(){
		assertThat("output is not null", new StringHelper.Builder.Adapter.Default().execute() == null);
		assertEquals("mystring", new StringHelper.Builder.Adapter.Default().setOutput("mystring").execute());
		
	}
		
}
