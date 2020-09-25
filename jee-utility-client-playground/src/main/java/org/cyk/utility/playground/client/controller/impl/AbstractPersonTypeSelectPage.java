package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonTypeSelectPage extends AbstractPageContainerManagedImpl implements Serializable {

	protected DataTable dataTable;
	protected CommandButton saveCommandButton;
	protected Layout layout;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = PersonTypeListPage.buildDataTable(DataTable.FIELD_SELECTION_MODE,isMany() ? "multiple" : "single");	
		saveCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,"Sélectionner"
			,CommandButton.FIELD_ICON,"fa fa-floppy-o"
				,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION,CommandButton.FIELD_STYLE_CLASS,"cyk-float-right"
				,CommandButton.FIELD_LISTENER,new CommandButton.Listener.AbstractImpl() {
					@Override
					protected Object __runExecuteFunction__(AbstractAction action) {
						System.out.println(
								"AbstractPersonTypeSelectPage.__listenPostConstruct__().new AbstractImpl() {...}.__runExecuteFunction__()");
						System.out.println("ONE  : "+dataTable.getSelection());
						System.out.println("MANY : "+dataTable.getSelectionAsCollection());
						return null;
					}
				});
		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
				MapHelper.instantiate(Cell.FIELD_CONTROL,dataTable,Cell.FIELD_WIDTH,12)
				,MapHelper.instantiate(Cell.FIELD_CONTROL,saveCommandButton,Cell.FIELD_WIDTH,12)
			));
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Sélection de "+(isMany() ? "plusieurs" : "un");
	}
	
	protected abstract Boolean isMany();
}