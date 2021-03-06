package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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
import org.cyk.utility.client.controller.component.menu.MenuItem;
import org.cyk.utility.client.controller.component.menu.MenuItems;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.jsf.JavaServerFacesHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.builder.CommandButtonBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.outputpanel.OutputPanel;
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
				setOutput(__build__(component));
			}
		});
	}
	
	/**********************************************/
	private Object __build__(Component component) {
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
		
		if(component instanceof Grid) 
			return __build__((Grid) component);
		return null;
	}
	
	private UIComponent __build__(View view) {
		return view == null ? null : __build__(view.getComponents());
	}
	
	private UIComponent __build__(Components components) {
		OutputPanel outputPanel = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(components)) {
			outputPanel = new OutputPanel();
			outputPanel.setStyleClass(components.getLayout().getStyle().getClassesAsString());
			for(Component index : components.get())
				outputPanel.getChildren().add((UIComponent) __build__(index));
		}
		return outputPanel;
	}
	
	private UIComponent __build__(OutputStringText outputStringText) {
		HtmlOutputText htmlOutputText = new HtmlOutputText();
		htmlOutputText.setValue(outputStringText.getValue());
		return htmlOutputText;
	}
	
	private UIComponent __build__(Commandable commandable) {
		/*CommandButton commandButton = new CommandButton();
		//Button commandButton = new Button();
		commandButton.setValue(commandable.getName());
		String onClickValueExpressionString = null;
		if(commandable.getNavigation()!=null) {
			commandButton.setType("button");
			String url = null;*/
			/*if(__inject__(CollectionHelper.class).isEmpty(commandable.getNavigation().getDynamicParameterNames())) {
				if(commandable.getNavigation().getUniformResourceLocator()==null)
					url = null;
				else
					url = commandable.getNavigation().getUniformResourceLocator().toString();
			}else {*/
				/*SystemAction navigationSystemAction = commandable.getNavigation().getSystemAction();
				if(navigationSystemAction == null) {
					if(commandable.getCommand()!=null && commandable.getCommand().getFunction()!=null)
						navigationSystemAction = commandable.getCommand().getFunction().getAction();
				}
				
				String systemActionClass = null;
				if(navigationSystemAction instanceof SystemActionCreate) {
					url = commandable.getNavigation().getUniformResourceLocator().toString();
				}else {
					if(navigationSystemAction instanceof SystemActionCreate)
						systemActionClass = "systemActionCreateClass";
					else if(navigationSystemAction instanceof SystemActionRead)
						systemActionClass = "systemActionReadClass";
					else if(navigationSystemAction instanceof SystemActionUpdate)
						systemActionClass = "systemActionUpdateClass";
					else if(navigationSystemAction instanceof SystemActionDelete)
						systemActionClass = "systemActionDeleteClass";
					else if(navigationSystemAction instanceof SystemActionSelect)
						systemActionClass = "systemActionSelectClass";
					else if(navigationSystemAction instanceof SystemActionProcess)
						systemActionClass = "systemActionProcessClass";
					
					url = "#{indexRow.getUrlBySystemActionClass(componentHelper."+systemActionClass+")}";
				}
				
				onClickValueExpressionString = "window.open('"+url+"','_self');return false";
			//}
			
			if(__inject__(StringHelper.class).isNotBlank(url)) {
				onClickValueExpressionString = "window.open('"+url+"','_self');return false";
			}
			
		}else if(commandable.getCommand()!=null) {
			commandButton.setType("submit");
			String update = __inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentIdentifier()
					+","+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier()
					+","+__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentIdentifier();
			
			Objects updatables = commandable.getUpdatables();
			
			if(__inject__(CollectionHelper.class).isNotEmpty(updatables))
				for(Object index : updatables.get()) {
					String token = null;
					if(index instanceof VisibleComponent) {
						token = (String)((VisibleComponent)index).getProperties().getIdentifierAsStyleClass();
						if(__inject__(StringHelper.class).isNotBlank(token))
							update = update + " , @(."+token+")";		
					}else
						update = update + " , "+index;
					
				}
			String commandableIdentifier = commandable.getIdentifier().toString();
			if(commandable.getCommand().getWindowContainerManaged()!=null) {
				String actionExpressionLanguage = commandable.getCommand().getWindowContainerManaged().getContextDependencyInjectionBeanName()+".getCommandableByIdentifier('"+commandableIdentifier+"').command.function.executeToReturnVoid";
				commandButton.setActionExpression(__inject__(JavaServerFacesHelper.class).buildMethodExpression(actionExpressionLanguage, Void.class,new Class<?>[] {}));	
			}
			
			update = StringUtils.replace(update, "glo", ":form:glo");
			
			commandButton.setUpdate(update);
			
		}else {
			commandButton.setType("button");
		}
		
		if(__inject__(StringHelper.class).isNotBlank(onClickValueExpressionString)) {
			ValueExpression valueExpression = __buildValueExpressionString__(onClickValueExpressionString);
			__setValueExpression__(commandButton, "onclick", valueExpression);	
		}
		
		return commandButton;
		*/
		return __inject__(CommandButtonBuilder.class).build(commandable);
	}
	
	/* Menu to MenuModel */
	
	private MenuModel __build__(Menu menu) {
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
		        	DefaultMenuItem item = new DefaultMenuItem(index.getCommandable().getName());
		        	if(index.getCommandable().getNavigation() == null) {
		        		
		        	}else {
		        		URL url = index.getCommandable().getNavigation().getUniformResourceLocator();
		        		if(url == null) {
		        			//String outcome = (String) navigation.getIdentifier();
			        		//if(__inject__(StringHelper.class).isNotBlank(outcome))
			        		//	item.setOutcome(outcome);
		        		}
		        		
		        		if(url!=null)
		        			item.setUrl(url.toString());	
		        	}
		        	__buildMenuAddMenuElement__(parent, item);	
		        }else {
		        	DefaultSubMenu subMenu = new DefaultSubMenu(index.getCommandable().getName());
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
	
	private DataTable __build__(Grid grid) {
		DataTable dataTable = new DataTable();
		dataTable.setVar("indexRow");
		dataTable.setReflow(Boolean.TRUE);
		if(grid.getStyle()!=null)
			dataTable.setStyleClass(grid.getStyle().getClassesAsString());
		
		UIComponent uiComponent =__build__(grid.getView(ViewMap.HEADER));
		if(uiComponent!=null)
			dataTable.setHeader(uiComponent);
		
		uiComponent =__build__(grid.getView(ViewMap.FOOTER));
		if(uiComponent!=null)
			dataTable.setFooter(uiComponent);
		
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
		
		ListDataModel<Object> dataModel = new ListDataModel<Object>();
		dataTable.setValue(dataModel);
		Collection<Object> collection;
		
		if(__inject__(CollectionHelper.class).isNotEmpty(objects))
			collection = objects.get();
		else
			collection = new ArrayList<Object>();
		
		dataModel.setWrappedData(collection);
		
		Columns columns = grid.getColumns();
		if(__inject__(CollectionHelper.class).isNotEmpty(columns))
			for(Column index : columns.get())
				__buildDataTableAddColumn__(dataTable,grid, index);
		return dataTable;
	}
	
	private void __buildDataTableAddColumn__(DataTable dataTable,Grid grid,Column column) {
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