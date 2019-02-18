package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.AbstractRowDataImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors; 

@Named @ViewScoped @Getter @Setter
public class GridPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		GridBuilder gridBuilder = __inject__(GridBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("String").addFieldNameStrings("data","string")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Number").setFooterTextValue("Total : ?").addFieldNameStrings("data","number")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Date").addFieldNameStrings("data","date")
						)
				
				.addObjects(new Data().setString("string 01").setNumber("12").setDate("01/02/2001")
						,new Data().setString("string 02").setNumber("1")
						,new Data().setString("string 03").setNumber("349")
						,new Data().setString("string 041").setDate("17/07/2018")
						,new Data().setString("another string").setNumber("my number").setDate("yesterday")
						)
				.setRowDataClass(Data.class)
				;

		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		
		gridBuilder.getRows(Boolean.TRUE).setRowClass(Row.class);
		
		gridBuilder.getViewMap(Boolean.TRUE).set(ViewMap.HEADER,__inject__(ViewBuilder.class));
		
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setName("Créer");
		commandable.addRoles(ComponentRole.CREATOR);
		commandable.setNavigationIdentifier("gridCreateView");
		commandable.getNavigation().getProperties().setContext(__getContext__());
		commandable.getNavigation().getProperties().setMap(__getUniformResourceLocatorMap__());
		gridBuilder.getViewMap(Boolean.TRUE).get(ViewMap.HEADER).getComponentsBuilder(Boolean.TRUE).addComponents(commandable);
		
		commandable = __inject__(CommandableBuilder.class).setName("Modifier");
		commandable.addRoles(ComponentRole.MODIFIER);
		commandable.setNavigationIdentifier("gridEditView");
		commandable.getNavigation().setSystemAction(__inject__(SystemActionUpdate.class).setEntityClass(Data.class));
		commandable.getNavigation().getProperties().setContext(__getContext__());
		commandable.getNavigation().getProperties().setMap(__getUniformResourceLocatorMap__());
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).addComponents(commandable);
		
		commandable = __inject__(CommandableBuilder.class).setName("Supprimer");
		commandable.addRoles(ComponentRole.MODIFIER);
		commandable.setNavigationIdentifier("gridEditView");
		commandable.getNavigation().setSystemAction(__inject__(SystemActionDelete.class));
		commandable.getNavigation().getProperties().setContext(__getContext__());
		commandable.getNavigation().getProperties().setMap( ((ConfigurableNavigationHandler)((FacesContext)__getContext__()).getApplication().getNavigationHandler()).getNavigationCases() );
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).addComponents(commandable);
		
		//gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).addNavigationCommandablesBySystemActionClasses(SystemActionRead.class/*,SystemActionUpdate.class,SystemActionDelete.class*/);
		
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
