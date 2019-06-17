package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class MenuItemBuilderBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,1);
	}
	
	@Test
	public void navigation_list() {
		/*MenuItemBuilder builder = __inject__(MenuItemBuilder.class).setCommandableName("Lister")
				.setCommandableNavigationIdentifierAndParameters("__entity__ListView",new Object[] {"class","myentity","action","list"});
		MenuItem item = builder.execute().getOutput();
		assertionHelper.assertNotNull(item.getCommandable());
		assertionHelper.assertNotNull(item.getCommandable().getNavigation());
		assertionHelper.assertEquals("http://localhost:8080/list.jsf?class=myentity&action=list",item.getCommandable().getNavigation().getUniformResourceLocator().toString());
		*/
	}
	
	
	/**/
	/*
	private static class MyEntity {
		
	}
	*/
	/**/
	
	public static class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					Object identifier = getFunction().getIdentifier();
					if("__entity__ListView".equals(identifier))
						setOutput("http://localhost:8080/list.jsf");
					
				}
			});
		}
		
	}
	
}
