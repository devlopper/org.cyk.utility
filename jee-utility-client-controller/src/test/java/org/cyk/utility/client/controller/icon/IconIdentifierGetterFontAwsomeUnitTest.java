package org.cyk.utility.client.controller.icon;

import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class IconIdentifierGetterFontAwsomeUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void identifier_plus_is_fa_fa_plus() {
		assertionHelper.assertEquals("fa fa-plus", __inject__(IconIdentifierGetter.class).setIcon(Icon.PLUS).execute().getOutput());
	}
	
	
	
}
