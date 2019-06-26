package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.AbstractRowDataImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors; 

@Named @ViewScoped @Getter @Setter
public class GridPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.setContext(__getRequest__()).setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
		
		ColumnBuilder inputTextColumn = __inject__(ColumnBuilder.class).setHeaderTextValue("InputText").addFieldNameStrings("data","inputText");
		InputStringLineOneBuilder inputStringLineOneBuilder = __inject__(InputStringLineOneBuilder.class);
		
		inputTextColumn.getViewMap(Boolean.TRUE).get(ViewMap.BODY,Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
		inputTextColumn.getViewMap(Boolean.TRUE).get(ViewMap.BODY,Boolean.TRUE).addComponentBuilder(inputStringLineOneBuilder);
		
		GridBuilder gridBuilder = __inject__(GridBuilder.class).setRowClass(Row.class).setRowDataClass(Data.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("String").addFieldNameStrings("data","string")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Number").setFooterTextValue("Total : ?").addFieldNameStrings("data","number")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Date").addFieldNameStrings("data","date")
						,inputTextColumn
						)
				.addObjects(new Data().setString("string 01").setNumber("12").setDate("01/02/2001").setInputText("i01")
						,new Data().setString("string 02").setNumber("1")
						,new Data().setString("string 03").setNumber("349")
						,new Data().setString("string 041").setDate("17/07/2018")
						,new Data().setString("another string").setNumber("my number").setDate("yesterday")
						)
			.setIsLazyLoadable(Boolean.FALSE)
			;
		
		gridBuilder.setContext(__getRequest__()).setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
						
		viewBuilder.addComponentBuilder(gridBuilder);
		
		return viewBuilder;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Grid";
	}
	
	@Override
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return null;
	}
	
	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return null;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Row extends AbstractRowDataImpl<Data> {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Row setData(Data data) {
			return (Row) super.setData(data);
		}
		
		@Override
		public Row setOrderNumber(Object orderNumber) {
			return (Row) super.setOrderNumber(orderNumber);
		}
	}

	@Getter @Setter @Accessors(chain=true)
	public static class Datas extends AbstractObject {
		private static final long serialVersionUID = 1L;
		
		@org.cyk.utility.client.controller.component.annotation.Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void add() {
			System.out.println("GridPage.Datas.add()");
		}
		
	}
}
