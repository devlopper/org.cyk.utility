package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;

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
				
				.addObjects(__inject__(MyEntityData.class).setCode("C01").setName("N01")
						,__inject__(MyEntityData.class).setCode("C02").setName("N02")
						)
				;
		
		ViewBuilder viewBuilder = gridBuilder.getCommandablesColumn(Boolean.TRUE).getBodyView(Boolean.TRUE);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).addComponents(__inject__(CommandableButtonBuilder.class).setName("Edit")
				,__inject__(CommandableButtonBuilder.class).setName("Delete")
				);
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		return gridBuilder;
	}
	
}
