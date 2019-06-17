package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class AssertionBuilderComparisonUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsLiteralIs1_value2IsLiteralIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setValue(1).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setValue(2).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.LT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsLiteralIs2_value2IsLiteralIs1() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setValue(2).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setValue(1).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.GT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}

	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsLiteralIs2_value2IsFieldIs1() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setValue(2).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(1)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.GT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsFieldIs1_value2IsLiteralIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(1)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setValue(2).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.LT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsFieldIs1_value2IsFieldIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(1)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger2(2)).setField("integer2")).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.LT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsFieldIs3_value2IsFieldIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(3)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger2(2)).setField("integer2")).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.GT)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsFieldIs2_value2IsFieldIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(2)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger2(2)).setField("integer2")).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.EQ)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_value1IsFieldIs1_value8IsFieldIs2() {
		Assertion assertion = __inject__(AssertionBuilderComparison.class)
				.getAssertedValue1(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger(8)).setField("integer")).getParentAs(AssertionBuilderComparison.class)
				.getAssertedValue2(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass().setInteger2(2)).setField("integer2")).getParentAs(AssertionBuilderComparison.class)
				.setOperator(ComparisonOperator.NEQ)
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
}
