package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Operator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class StructuredQueryLanguageHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void jpql(){
		JavaPersistenceQueryLanguage jpql = new JavaPersistenceQueryLanguage();
		AssertionHelper.getInstance().assertEquals("SELECT myrecord", jpql.addExpressions(Operator.SELECT, "myrecord").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord WHERE c1", jpql.addExpressions(Operator.WHERE, "c1").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord FROM t1 WHERE c1", jpql.addExpressions(Operator.FROM, "t1").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord FROM t1 WHERE c1 ORDER BY t1.f1", jpql.addExpressions(Operator.ORDER_BY, "t1.f1").execute());
	}
	
	
}
