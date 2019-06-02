package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.ListDataModel;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentTargetModelBuilder;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
import org.cyk.utility.client.controller.component.grid.cell.Cells;
import org.cyk.utility.client.controller.component.grid.column.Column;
import org.cyk.utility.client.controller.component.grid.column.Columns;
import org.cyk.utility.client.controller.component.grid.row.Row;
import org.cyk.utility.client.controller.component.grid.row.Rows;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.CommandButtonBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.MenuBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueHelper;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.model.menu.MenuModel;

public class ComponentTargetModelBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentTargetModelBuilder> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentTargetModelBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Component component = getFunction().getComponent();
				setOutput(__build__(component));
			}
		});
	}
	
	/**********************************************/
	public static Object __build__(Component component) {
		if(component instanceof View) 
			return __build__((View) component);
		
		if(component instanceof Components) 
			return __build__((Components) component);
		
		if(component instanceof OutputStringText) 
			return __build__((OutputStringText) component);
		
		if(component instanceof Commandable) 
			return __build__((Commandable) component);
		
		if(component instanceof Menu) 
			return __build__((Menu) component);
		
		if(component instanceof Tree) 
			return __build__((Tree) component);
		
		if(component instanceof Grid) 
			return __build__((Grid) component);
		return null;
	}
	
	public static UIComponent __build__(View view) {
		return view == null ? null : __build__(view.getComponents());
	}
	
	public static UIComponent __build__(Components components) {
		OutputPanel outputPanel = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
			outputPanel = new OutputPanel();
			outputPanel.setStyleClass(components.getLayout().getStyle().getClassesAsString());
			for(Component index : components.get())
				outputPanel.getChildren().add((UIComponent) __build__(index));
		}
		return outputPanel;
	}
	
	public static UIComponent __build__(OutputStringText outputStringText) {
		HtmlOutputText htmlOutputText = new HtmlOutputText();
		htmlOutputText.setValue(outputStringText.getValue());
		return htmlOutputText;
	}
	
	public static UIComponent __build__(Commandable commandable) {
		return __inject__(CommandButtonBuilder.class).setModel(commandable).execute().getOutput();
	}
	
	public static MenuModel __build__(Menu menu) {
		return __inject__(MenuBuilder.class).setModel(menu).execute().getOutput();
	}
	
	public static Object __build__(Tree tree) {
		return __inject__(OrganigramNodeBuilder.class).setHierarchyNode(tree.getRoot().getHierarchyNode()).execute().getOutput();
	}
	
	/* Grid to DataTable */
	
	public static DataTable __build__(Grid grid) {
		DataTable dataTable = new DataTable();
		dataTable.setVar("indexRow");
		dataTable.setReflow(Boolean.TRUE);
		dataTable.setWidgetVar((String) grid.getProperties().getWidgetVar());
		if(grid.getStyle()!=null)
			dataTable.setStyleClass(grid.getStyle().getClassesAsString());
		
		UIComponent uiComponent =__build__(grid.getView(ViewMap.HEADER));
		if(uiComponent!=null) {
			InputText inputText = new InputText();
			inputText.setId("globalFilter");
			inputText.setOnchange("PF('"+grid.getProperties().getWidgetVar()+"').filter();");
			inputText.setPlaceholder("Rechercher...");
			//((OutputPanel)uiComponent).getChildren().add(inputText);
			dataTable.setHeader(uiComponent);
		}
		uiComponent =__build__(grid.getView(ViewMap.FOOTER));
		if(uiComponent!=null)
			dataTable.setFooter(uiComponent);
		
		Object dataModel = null;
		Boolean isLazyLoadable = __inject__(ValueHelper.class).defaultToIfNull(grid.getIsLazyLoadable(),Boolean.TRUE);
		dataTable.setLazy(isLazyLoadable);
		if(Boolean.TRUE.equals(isLazyLoadable)) {
			dataModel = new LazyDataModel<Object>(grid);
			dataTable.setPaginator(Boolean.TRUE);
			dataTable.setPaginatorAlwaysVisible(Boolean.FALSE);
			dataTable.setPaginatorPosition("top");
			dataTable.setRows(5);
			//dataTable.setPaginatorTemplate("{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {CurrentPageReport} {NextPageLink} {LastPageLink}");
			dataTable.setRowsPerPageTemplate("5,10,15");
		}else {
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
			dataModel = new ListDataModel<Object>();
			((javax.faces.model.DataModel<?>)dataModel).setWrappedData(Boolean.TRUE.equals(__inject__(CollectionHelper.class).isEmpty(objects)) ? new ArrayList<Object>() : objects.get());
		}		
		dataTable.setValue(dataModel);
		
		Columns columns = grid.getColumns();
		if(__inject__(CollectionHelper.class).isNotEmpty(columns))
			for(Column index : columns.get())
				__buildDataTableAddColumn__(dataTable,grid, index);
		return dataTable;
	}
	
	public static void __buildDataTableAddColumn__(DataTable dataTable,Grid grid,Column column) {
		org.primefaces.component.column.Column __column__ = new org.primefaces.component.column.Column();
		UIComponent uiComponent =__build__(column.getView(ViewMap.HEADER));
		if(uiComponent!=null)
			__column__.setHeader(uiComponent);
		
		uiComponent = __build__(column.getView(ViewMap.BODY));
		if(uiComponent==null) {
			String valuePropertyName = __inject__(CollectionHelper.class).isEmpty(grid.getObjects()) ? column.getValuePropertyName() : column.getFieldName();
			if(__inject__(StringHelper.class).isNotBlank(valuePropertyName)) {
				HtmlOutputText htmlOutputText = new HtmlOutputText();
				//__setValueExpression__(htmlOutputText, "value", __buildValueExpressionString__(__formatExpression__(dataTable.getVar()+"['"+valuePropertyName+"']")));
				__setValueExpression__(htmlOutputText, "value", __buildValueExpressionString__(__formatExpression__(dataTable.getVar()+"."+valuePropertyName)));
				uiComponent = htmlOutputText;
			}else {
				
			}
		}else {
			
		}
		if(uiComponent!=null)
			__column__.getChildren().add(uiComponent);
		
		uiComponent = __build__(column.getView(ViewMap.FOOTER));
		if(uiComponent!=null)
			__column__.setFooter(uiComponent);
		
		dataTable.getColumns().add(__column__);
	}
	
	/* View to UIComponent */
	
	/*private Collection<UIComponent> __buildComponents__(View view) {
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
	}*/
	
	/**/
	
	/*private void __setAttributes__(UIComponent uiComponent,Component component) {
		
	}*/
	
	/**/
	
	private static JavaServerFacesHelper __injectJavaServerFacesHelper__() {
		return __inject__(JavaServerFacesHelper.class);
	}
	
	private static String __formatExpression__(String expression) {
		return __injectJavaServerFacesHelper__().formatExpression(expression);
	}
	
	private static ValueExpression __buildValueExpression__(String expression,Class<?> returnType) {
		return __injectJavaServerFacesHelper__().buildValueExpression(expression, returnType);
	}
	
	private static ValueExpression __buildValueExpressionString__(String expression) {
		return __buildValueExpression__(expression, String.class);
	}
	
	private static void __setValueExpression__(UIComponent uiComponent,String propertyName,ValueExpression valueExpression) {
		__injectJavaServerFacesHelper__().setValueExpression(uiComponent, propertyName, valueExpression);
	}
}