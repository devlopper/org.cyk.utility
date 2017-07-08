package org.cyk.utility.common;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class MappingUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
 
	@Test
	public void mapStringToString(){
		assertEquals("mystring", new StringHelper.ToStringMapping.Adapter.Default("mystringid").execute());
	}
		
}
