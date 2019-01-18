package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.request.RequestParameterValueGetterImpl;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;
import org.junit.Test;

public class MenuBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestParameterValueGetterImpl.class, RequestParameterValueGetterFunctionRunnableImpl.class);
	}
	
	@Test
	public void build_01() {
		build(1);
	}
	
	@Test
	public void build_02() {		
		build(2);
	}
	/*
	@Test
	public void build_03() {		
		build(3);
	}
	
	@Test
	public void build_10() {		
		build(10);
	}
	
	@Test
	public void build_20() {		
		build(20);
	}*/
	
	/**/
	/*
	private static class MyEntity {
		
	}
	*/
	/**/
	
	public static MenuBuilder getMenuBuilder() {
		MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
		menuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setCommandableName("Layout")
					.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index")
					,__inject__(MenuItemBuilder.class).setCommandableName("Table"))
				,__inject__(MenuItemBuilder.class).setCommandableName("Commandable")					
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Button Index").setCommandableNavigationIdentifier("commandableButtonIndexView")
									,__inject__(MenuItemBuilder.class).setCommandableName("Commandable Navigation Index").setCommandableNavigationIdentifier("commandableNavigationIndexView")
							)
							
				,__inject__(MenuItemBuilder.class).setCommandableName("Grid")
					.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("gridIndexView")
							)
				,__inject__(MenuItemBuilder.class).setCommandableName("View")
					.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("viewIndexView")
							)
				,__inject__(MenuItemBuilder.class).setCommandableName("Navigation")
					.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("navigationIndexView")
							)
				
				,__inject__(MenuItemBuilder.class).setCommandableName("Entités")
					.addChild(
						__inject__(MenuItemBuilder.class).setCommandableName("List").addEntitiesList(MyEntity.class,VerySimpleEntity.class)	
						,__inject__(MenuItemBuilder.class).setCommandableName("Selection pour traitement")
							.addEntitiesSelect(MyEntity.class,VerySimpleEntity.class)
							.addEntitySelect(VerySimpleEntity.class,"validate")
							.addEntitySelect(VerySimpleEntity.class,"transfer")
						)
				,__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(MyEntity.class)
					.addChild(__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(MyEntity.class))
							,__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))
							)
					);
		return menuBuilder;
	}
	
	public static void build(Integer count) {		
		for(int i = 1 ; i <= count ; i = i + 1) {
			DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
			getMenuBuilder().execute();
			System.out.println("MenuBuilderUnitTest.build() DURATION : "+__inject__(DurationStringBuilder.class).setDuration(durationBuilder.setEndNow().execute().getOutput()).execute().getOutput());
		}
	}
	
	public static class NavigationIdentifierToUrlStringMapperFunctionRunnableImpl extends AbstractFunctionRunnableImpl<NavigationIdentifierToUrlStringMapper> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public NavigationIdentifierToUrlStringMapperFunctionRunnableImpl() {
			setRunnable(new Runnable() {
				@Override
				public void run() {
					//Object identifier = getFunction().getIdentifier();
					//if("__entity__ListView".equals(identifier))
					//	setOutput("http://localhost:8080/list.jsf");
					setOutput("http://localhost:8080/");
				}
			});
		}
		
	}
	
}
