package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentTargetModelBuilder;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.Cells;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.component.menu.MenuItems;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

public class ComponentTargetModelBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentTargetModelBuilder> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentTargetModelBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Component component = getFunction().getComponent();
				if(component instanceof OutputStringText) 
					setOutput(__buildHtmlOutputText__((OutputStringText) component));
				else if(component instanceof Menu) 
					setOutput(__buildMenu__((Menu) component));
				else if(component instanceof Grid)
					setOutput(__buildDataTable__((Grid) component));
				else if(component instanceof View)
					setOutput(__buildComponents__((View) component));
			}
		});
	}
	
	/* OutputStringText to outputText*/
	
	private HtmlOutputText __buildHtmlOutputText__(OutputStringText outputStringText) {
		HtmlOutputText htmlOutputText = new HtmlOutputText();
		htmlOutputText.setValue(outputStringText.getValue());
		return htmlOutputText;
	}
	
	/* Menu to MenuModel */
	
	private MenuModel __buildMenu__(Menu menu) {
		MenuItems menuItems = menu.getItems();
		MenuModel model = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(menuItems)) {
			model = new DefaultMenuModel();	
			__buildMenuAddItemChildren__(model, menuItems.get());
		}
		return model;
	}
	
	private void __buildMenuAddItemChildren__(Object parent,Collection<MenuItem> menuItems) {
		if(__inject__(CollectionHelper.class).isNotEmpty(menuItems)) {
			for(MenuItem index : menuItems) {
				Collection<MenuItem> children = __inject__(CollectionHelper.class).cast(MenuItem.class, index.getChildren());
		        if(__inject__(CollectionHelper.class).isEmpty(children)) {
		        	DefaultMenuItem item = new DefaultMenuItem(index.getName());
		        	Navigation navigation = index.getNavigation();
		        	if(navigation!=null) {
		        		String outcome = (String) navigation.getIdentifier();
		        		if(__inject__(StringHelper.class).isNotBlank(outcome))
		        			item.setOutcome(outcome);
		        	}
		        	
		        	__buildMenuAddMenuElement__(parent, item);	
		        }else {
		        	DefaultSubMenu subMenu = new DefaultSubMenu(index.getName());
		        	__buildMenuAddMenuElement__(parent, subMenu);
		        	__buildMenuAddItemChildren__(subMenu, children);
		        }
			}
		}
	}
	
	private void __buildMenuAddMenuElement__(Object parent,MenuElement element) {
		if(parent instanceof MenuModel)
	        ((MenuModel)parent).addElement(element);
	     else if(parent instanceof DefaultSubMenu)
	    	 ((DefaultSubMenu)parent).addElement(element);	
	}
	
	/* Grid to DataTable */
	
	private DataTable __buildDataTable__(Grid grid) {
		DataTable dataTable = new DataTable();
		dataTable.setVar("indexRow");
		Objects objects = grid.getObjects();
		if(__inject__(CollectionHelper.class).isEmpty(objects)) {
			Rows rows = grid.getRows();
			if(__inject__(CollectionHelper.class).isNotEmpty(rows)) {
				if(objects == null)
					objects = __inject__(Objects.class);
				for(Row indexRow : rows.get()) {
					Map<String,Object> map = new LinkedHashMap<>();
					objects.add(map);
					Cells cells = indexRow.getCells();
					if(__inject__(CollectionHelper.class).isNotEmpty(cells)) {
						for(Cell indexCell : cells.get()) {
							Object value = null;
							Components components = null;
							if(indexCell.getView()!=null)
								components = indexCell.getView().getComponents();
							if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
								for(Component indexCellComponent : components.get()) {
									if(indexCellComponent instanceof OutputString) {
										value = ((OutputString)indexCellComponent).getValue();
										break;
									}
								}
							}
							map.put((String)indexCell.getColumn().getValuePropertyName(), value);
						}
					}
				}
			}
		}
		
		if(__inject__(CollectionHelper.class).isNotEmpty(objects)) {
			dataTable.setValue(objects.get());
			ListDataModel<Object> dataModel = new ListDataModel<Object>();
			dataModel.setWrappedData(objects.get());
			dataTable.setValue(dataModel);
		}
		
		Columns columns = grid.getColumns();
		if(__inject__(CollectionHelper.class).isNotEmpty(columns))
			for(Column index : columns.get())
				__buildDataTableAddColumn__(dataTable, index);
		return dataTable;
	}
	
	private void __buildDataTableAddColumn__(DataTable dataTable,Column column) {
		org.primefaces.component.column.Column __column__ = new org.primefaces.component.column.Column();
		UIComponent uiComponent = __inject__(CollectionHelper.class).getFirst(__buildComponents__(column.getView(ViewMap.HEADER)));
		if(uiComponent!=null)
			__column__.setHeader(uiComponent);
		
		uiComponent = __inject__(CollectionHelper.class).getFirst(__buildComponents__(column.getView(ViewMap.BODY)));
		if(uiComponent==null) {
			String valuePropertyName = column.getValuePropertyName();
			if(__inject__(StringHelper.class).isNotBlank(valuePropertyName)) {
				HtmlOutputText htmlOutputText = new HtmlOutputText();
				ValueExpression valueExpression = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext()
						, "#{"+dataTable.getVar()+"['"+valuePropertyName+"']}"
						, String.class);
				htmlOutputText.setValueExpression("value",valueExpression);
				uiComponent = htmlOutputText;
			}
		}else {
			
		}
		if(uiComponent!=null)
			__column__.getChildren().add(uiComponent);
		
		uiComponent = __inject__(CollectionHelper.class).getFirst(__buildComponents__(column.getView(ViewMap.FOOTER)));
		if(uiComponent!=null)
			__column__.setFooter(uiComponent);
		
		dataTable.getColumns().add(__column__);
	}
	
	/* View to UIComponent */
	
	private Collection<UIComponent> __buildComponents__(View view) {
		Collection<UIComponent> uiComponents = null;
		if(view!=null) {
			Components components = view.getComponents();
			if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
				uiComponents = new ArrayList<>();
				for(Component index : components.get()) {
					UIComponent uiComponent = (UIComponent) __inject__(ComponentTargetModelBuilder.class).setComponent(index).execute().getOutput();
					if(uiComponent!=null)
						uiComponents.add(uiComponent);
				}
			}
		}
		return uiComponents;
	}
}