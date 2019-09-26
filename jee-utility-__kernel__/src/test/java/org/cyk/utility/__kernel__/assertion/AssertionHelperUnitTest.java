package org.cyk.utility.__kernel__.assertion;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class AssertionHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildAssertionComparison_literal_null_equal_literal_null() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(null), ComparisonOperator.EQ, __inject__(Value.class).set(null));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionComparison_literal_1_equal_literal_1() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(1), ComparisonOperator.EQ, __inject__(Value.class).set(1));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionComparison_literal_2_greaterThan_literal_1() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(2), ComparisonOperator.GT, __inject__(Value.class).set(1));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionComparison_literal_2_lesserThan_literal_1() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(2), ComparisonOperator.LT, __inject__(Value.class).set(1));
		assertThat(assertion.getValue()).isFalse();
	}
	
	@Test
	public void buildAssertionComparison_literal_null_gt_literal_minus1() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(null), ComparisonOperator.GT, __inject__(Value.class).set(-1));
		assertThat(assertion.getValue()).isFalse();
	}
	
	@Test
	public void buildAssertionComparison_field_null_equal_literal_null() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class)
				.setFieldInstance(__inject__(FieldInstancesRuntime.class).get(Class.class, "integer01")), ComparisonOperator.EQ, __inject__(Value.class).set(null));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionComparison_field_123_equal_literal_123() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class)
				.setFieldInstance(__inject__(FieldInstancesRuntime.class).get(Class.class, "integer01")).setObject(new Class().setInteger01(123))
				, ComparisonOperator.EQ, __inject__(Value.class).set(123));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionComparison_identifier_lt() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(2), ComparisonOperator.LT, __inject__(Value.class).set(1));
		assertThat(assertion.getIdentifier()).isEqualTo("assertion.comparison.lt");
	}
	
	@Test
	public void buildAssertionComparison_messageWhenValueIsNotTrue_lt() {
		Assertion assertion = AssertionHelper.buildAssertionComparison(__inject__(Value.class).set(2), ComparisonOperator.LT, __inject__(Value.class).set(1));
		assertThat(assertion.getMessageWhenValueIsNotTrue()).isEqualTo("la comparaison n'est pas correcte : 2 < 1");
	}
	
	@Test
	public void buildAssertionNull_literal_null() {
		Assertion assertion = AssertionHelper.buildAssertionNull(__inject__(Value.class).set(null));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionNull_field_null() {
		Assertion assertion = AssertionHelper.buildAssertionNull(__inject__(Value.class)
				.setFieldInstance(__inject__(FieldInstancesRuntime.class).get(Class.class, "integer01")));
		assertThat(assertion.getValue()).isTrue();
	}
	
	@Test
	public void buildAssertionNull_field_notNull() {
		Assertion assertion = AssertionHelper.buildAssertionNull(__inject__(Value.class)
				.setFieldInstance(__inject__(FieldInstancesRuntime.class).get(Class.class, "integer01")).setObject(new Class().setInteger01(15)));
		assertThat(assertion.getValue()).isFalse();
	}
	
	@Test
	public void buildAssertionNull_identifier() {
		Assertion assertion = AssertionHelper.buildAssertionNull(__inject__(Value.class).set(123));
		assertThat(assertion.getIdentifier()).isEqualTo("assertion.null");
	}
	
	@Test
	public void buildAssertionNull_messageWhenValueIsNotTrue() {
		Assertion assertion = AssertionHelper.buildAssertionNull(__inject__(Value.class).set(123));
		assertThat(assertion.getMessageWhenValueIsNotTrue()).isEqualTo("la valeur nulle");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class {
		private Integer integer01,integer02;
	}
}
