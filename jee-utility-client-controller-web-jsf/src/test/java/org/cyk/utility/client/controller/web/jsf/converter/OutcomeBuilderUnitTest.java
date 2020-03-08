package org.cyk.utility.client.controller.web.jsf.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.client.controller.web.jsf.OutcomeBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class OutcomeBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void build() {
		assertThat(OutcomeBuilder.getInstance().build(MyClass.class, Action.CREATE)).isEqualTo("myClassCreateView");
	}
	
	public static class MyClass {
		
	}
}
