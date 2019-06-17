package org.cyk.utility.criteria;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CriteriaStringUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createNull(){
		Criteria criteria = __inject__(Criteria.class);
		assertionHelper.assertNull(criteria.getValuesMatch());
	}
	
}
