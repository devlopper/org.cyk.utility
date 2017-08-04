package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Between;
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
	public void jpqlBetween(){
		JavaPersistenceQueryLanguage jpql = new JavaPersistenceQueryLanguage();
		Between between = new Between.Adapter.Default.JavaPersistenceQueryLanguage().setStructuredQueryLanguageBuilder(jpql);
		AssertionHelper.getInstance().assertEquals("r.instantInterval.from.year BETWEEN :fromYear AND :toYear"
				, between.setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.year").execute());
		AssertionHelper.getInstance().assertEquals("r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear"
				, between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.monthOfYear").execute());
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
		JavaPersistenceQueryLanguage jpql = new JavaPersistenceQueryLanguage();
		Where where = new Where.Adapter.Default.JavaPersistenceQueryLanguage().setStructuredQueryLanguageBuilder(jpql);
		
		where.leftParathensis()
				.addBetween("r.instantInterval.from.year").and().addBetween("r.instantInterval.from.monthOfYear").and().addBetween("r.instantInterval.from.dayOfMonth")
			.rightParathensis()
			.or()
			.leftParathensis()
				.addBetween("r.instantInterval.to.year").and().addBetween("r.instantInterval.to.monthOfYear").and().addBetween("r.instantInterval.to.dayOfMonth")
			.rightParathensis()
			.or()
			.leftParathensis()
				.addLessThanOrEqual("r.instantInterval.from.year","fromYear").and().addLessThanOrEqual("r.instantInterval.from.monthOfYear","fromMonthOfYear")
					.and().addLessThanOrEqual("r.instantInterval.from.dayOfMonth","fromDayOfMonth").and().addGreaterThanOrEqual("r.instantInterval.to.year","toYear")
					.and().addGreaterThanOrEqual("r.instantInterval.to.monthOfYear","toMonthOfYear").and().addGreaterThanOrEqual("r.instantInterval.to.dayOfMonth","toDayOfMonth")
			.rightParathensis()
			;
		
		AssertionHelper.getInstance().assertEquals("SELECT r FROM ScheduleItem r WHERE (r.instantInterval.from.year BETWEEN :fromYear AND :toYear "
				+ "AND r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear AND r.instantInterval.from.dayOfMonth "
				+ "BETWEEN :fromDayOfMonth AND :toDayOfMonth) OR (r.instantInterval.to.year BETWEEN :fromYear AND :toYear "
				+ "AND r.instantInterval.to.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear AND r.instantInterval.to.dayOfMonth "
				+ "BETWEEN :fromDayOfMonth AND :toDayOfMonth) OR (r.instantInterval.from.year<=:fromYear AND r.instantInterval.from.monthOfYear<=:fromMonthOfYear AND "
				+ "r.instantInterval.from.dayOfMonth<=:fromDayOfMonth AND r.instantInterval.to.year>=:toYear AND r.instantInterval.to.monthOfYear>=:toMonthOfYear AND "
				+ "r.instantInterval.to.dayOfMonth>=:toDayOfMonth)", jpql.addTupleCollection("ScheduleItem", "r")
				.addWhere(where.execute())
				.execute());
	}
	
	
}
