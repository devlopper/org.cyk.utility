package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.identifier.resource.QueryParameterValueGetter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeNormal;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UniformResourceIdentifierHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(ApplicationScopeLifeCycleListener.class).__initialize__(null);
	}
	
	@Test
	public void map() {
		ParameterName.WINDOW_RENDER_TYPE_CLASS.setType(WindowRenderType.class);
		ParameterName.addClasses(WindowRenderTypeNormal.class,WindowRenderTypeDialog.class);
		System.out.println(ParameterName.MAP);
		Object value = UniformResourceIdentifierHelper.mapParameterValue(ParameterName.WINDOW_RENDER_TYPE_CLASS.getValue(),new QueryParameterValueGetter() {			
			@Override
			public String get(String name) {
				if(name.equals("windowrendertypeclass"))
					return "windowrendertypenormal";
				return null;
			}
		});
		assertThat(value).isEqualTo(WindowRenderTypeNormal.class);
	}
	
	
	
}
