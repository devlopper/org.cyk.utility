package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMapGetter;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.entities.entitynoform.EntityNoForm;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verycomplexentity.VeryComplexEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.scope.ScopeSession;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;

public class MenuBuilderMapGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<MenuBuilderMapGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MenuBuilderMapGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(__inject__(MenuRenderTypeRowBar.class));
				menuBuilder.addItems(
						__inject__(MenuItemBuilder.class).setCommandableName("Layout").setCommandableOutputProperty(Properties.ICON, "fa fa-child")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index")
							,__inject__(MenuItemBuilder.class).setCommandableName("Table"))
						,__inject__(MenuItemBuilder.class).setCommandableName("Page").setCommandableIcon(Icon.FILE)
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("pageIndexView")
							,__inject__(MenuItemBuilder.class).setCommandableName("Custom").setCommandableNavigationIdentifier("pageCustomView")
							,__inject__(MenuItemBuilder.class).setCommandableName("No Theme").setCommandableNavigationIdentifier("pageNoThemeView"))
						,__inject__(MenuItemBuilder.class).setCommandableName("Commandable").setCommandableOutputProperty(Properties.ICON, "fa fa-cube")					
								.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Button Index").setCommandableNavigationIdentifier("commandableButtonIndexView")
										,__inject__(MenuItemBuilder.class).setCommandableName("Commandable Navigation Index").setCommandableNavigationIdentifier("commandableNavigationIndexView")
								)									
						,__inject__(MenuItemBuilder.class).setCommandableName("Grid").setCommandableOutputProperty(Properties.ICON, "fa fa-edit")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("gridIndexView")
									)
						,__inject__(MenuItemBuilder.class).setCommandableName("Input").setCommandableOutputProperty(Properties.ICON, "fa-glass")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("inputIndexView")
									,__inject__(MenuItemBuilder.class).setCommandableName("Text").setCommandableNavigationIdentifier("inputTextView")
									,__inject__(MenuItemBuilder.class).setCommandableName("File").setCommandableNavigationIdentifier("inputFileView")
									,__inject__(MenuItemBuilder.class).setCommandableName("Choice").setCommandableNavigationIdentifier("inputChoiceView")
									)	
						,__inject__(MenuItemBuilder.class).setCommandableName("Output").setCommandableOutputProperty(Properties.ICON, "fa-image")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Text").setCommandableNavigationIdentifier("outputTextView")
									,__inject__(MenuItemBuilder.class).setCommandableName("Link").setCommandableNavigationIdentifier("outputLinkView")
									,__inject__(MenuItemBuilder.class).setCommandableName("File").setCommandableNavigationIdentifier("outputFileView")
									)	
						,__inject__(MenuItemBuilder.class).setCommandableName("Hierarchy").setCommandableOutputProperty(Properties.ICON, "fa fa-tree")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Organigram").setCommandableNavigationIdentifier("hierarchyOrganigramView")
									)
						,__inject__(MenuItemBuilder.class).setCommandableName("View").setCommandableOutputProperty(Properties.ICON, "fa fa-car")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("viewIndexView")
									)
						,__inject__(MenuItemBuilder.class).setCommandableName("Navigation").setCommandableOutputProperty(Properties.ICON, "fa fa-external-link")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableName("Index").setCommandableNavigationIdentifier("navigationIndexView")
									)
						
						,__inject__(MenuItemBuilder.class).setCommandableName("Entités").setCommandableOutputProperty(Properties.ICON, "fa fa-database")
							.addChild(
								__inject__(MenuItemBuilder.class).setCommandableName("List").addEntitiesList(MyEntity.class,VerySimpleEntity.class,VeryComplexEntity.class,EntityNoForm.class)	
								,__inject__(MenuItemBuilder.class).setCommandableName("Selection pour traitement")
									.addEntitiesSelect(MyEntity.class,VerySimpleEntity.class)
									.addEntitySelect(EntityNoForm.class,"validate")
									.addEntitySelect(EntityNoForm.class,"transfer")
								)
						,__inject__(MenuItemBuilder.class).setCommandableNameInternalizationKeyValue(MyEntity.class).setCommandableOutputProperty(Properties.ICON, "fa fa-camera")
							.addChild(__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionList.class).setEntityClass(MyEntity.class))
									,__inject__(MenuItemBuilder.class).setCommandableNavigationIdentifierBuilderSystemAction(__inject__(SystemActionCreate.class).setEntityClass(MyEntity.class))
									)
							);
				//
				setOutput(__inject__(MenuBuilderMap.class).set(ScopeSession.class,menuBuilder));
			}
		});
	}
	
}