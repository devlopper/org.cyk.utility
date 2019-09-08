package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;

@Deprecated
public interface AssertionBuilderComparison extends AssertionBuilder {

	ComparisonOperator getOperator();
	AssertionBuilderComparison setOperator(ComparisonOperator operator);
	
	AssertionValue getAssertedValue1();
	AssertionValue getAssertedValue1(Boolean instanciateIfNull);
	AssertionBuilderComparison setAssertedValue1(AssertionValue assertionValue);
	
	AssertionValue getAssertedValue2();
	AssertionValue getAssertedValue2(Boolean instanciateIfNull);
	AssertionBuilderComparison setAssertedValue2(AssertionValue assertionValue);
	
	AssertionBuilderComparison setIsAffirmation(Boolean value);
	
}
