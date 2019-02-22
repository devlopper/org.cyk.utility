package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetterImpl;
import org.cyk.utility.request.RequestParameterValueGetterImpl;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class WindowBuilderPerformanceUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;

	static {
		__inject__(FunctionRunnableMap.class).set(MenuBuilderMapGetterImpl.class, MenuBuilderMapGetterFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(RequestParameterValueGetterImpl.class, RequestParameterValueGetterFunctionRunnableImpl.class);
	}
	
	@Test
	public void createWindowBuilder() {
		Request request = new Request();
		RequestParameterValueGetterFunctionRunnableImpl.REQUEST = request;
		
		VerySimpleEntityEditWindowBuilder builder = __inject__(VerySimpleEntityEditWindowBuilder.class);
		//function.getAction().setEntityClass(Model.class).getEntities(Boolean.TRUE).add(new Model());
		//WindowBuilder windowBuilder = 
		builder.execute().getOutput();
		//assertionHelper.assertEquals(null, fun);
	}
	
}
