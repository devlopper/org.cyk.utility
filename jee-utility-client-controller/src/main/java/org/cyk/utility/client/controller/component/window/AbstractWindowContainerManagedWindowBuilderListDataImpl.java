package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;

public abstract class AbstractWindowContainerManagedWindowBuilderListDataImpl extends AbstractWindowContainerManagedWindowBuilderListImpl implements WindowContainerManagedWindowBuilderListData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeSystemActionIsNotNull__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		ViewBuilder viewBuilder = null;
		if(rowClass!=null) {
			Collection<?> objects = getGridObjects();
			if(objects == null) {
				//Lazy load data
			}
			
			@SuppressWarnings({ "rawtypes" })
			GridBuilder gridBuilder = __inject__(GridBuilder.class).setRowClass(rowClass).setRowDataClass((Class<? extends Data>) systemAction.getEntities().getElementClass())
				.addObjects((Collection)objects)
				.setIsLazyLoadable(Boolean.TRUE)
				;
			
			gridBuilder.setContext(getContext()).setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			
			Strings columnsFieldNames = getGridColumnsFieldNames();
			if(columnsFieldNames!=null)
				gridBuilder.addColumnsByFieldNames(columnsFieldNames.get());
			
			gridBuilder.getViewMap(Boolean.TRUE).set(ViewMap.HEADER,__inject__(ViewBuilder.class));
			
			/* Create new instance */
			CommandableBuilder commandableBuilder = null;
			commandableBuilder = __inject__(CommandableBuilder.class).setUniformResourceIdentifierSystemAction(__inject__(SystemActionCreate.class).setEntityClass(gridBuilder.getRowDataClass()));
			commandableBuilder.setDerivableFieldNames(CommandableBuilder.PROPERTY_NAME,Boolean.FALSE).addRoles(ComponentRole.COLLECTION_PROCESSOR,ComponentRole.CREATOR);
			gridBuilder.addComponentBuildersToViewHeader(commandableBuilder);
			
			/* Read current instance */
			commandableBuilder = __inject__(CommandableBuilder.class).setUniformResourceIdentifierSystemAction(__inject__(SystemActionRead.class).setEntityClass(gridBuilder.getRowDataClass()));
			commandableBuilder.setDerivableFieldNames(CommandableBuilder.PROPERTY_NAME,Boolean.FALSE).addRoles(ComponentRole.COLLECTION_ITEM_PROCESSOR,ComponentRole.READER);
			gridBuilder.addCommandablesToColumnBodyView(commandableBuilder);
			
			/* Update current instance */
			commandableBuilder = __inject__(CommandableBuilder.class).setUniformResourceIdentifierSystemAction(__inject__(SystemActionUpdate.class).setEntityClass(gridBuilder.getRowDataClass()));
			commandableBuilder.setDerivableFieldNames(CommandableBuilder.PROPERTY_NAME,Boolean.FALSE).addRoles(ComponentRole.COLLECTION_ITEM_PROCESSOR,ComponentRole.MODIFIER);
			gridBuilder.addCommandablesToColumnBodyView(commandableBuilder);
			
			/* Remove current instance */
			commandableBuilder = __inject__(CommandableBuilder.class).setUniformResourceIdentifierSystemAction(__inject__(SystemActionDelete.class).setEntityClass(gridBuilder.getRowDataClass()));
			commandableBuilder.setDerivableFieldNames(CommandableBuilder.PROPERTY_NAME,Boolean.FALSE).addRoles(ComponentRole.COLLECTION_ITEM_PROCESSOR,ComponentRole.REMOVER);
			gridBuilder.addCommandablesToColumnBodyView(commandableBuilder);
			
			/* Create new instance using normal window */
			/*
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getNavigation(Boolean.TRUE).setIdentifierBuilderSystemAction(systemActionCreate.setEntityClass(gridBuilder.getRowDataClass()));
			
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getNavigation(Boolean.TRUE).getProperties().setContext(getContext());
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getNavigation(Boolean.TRUE).getProperties().setMap(getNavigationIdentifierStringMap());
			*/
			/* Create new instance using dialog window */
			/*
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).setWindowRenderTypeClass(WindowRenderTypeDialog.class);
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).setWindowContainerManaged(getWindowContainerManaged());
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemActionCreate);
			*/
			//gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).addNavigationCommandablesBySystemActionClasses(SystemActionRead.class/*,SystemActionUpdate.class,SystemActionDelete.class*/);
			
			LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
			gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
			layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
			
			viewBuilder = __inject__(ViewBuilder.class);
			viewBuilder.setContext(getContext()).setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
			viewBuilder.addComponentBuilder(gridBuilder);
			
			__execute__(gridBuilder);
		}
		
		setView(viewBuilder);
	}
	
	protected abstract void __execute__(GridBuilder gridBuilder);
	
}
