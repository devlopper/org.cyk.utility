package org.cyk.utility.common.property;

import org.cyk.utility.common.helper.FilterHelper;
import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class FluentPropertyBeanIntrospectorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isIntrospectable(){
		TestCase testCase = new TestCase();
		FluentPropertyBeanIntrospector fluentPropertyBeanIntrospector = new FluentPropertyBeanIntrospector()
				.setTargetClassNameIncludedRegularExpression("(^org.cyk.)(.+)(.model.)");
		testCase.assertEquals(Boolean.TRUE,fluentPropertyBeanIntrospector .isIntrospectable("org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory"));
		testCase.assertEquals(Boolean.FALSE, fluentPropertyBeanIntrospector.isIntrospectable("org.cyk.utility.common.helper.FilterHelper$Filter"));
		testCase.assertEquals(Boolean.FALSE, fluentPropertyBeanIntrospector.isIntrospectable(FilterHelper.Filter.class));
	}
	
}
