package org.cyk.utility.client.controller.menu;

import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetterImpl;
import org.cyk.utility.client.controller.component.menu.MenuGetter;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.client.controller.session.SessionAttributeGetterImpl;
import org.cyk.utility.client.controller.session.SessionAttributeSetterImpl;
import org.cyk.utility.request.RequestParameterValueGetterImpl;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;
import org.cyk.utility.scope.ScopeSession;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class MenuGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestParameterValueGetterImpl.class, RequestParameterValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(SessionAttributeGetterImpl.class, SessionAttributeGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(SessionAttributeSetterImpl.class, SessionAttributeSetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(MenuBuilderMapGetterImpl.class, MenuBuilderMapGetterFunctionRunnableImpl.class);
	}
	
	@Test
	public void get_01() {
		build(1);
	}
	
	@Test
	public void get_02() {		
		build(2);
	}
	
	@Test
	public void get_03() {		
		build(3);
	}
	
	@Test
	public void get_10() {		
		build(10);
	}
	
	@Test
	public void get_20() {		
		build(20);
	}
	
	/**/
	/*
	private static class MyEntity {
		
	}
	*/
	/**/
	
	
	
	public static void build(Integer count) {		
		for(int i = 1 ; i <= count ; i = i + 1) {
			//DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
			__inject__(MenuGetter.class).setScopeClass(ScopeSession.class).execute();
			//System.out.println("MenuGetterUnitTest.build() DURATION : "+__inject__(DurationStringBuilder.class).setDuration(durationBuilder.setEndNow().execute().getOutput()).execute().getOutput());
		}
	}
	
	
	
}
