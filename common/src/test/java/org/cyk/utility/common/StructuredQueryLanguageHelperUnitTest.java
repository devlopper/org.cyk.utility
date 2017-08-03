package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Operator;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class StructuredQueryLanguageHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void jpqlWhere(){
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.Where.Adapter.Default.JavaPersistenceQueryLanguage()
			.addTokens("c1","=","v1").execute());
	}
	
	@Test
	public void jpql(){
		/*JavaPersistenceQueryLanguage jpql = new JavaPersistenceQueryLanguage();
		AssertionHelper.getInstance().assertEquals("SELECT myrecord", jpql.addExpressions(Operator.SELECT, "myrecord").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord WHERE c1", jpql.addExpressions(Operator.WHERE, "c1").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord FROM t1 WHERE c1", jpql.addExpressions(Operator.FROM, "t1").execute());
		AssertionHelper.getInstance().assertEquals("SELECT myrecord FROM t1 WHERE c1 ORDER BY t1.f1", jpql.addExpressions(Operator.ORDER_BY, "t1.f1").execute());
		
		AssertionHelper.getInstance().assertEquals("SELECT r FROM ScheduleItem r WHERE r.instantInterval.from.year BETWEEN :fromYear AND :toYear "
				+ "AND r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear AND r.instantInterval.from.dayOfMonth "
				+ "BETWEEN :fromDayOfMonth AND :toDayOfMonth", new JavaPersistenceQueryLanguage().addExpressions(Operator.SELECT, "r")
				.addExpressions(Operator.FROM, "ScheduleItem r").addExpressions(Operator.WHERE, "r.instantInterval.from.year BETWEEN :fromYear AND :toYear "
						+ "AND r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear AND r.instantInterval.from.dayOfMonth "
						+ "BETWEEN :fromDayOfMonth AND :toDayOfMonth").execute());
		*/
		AssertionHelper.getInstance().assertEquals("SELECT r FROM ScheduleItem r WHERE r.instantInterval.from.year BETWEEN :fromYear AND :toYear "
				+ "AND r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear AND r.instantInterval.from.dayOfMonth "
				+ "BETWEEN :fromDayOfMonth AND :toDayOfMonth", new JavaPersistenceQueryLanguage().addTupleCollection("ScheduleItem", "r")
				.addExpressions(Operator.WHERE, StructuredQueryLanguageHelper.getInstance().getBetween("r.instantInterval.from.year", ":fromYear", ":toYear")
						+" AND "+StructuredQueryLanguageHelper.getInstance().getBetween("r.instantInterval.from.monthOfYear", ":fromMonthOfYear", ":toMonthOfYear")+
						" AND "+StructuredQueryLanguageHelper.getInstance().getBetween("r.instantInterval.from.dayOfMonth", ":fromDayOfMonth", ":toDayOfMonth")).execute());
	}
	
	
}
