package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
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
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.ColumnBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.CommandButtonBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.DataTableBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.MenuBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.component.OrganigramNodeBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;
import org.cyk.utility.value.ValueHelper;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.model.menu.MenuModel;

@Deprecated
public class ComponentTargetModelBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentTargetModelBuilder> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentTargetModelBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Component component = getFunction().getComponent();
				DurationBuilder durationBuilder = __inject__(DurationBuilder.class);
				Object target = __build__(component);
				__logInfo__("Component : <<"+target.getClass().getSimpleName()+">>. Duration : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder).execute().getOutput());
				setOutput(target);				
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
		
		if(component instanceof InputStringLineOne) 
			return __build__((InputStringLineOne) component);
		return null;
	}
	
	public static UIComponent __build__(InputStringLineOne inputStringLineOne) {
		return new HtmlInputText();
	}
	
	public static UIComponent __build__(View view) {
		return view == null ? null : __build__(view.getComponents());
	}
	
	public static UIComponent __build__(Components components) {
		OutputPanel outputPanel = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
			outputPanel = new OutputPanel();
			outputPanel.setStyleClass(components.getLayout().getStyle().getClassesAsString());
			for(Component index : components.get()) {
				UIComponent uiComponent = (UIComponent) __build__(index);
				if(uiComponent != null)
					outputPanel.getChildren().add(uiComponent);
			}
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
		return __inject__(DataTableBuilder.class).setModel(grid).execute().getOutput();
		/*
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
			for(Column index : columns.get()) {
				org.primefaces.component.column.Column __column__ = __inject__(ColumnBuilder.class).setDataTable(dataTable).setGrid(grid).setModel(index).execute().getOutput();
				dataTable.getColumns().add(__column__);
			}
		return dataTable;
		*/
	}

}