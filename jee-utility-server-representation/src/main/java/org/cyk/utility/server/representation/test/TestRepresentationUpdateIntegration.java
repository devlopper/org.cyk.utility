package org.cyk.utility.server.representation.test;

import java.util.Map;

public interface TestRepresentationUpdateIntegration extends TestRepresentationTransactionIntegration {

	TestRepresentationUpdateIntegration setFieldValuesMap(Map<Object,Map<String,Object>> fieldValuesMap);
	Map<Object,Map<String,Object>> getFieldValuesMap();
	Map<String,Object> getFieldValuesMap(Object object);
	TestRepresentationUpdateIntegration setFieldValuesMap(Object object,Map<String,Object> fieldValuesMap);
	
}
