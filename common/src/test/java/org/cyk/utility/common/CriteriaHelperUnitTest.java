package org.cyk.utility.common;

import org.cyk.utility.common.helper.CriteriaHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class CriteriaHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertNull(){
		assertEquals(Boolean.TRUE, new CriteriaHelper.Criteria.String().isNull());
		assertEquals(Boolean.TRUE, new CriteriaHelper.Criteria.String().set((String)null).isNull());
		assertEquals(Boolean.TRUE, new CriteriaHelper.Criteria.String().set("").isNull());
		assertEquals(Boolean.TRUE, new CriteriaHelper.Criteria.String().set(" ").isNull());
		assertEquals(Boolean.FALSE, new CriteriaHelper.Criteria.String().set("maval").isNull());
	}
	

}
