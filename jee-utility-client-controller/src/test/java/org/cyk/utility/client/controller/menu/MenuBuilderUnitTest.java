package org.cyk.utility.client.controller.menu;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.function.FunctionRunnableMap;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierStringBuilderExtensionFunctionRunnableImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierStringBuilderExtensionImpl;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapperImpl;
import org.cyk.utility.request.RequestPropertyValueGetterImpl;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;
import org.junit.Test;

public class MenuBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	static {
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class,1);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierToUrlStringMapperImpl.class, NavigationIdentifierToUrlStringMapperFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(RequestPropertyValueGetterImpl.class, RequestPropertyValueGetterFunctionRunnableImpl.class);
		__inject__(FunctionRunnableMap.class).set(NavigationIdentifierStringBuilderExtensionImpl.class, NavigationIdentifierStringBuilderExtensionFunctionRunnableImpl.class,1);
	}
	
	//@Test
	public void build_01() {
		build(1);
	}
	
	//@Test
	public void build_02() {		
		build(2);
	}
	
	@Test
	public void getMenuBuilder_durationIsLessThan1Second_when_1Item_1Level() {		
		getMenuBuilder(1,1,1000);
	}
	
	@Test
	public void executeMenuBuilder_durationIsLessThan1Second_when_1Item_1Level() {		
		executeMenuBuilder(1,1,1000);
	}
	
	@Test
	public void getMenuBuilder_durationIsLessThan1Second_when_10Item_1Level() {		
		getMenuBuilder(10,1,1000);
	}
	
	@Test
	public void executeMenuBuilder_durationIsLessThan1Second_when_10Item_1Level() {		
		executeMenuBuilder(10,1,1000);
	}
	
	/*
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
	
	public static MenuBuilder getMenuBuilder(Integer numberOfItem,Integer numberOfLevel) {
		MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
		if(numberOfItem!=null && numberOfItem>0) {
			for(Integer index = 0 ; index < numberOfItem ; index = index + 1)
				menuBuilder.addItems(getMenuItemBuilder(numberOfItem, numberOfLevel));
		}
		return menuBuilder;
	}
	
	public static MenuItemBuilder getMenuItemBuilder(Integer numberOfItem,Integer numberOfLevel) {
		MenuItemBuilder item = __inject__(MenuItemBuilder.class);
		if(numberOfLevel!=null && numberOfLevel>0) {
			if(numberOfItem!=null && numberOfItem>0) {
				for(Integer index = 0 ; index < numberOfItem ; index = index + 1)
					item.addChild(getMenuItemBuilder(numberOfItem, numberOfLevel-1));
			}
		}
		return item;
	}
	
	public static void build(Integer count) {		
		for(int i = 1 ; i <= count ; i = i + 1) {
			DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
			getMenuBuilder().execute();
			System.out.println("MenuBuilderUnitTest.build() DURATION : "+__inject__(DurationStringBuilder.class).setDuration(durationBuilder.setEndToNow().execute().getOutput()).execute().getOutput());
		}
	}
	
	public void getMenuBuilder(Integer numberOfItem,Integer numberOfLevel,Integer expectedMaximumNumberOfMillisecond) {	
		Long t = System.currentTimeMillis();
		getMenuBuilder(numberOfItem,numberOfLevel);
		Long durationInMillisecond = System.currentTimeMillis() - t;
		DurationStringBuilder durationStringBuilder = __inject__(DurationStringBuilder.class);
		durationStringBuilder.getDurationBuilder(Boolean.TRUE).setNumberOfMillisecond(durationInMillisecond);
		assertionHelper.assertTrue("Too much time taken to get menu : "+durationStringBuilder.execute().getOutput(),durationInMillisecond<=expectedMaximumNumberOfMillisecond);	
	}
	
	public void executeMenuBuilder(Integer numberOfItem,Integer numberOfLevel,Integer expectedMaximumNumberOfMillisecond) {	
		Long t = System.currentTimeMillis();
		getMenuBuilder(numberOfItem,numberOfLevel).execute();
		Long durationInMillisecond = System.currentTimeMillis() - t;
		DurationStringBuilder durationStringBuilder = __inject__(DurationStringBuilder.class);
		durationStringBuilder.getDurationBuilder(Boolean.TRUE).setNumberOfMillisecond(durationInMillisecond);
		assertionHelper.assertTrue("Too much time taken to build menu : "+durationStringBuilder.execute().getOutput(),durationInMillisecond<=expectedMaximumNumberOfMillisecond);	
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
