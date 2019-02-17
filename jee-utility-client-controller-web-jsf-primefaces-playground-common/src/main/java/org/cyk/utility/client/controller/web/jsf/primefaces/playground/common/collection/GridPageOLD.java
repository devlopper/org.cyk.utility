package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.WindowContainerManaged;
import org.cyk.utility.client.controller.data.AbstractRowDataImpl;
import org.cyk.utility.client.controller.data.RowBuilder;
import org.cyk.utility.client.controller.event.EventBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.throwable.ThrowableHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors; 

@Named @ViewScoped @Getter @Setter
public class GridPageOLD extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String gridIdentifier,addRowCommandableIdentifier,deleteRowComandableIdentifier;
	private GridBuilder gridBuilder;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Grid";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		gridBuilder = createDataTableBuilder(this);
		gridIdentifier = gridBuilder.getOutputProperties().getIdentifier().toString();
		viewBuilder.addComponentBuilder(gridBuilder);
		return viewBuilder;
	}
	
	public static GridBuilder createDataTableBuilder(WindowContainerManaged windowContainerManaged) {
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
		
		gridBuilder.getRows(Boolean.TRUE).setRowClass(Row.class);
		
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class).setName("Ajouter une ligne")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						//String gridIdentifier = ((GridPage)windowContainerManaged).getGridIdentifier();
						//Grid grid = ((GridPage)windowContainerManaged).getWindow().getView().getComponents().getIsInstanceOfOneByIdentifier(Grid.class, gridIdentifier);
						
						//Data data = new Data().setString("NEW").setNumber("0000").setDate("dd/MM/yyyy");
						Class<?> dataClass = gridBuilder.getRowDataClass();
						if(dataClass==null)
							__inject__(ThrowableHelper.class).throwRuntimeException("Data class is required.");
						Data data = (Data) __inject__(ClassHelper.class).instanciateOne(dataClass); 
						
						data.setString("NEW").setNumber("0000").setDate("dd/MM/yyyy");
						
						DataTable dataTable = (DataTable) gridBuilder.getComponent().getTargetModel();
						ListDataModel<Object> dataModel = (ListDataModel<Object>) dataTable.getValue();
						Collection<org.cyk.utility.client.controller.data.Row> collection = (Collection<org.cyk.utility.client.controller.data.Row>) dataModel.getWrappedData();
						collection.add(__inject__(RowBuilder.class).setGrid(gridBuilder).setData(data).execute().getOutput());
					}
				});
		commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		commandableBuilder.getCommand(Boolean.TRUE).setWindowContainerManaged(windowContainerManaged);
		//commandableBuilder.addUpdatables( gridBuilder.getOutputProperties().getIdentifier());
		commandableBuilder.addUpdatables("@(."+gridBuilder.getOutputProperties().getIdentifierAsStyleClass()+")");
		
		gridBuilder.getViewMap(Boolean.TRUE).get(ViewMap.HEADER,Boolean.TRUE).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
		gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandableBuilder);
		
		/**/
		
		commandableBuilder = __inject__(CommandableBuilder.class).setName("Générer une erreur")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						__inject__(ThrowableHelper.class).throwRuntimeException("Erreur générée");
					}
				});
		commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		commandableBuilder.getCommand(Boolean.TRUE).setWindowContainerManaged(windowContainerManaged);
		//commandableBuilder.addUpdatables( gridBuilder.getOutputProperties().getIdentifier());
		commandableBuilder.addUpdatables("@(."+gridBuilder.getOutputProperties().getIdentifierAsStyleClass()+")");
		
		gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandableBuilder);
		
		/**/
		
		commandableBuilder = __inject__(CommandableBuilder.class).setName("Ouvrir une fenêtre en popup afin d'ajouter une ligne")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						Map<String,Object> options = new HashMap<String, Object>();
				        options.put("modal", true);
				        options.put("width", 840);
				        options.put("height", 440);
				        options.put("contentWidth", "100%");
				        options.put("contentHeight", "100%");
				        PrimeFaces.current().dialog().openDynamic("dialog", options, null);
					}
				});
		commandableBuilder.getCommand(Boolean.TRUE).setWindowContainerManaged(windowContainerManaged);
		commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		EventBuilder event = __inject__(EventBuilder.class);
		event.setOutputProperties(__inject__(Properties.class));
		event.getOutputProperties().setEvent("dialogReturn");
		event.getOutputProperties().setDisabled("false");
		event.getOutputProperties().setAsync("true");
		event.getOutputProperties().setGlobal("true");
		event.getOutputProperties().setPartialSubmit("true");
		event.getOutputProperties().setResetValues("true");
		event.getOutputProperties().setIgnoreAutoUpdate("true");
		event.getOutputProperties().setImmediate("false");
		event.getOutputProperties().setSkipChildren("true");
		event.getOutputProperties().setUpdate("@(."+gridBuilder.getOutputProperties().getIdentifierAsStyleClass()+")");
		CommandFunction function = __inject__(CommandFunction.class);
		function.try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {
			@Override
			public void run() {
				SelectEvent selectEvent = (SelectEvent) function.getProperties().getParameter();
				Data data = (Data) selectEvent.getObject();
				
				DataTable dataTable = (DataTable) gridBuilder.getComponent().getTargetModel();
				ListDataModel<Object> dataModel = (ListDataModel<Object>) dataTable.getValue();
				Collection<org.cyk.utility.client.controller.data.Row> collection = (Collection<org.cyk.utility.client.controller.data.Row>) dataModel.getWrappedData();
				collection.add(__inject__(RowBuilder.class).setGrid(gridBuilder).setData(data).execute().getOutput());
			}
		});
		event.getOutputProperties().setFunction(function);
		function.setAction(__inject__(SystemActionCreate.class));
		commandableBuilder.addEvents(event);
		
		gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandableBuilder);
		
		/**/
		
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.FALSE);
		return gridBuilder;
	}
	
	public void call() {
		System.out.println("GridPage.call()");
	}
	
	public void l(SelectEvent event) {
		System.out.println("GridPage.l()");
	}
	
	/**/
	
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
