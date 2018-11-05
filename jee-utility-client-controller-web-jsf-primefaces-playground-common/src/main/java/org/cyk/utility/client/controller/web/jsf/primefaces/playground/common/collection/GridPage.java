package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors; 

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
		.addComponents(/*createDataTableBuilderNoHeadersAndFooters(),*/createDataTableBuilder())
		
		;
		return viewBuilder;
	}
	
	public static GridBuilder createDataTableBuilderNoHeadersAndFooters() {
		GridBuilder gridBuilder = __inject__(GridBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class)//.addFieldNameStrings("f1")
						,__inject__(ColumnBuilder.class)//.addFieldNameStrings("f2")
						,__inject__(ColumnBuilder.class)//.addFieldNameStrings("f3")
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
	
	public static GridBuilder createDataTableBuilder() {
		GridBuilder gridBuilder = __inject__(GridBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("String").addFieldNameStrings("string")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Number").setFooterTextValue("Total : ?").addFieldNameStrings("number")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Date").addFieldNameStrings("date")
						)
				
				.addObjects(new Data().setString("string 01").setNumber("12").setDate("01/02/2001")
						,new Data().setString("string 02").setNumber("1")
						,new Data().setString("string 03").setNumber("349")
						,new Data().setString("string 04").setDate("17/07/2018")
						,new Data().setString("another string").setNumber("my number").setDate("yesterday")
						)
				;
		/*
		gridBuilder.getCommandablesColumnCommandableMap(Boolean.TRUE).set(SystemActionUpdate.class,__inject__(CommandableBuilder.class).setName("Modifier")
				,SystemActionDelete.class,__inject__(CommandableBuilder.class).setName("Supprimer"));
		*/
		
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getCommandableByClassMap(Boolean.TRUE).set(SystemActionUpdate.class,__inject__(CommandableBuilder.class)
				.setName("Modifier"),SystemActionDelete.class,__inject__(CommandableBuilder.class).setName("Supprimer"));
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		return gridBuilder;
	}
	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Data extends AbstractObject {
		private static final long serialVersionUID = 1L;
		
		private String string;
		private String number;
		private String date;
		
	}
	
}
