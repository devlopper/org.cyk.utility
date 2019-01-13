package org.cyk.utility.client.controller.impl.verysimpleentity;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.AbstractWindowContainerManagedWindowBuilderEditDataImpl;
import org.cyk.utility.client.controller.component.window.WindowRenderTypeDialog;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetails;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityDetailsReadRow;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntityEditWindowBuilder;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;

public class VerySimpleEntityEditWindowBuilderImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements VerySimpleEntityEditWindowBuilder, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(Form form,SystemAction systemAction,Data data,ViewBuilder viewBuilder) {
		if(((VerySimpleEntity)data).getDetails() == null)
			((VerySimpleEntity)data).setDetails(__inject__(VerySimpleEntityDetails.class));
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_CODE);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_NAME);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_DESCRIPTION);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_DESCRIPTION_02);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATIONS);
		/*viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_DETAILS,VerySimpleEntityDetails.PROPERTY_ADDRESS);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_BOOLEAN_VALUE_CHECK_BOX);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_BOOLEAN_VALUE_BUTTON);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATION);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATION_RADIO);
		viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, VerySimpleEntity.PROPERTY_ENUMERATIONS);
		*/
		
		/**/
		
		GridBuilder gridBuilder = __inject__(GridBuilder.class).setRowClass(VerySimpleEntityDetailsReadRow.class).setRowDataClass(VerySimpleEntityDetails.class);
		gridBuilder.addColumns(
				__inject__(ColumnBuilder.class).addFieldNameStrings("data",VerySimpleEntityDetails.PROPERTY_CODE)
				,__inject__(ColumnBuilder.class).addFieldNameStrings("data",VerySimpleEntityDetails.PROPERTY_ADDRESS)
				);
		
		/* Create new instance */
		SystemAction systemActionCreate = __inject__(SystemActionCreate.class);
		
		/* Create new instance using dialog window */
		gridBuilder.getCreateRowCommandable(Boolean.TRUE).setWindowRenderTypeClass(WindowRenderTypeDialog.class);
		gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).setWindowContainerManaged(getWindowContainerManaged());
		gridBuilder.getCreateRowCommandable(Boolean.TRUE).getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemActionCreate);
		
		SystemAction systemActionProcess = __inject__(SystemActionCreate.class);
		
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Ouvrir");
		commandableBuilder.setWindowRenderTypeClass(WindowRenderTypeDialog.class);
		commandableBuilder.getCommand(Boolean.TRUE).setContainerContextDependencyInjectionBeanName("indexRow");
		commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemActionProcess);
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getCommandables(Boolean.TRUE).add(commandableBuilder);
		
		//gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).addNavigationCommandablesBySystemActionClasses(SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class);
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		
		viewBuilder.addComponentBuilder(gridBuilder);
	}

}
