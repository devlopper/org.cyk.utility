package org.cyk.utility.regularexpression;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class RegularExpressionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void matchPackageNameSubStringTokenLocation() {
		String expression = "[.]{0,1}mypack[.]{0,1}";
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("a.mypack.b", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("a.mypack", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("mypack.b", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("mypack", expression));
	}
	
	@Test
	public void matchSystemServerRepresentationEntity() {
		String expression = "representation.entities..+Dto$";
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("representation.entities.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("representation.entities.p1.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("p.representation.entities.p1.MyClassDto", expression));
		assertionHelper.assertTrue(__inject__(RegularExpressionHelper.class).match("p.representation.entities.MyClassDto", expression));
	}
}
