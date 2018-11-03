package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter; 

@Named @RequestScoped @Getter @Setter
public class GridPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Grid";
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
				.addColumns(__inject__(ColumnBuilder.class)
						,__inject__(ColumnBuilder.class)
						,__inject__(ColumnBuilder.class)
						//,__inject__(ColumnBuilder.class)
						)
				.addRows(__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class).setTextValue("v00"),__inject__(CellBuilder.class).setTextValue("v10")
						,__inject__(CellBuilder.class).setTextValue("v20"))
						//,__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class),__inject__(CellBuilder.class),__inject__(CellBuilder.class))
						)
				.addRows(__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class).setTextValue("v01"),__inject__(CellBuilder.class).setTextValue("v11")
						,__inject__(CellBuilder.class).setTextValue("v21"))
						)
				.addRows(__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class).setTextValue("v02"),__inject__(CellBuilder.class).setTextValue("v12")
						,__inject__(CellBuilder.class).setTextValue("v22"))
						)
				;
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		return gridBuilder;
	}
	/*
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable01 = __inject__(DataTableBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class)
						,__inject__(ColumnBuilder.class)
						,__inject__(ColumnBuilder.class)
						//,__inject__(ColumnBuilder.class)
						)
				.addRows(__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class).setTextValue("v00"),__inject__(CellBuilder.class).setTextValue("v10")
						,__inject__(CellBuilder.class).setTextValue("v20"))
						//,__inject__(RowBuilder.class).addCells(__inject__(CellBuilder.class),__inject__(CellBuilder.class),__inject__(CellBuilder.class))
						)
				.execute().getOutput();
		
		
		dataTable02 = __inject__(DataTableBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("Code")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Name")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Description")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Date")
						)
				.addRows(__inject__(RowBuilder.class))
				.execute().getOutput();
		
		dataTable03 = __inject__(DataTableBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("Code")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Name")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Value").setFooterTextValue("Total")
						)
				.addRows(__inject__(RowBuilder.class)
						,__inject__(RowBuilder.class)
						,__inject__(RowBuilder.class))
				.execute().getOutput();
		
	}
	*/
	
}
