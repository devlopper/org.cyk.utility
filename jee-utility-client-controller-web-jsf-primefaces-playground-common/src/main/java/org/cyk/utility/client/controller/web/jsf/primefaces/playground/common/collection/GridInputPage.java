package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputDateTime;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.data.AbstractRowDataImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.primefaces.PrimeFaces;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors; 

@Named @ViewScoped @Getter @Setter
public class GridInputPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private DataTable dataTable;
	private List<Data> datas = new ArrayList<Data>();
	
	@Override
	protected void __listenPostConstruct__() {
		// TODO Auto-generated method stub
		super.__listenPostConstruct__();
		dataTable = new DataTable();
		Column column = new Column();
		column.setHeaderText("MyCol");
		InputText inputText = new InputText();
		column.getChildren().add(inputText);
		dataTable.getColumns().add(column);
		DataModel<Data> dataModel = new ListDataModel<Data>();
		dataModel.setWrappedData(new ArrayList<Data>());
		dataTable.setValue(dataModel);
	}
	
	public void addData() {
		DataModel<Data> dataModel = (DataModel<Data>) dataTable.getValue();
		((Collection<Data>)dataModel.getWrappedData()).add(__inject__(Data.class));
		datas.add(__inject__(Data.class));
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.setContext(__getRequest__()).setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
		
		ColumnBuilder inputTextColumn = __inject__(ColumnBuilder.class).setHeaderTextValue("InputText").addFieldNameStrings("data","string");
		InputStringLineOneBuilder inputStringLineOneBuilder = __inject__(InputStringLineOneBuilder.class);
		inputStringLineOneBuilder.setMessage(null);
		
		inputTextColumn.getViewMap(Boolean.TRUE).get(ViewMap.BODY,Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE);
		inputTextColumn.getViewMap(Boolean.TRUE).get(ViewMap.BODY,Boolean.TRUE).addComponentBuilder(inputStringLineOneBuilder);
		
		GridBuilder gridBuilder = __inject__(GridBuilder.class).setRowClass(Row.class).setRowDataClass(Data.class)
				.addColumns(inputTextColumn)
			.setIsLazyLoadable(Boolean.FALSE)
			;
		
		gridBuilder.setContext(__getRequest__()).setUniformResourceLocatorMap(__getUniformResourceLocatorMap__());
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.TRUE);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		
		
		viewBuilder.addComponentBuilder(gridBuilder);
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setName("Add new row")
				.setCommandFunctionActionClass(SystemActionAdd.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						Data data = __inject__(Data.class);
						data.setString(__inject__(RandomHelper.class).getAlphabetic(5));
						Object row = DependencyInjection.inject(org.cyk.utility.client.controller.data.RowBuilder.class).setGrid(gridBuilder)
								.setDataClass((Class<? extends Data>) DependencyInjection.inject(ClassHelper.class).getByName(Data.class.getName()))
								.setData(data)
								.execute().getOutput();			
						gridBuilder.getComponent().getObjects(Boolean.TRUE).add(row);	
						
						DataTable dataTable = (DataTable) gridBuilder.getComponent().getTargetBinding().getValue();
						ListDataModel<Object> dataModel = (ListDataModel<Object>) dataTable.getValue();
						((Collection<Object>)dataModel.getWrappedData()).add(row);
					}
				});
		
		commandable.getCommand().getFunction().setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		commandable.getUpdatables(Boolean.TRUE).add(gridBuilder);
		
		//commandable.getUpdatables(Boolean.TRUE).add("@form");
		viewBuilder.addComponentBuilder(commandable);
		
		viewBuilder.addComponentBuilder(__inject__(CommandableBuilder.class).setName("Submit")
				.setCommandFunctionActionClass(SystemActionAdd.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						DataTable dataTable = (DataTable) gridBuilder.getComponent().getTargetBinding().getValue();
						ListDataModel<Object> dataModel = (ListDataModel<Object>) dataTable.getValue();
						for(Object index : (Collection<Object>)dataModel.getWrappedData()) {
							System.out.println("GridInputPage.__getViewBuilder__() ::: "+((Row)index).getData());
						}
					}
				}));
		
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		//viewBuilder.addInputBuilderByObjectByFieldNames(form.getData(),Boolean.TRUE, "string");
		
		return viewBuilder;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Grid Input Page";
	}
	
	@Override
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return null;
	}
	
	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return null;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl {
		private static final long serialVersionUID = 1L;
		
		@Input @InputString @InputStringLineOne @NotNull
		private String string;
		
		@Input @InputString @InputStringLineOne @NotNull
		private String number;
		
		@Input @InputDateTime @NotNull
		private LocalDateTime date;
		
		@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void submit() {
			PrimeFaces.current().dialog().closeDynamic(this);
		}
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
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
