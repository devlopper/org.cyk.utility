package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MyEntityListPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		return super.__getWindowBuilder__().setTitleValue("Liste de catégorie de fonction");
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(createDataTableBuilder())
		
		;
		return viewBuilder;
	}
	
	public static GridBuilder createDataTableBuilder() {
		GridBuilder gridBuilder = __inject__(GridBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("Code").addFieldNameStrings("code")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Nom").addFieldNameStrings("name")
						)
				
				.addObjects(MyEntityData.COLLECTION.toArray())
				;
		
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getCommandables(Boolean.TRUE).add(
				__inject__(CommandableBuilder.class).setName("Modifier").setNavigationIdentifierAndParameters("myEntityEditWindow",null)
				,__inject__(CommandableBuilder.class).setName("Supprimer").setNavigationIdentifierAndParameters("myEntityEditWindow",new Object[] {"p1","v1"})
				);
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		return gridBuilder;
	}
	
}
