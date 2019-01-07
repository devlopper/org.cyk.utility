package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;

public abstract class AbstractWindowContainerManagedWindowBuilderListDataImpl extends AbstractWindowContainerManagedWindowBuilderListImpl implements WindowContainerManagedWindowBuilderListData,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(WindowBuilder window,SystemAction systemAction,Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		ViewBuilder viewBuilder = null;
		if(rowClass!=null) {
			Collection<?> objects = getGridObjects();
			if(objects == null)
				objects = __inject__(Controller.class).readMany(systemAction.getEntities().getElementClass());
			
			@SuppressWarnings({ "rawtypes" })
			GridBuilder gridBuilder = __inject__(GridBuilder.class).setRowClass(rowClass).setRowDataClass(systemAction.getEntities().getElementClass())
				.addObjects((Collection)objects)
				;
			
			Strings columnsFieldNames = getGridColumnsFieldNames();
			if(columnsFieldNames!=null)
				gridBuilder.addColumnsByFieldNames(columnsFieldNames.get());
			
			/* Create new instance */
			SystemAction systemActionCreate = __inject__(SystemActionCreate.class);
			
			/* Create new instance using normal window */
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getNavigation(Boolean.TRUE).setIdentifierBuilderSystemAction(systemActionCreate.setEntityClass(gridBuilder.getRowDataClass()));
			
			/* Create new instance using dialog window */
			/*
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).setWindowRenderTypeClass(WindowRenderTypeDialog.class);
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).setWindowContainerManaged(getWindowContainerManaged());
			gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemActionCreate);
			*/
			gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).addNavigationCommandablesBySystemActionClasses(SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class);
			
			LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
			gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
			layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
			
			viewBuilder = __inject__(ViewBuilder.class);
			viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
			.addComponents(gridBuilder)
			
			;
			
			__execute__(gridBuilder);
		}
		
		setView(viewBuilder);
	}
	
	protected abstract void __execute__(GridBuilder gridBuilder);
	
}
