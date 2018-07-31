package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.field.FieldValueGetter;

public interface AssertionBuilderComparison extends AssertionBuilder {

	FieldValueGetter getFieldValueGetter1();
	AssertionBuilderComparison setFieldValueGetter1(FieldValueGetter fieldValueGetter);
	AssertionBuilderComparison setFieldValueGetter1(Object object,String...names);
	
	FieldValueGetter getFieldValueGetter2();
	AssertionBuilderComparison setFieldValueGetter2(FieldValueGetter fieldValueGetter);
	AssertionBuilderComparison setFieldValueGetter2(Object object,String...names);
	
	ComparisonOperator getOperator();
	AssertionBuilderComparison setOperator(ComparisonOperator operator);
	
	//String getValueName();
	//AssertionBuilderComparison setValueName(String name);
	
	AssertionBuilderComparison setIsAffirmation(Boolean value);
	
}
