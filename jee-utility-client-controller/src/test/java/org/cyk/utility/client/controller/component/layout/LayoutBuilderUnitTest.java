package org.cyk.utility.client.controller.component.layout;

import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.ApplicationScopeLifeCycleListener;
import org.cyk.utility.css.StyleClassBuilderWidthImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class LayoutBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void build() {
		Layout layout = __inject__(LayoutBuilder.class).execute().getOutput();
		assertionHelper.assertNull(layout.getChildren());
		//assertionHelper.assertEquals("ui-g", layout.getStyle().getClassesAsString());
	}
	
	@Test
	public void buildWithOneItem() {
		__inject__(FunctionRunnableMap.class).set(StyleClassBuilderWidthImpl.class, StyleClassBuilderWidthCssPrimefacesGridFunctionRunnableImpl.class);
		Layout layout = __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
				.execute().getOutput();
		assertionHelper.assertNotNull(layout.getChildren());
		assertionHelper.assertEquals(1, layout.getChildren().size());
		LayoutItem layoutItem = layout.getChildAt(0);
		assertionHelper.assertNotNull(layoutItem.getStyle());
		assertionHelper.assertEquals("ui-g-2 ui-xl-2 ui-lg-2 ui-md-2 ui-sm-12", layoutItem.getStyle().getClassesAsString());
		//assertionHelper.assertEquals("ui-g", layout.getStyle().getClassesAsString());
	}
	
	
}
