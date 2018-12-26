package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.collection;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.cell.CellBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.component.grid.row.RowBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutTypeGrid;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors; 

@Named @ViewScoped @Getter @Setter
public class GridPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Grid";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		/*viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(createDataTableBuilder())
		
		;
		
		viewBuilder.addComponentBuilderByObjectByMethodName(new Datas(), "add",__inject__(SystemActionCreate.class));
		*/
		viewBuilder = createViewBuilder();
		
		return viewBuilder;
	}
	
	public static ViewBuilder createViewBuilder() {
		Model model = new Model();
		model.set__title__("Titre");
		model.setLastNames("Yao Christian");
		model.getSubModel().set__title__("Sous-titre");
		model.getSubModel().setPhone1("11223344");
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder.addComponentBuilderByObjectByFieldNames(model, "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		/*
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "firstName");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "lastNames");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "otherDetails");
		
		ViewBuilder subViewBuilder = __inject__(ViewBuilder.class);
		subViewBuilder.setType(__inject__(ViewTypeForm.class));
		visibleComponentBuilder = (VisibleComponentBuilder<?>) subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "phone1");
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "phone2");
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "otherDetails");
		viewBuilder.addComponentBuilder(subViewBuilder);
		*/
		viewBuilder.addComponentBuilderByObjectByMethodName(model, "submit");
		/*
		viewBuilder.addComponentBuilderByObjectByMethodName(model, "close");
		*/
		
		viewBuilder.addComponentBuilder(createDataTableBuilder(model));
		
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
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE)
		.setIsHasCommandablesColumn(Boolean.TRUE)
		;
		return gridBuilder;
	}
	
	public static GridBuilder createDataTableBuilder(Model model) {
		GridBuilder gridBuilder = __inject__(GridBuilder.class)
				.addColumns(__inject__(ColumnBuilder.class).setHeaderTextValue("String").addFieldNameStrings("string")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Number").setFooterTextValue("Total : ?").addFieldNameStrings("number")
						,__inject__(ColumnBuilder.class).setHeaderTextValue("Date").addFieldNameStrings("date")
						)
				
				.addObjects(new Data().setString("string 01").setNumber("12").setDate("01/02/2001")
						,new Data().setString("string 02").setNumber("1")
						,new Data().setString("string 03").setNumber("349")
						,new Data().setString("string 041").setDate("17/07/2018")
						,new Data().setString("another string").setNumber("my number").setDate("yesterday")
						)
				//.setRowDataClass(Data.class)
				;
		
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class).setName("Command01")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						System.out.println("GridPage.createDataTableBuilder().new Runnable() {...}.run() : Command 01");
					}
				});
		commandableBuilder.getCommand(Boolean.TRUE).setWindowContainerVariableName("gridPage");
		
		gridBuilder.getViewMap(Boolean.TRUE).get(ViewMap.HEADER,Boolean.TRUE).setComponentsBuilder(__inject__(ComponentsBuilder.class).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE));
		gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class).setName("Command02")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {
						System.out.println("GridPage.createDataTableBuilder().new Runnable() {...}.run() : Command 02");
					}
				});
		commandableBuilder.getCommand(Boolean.TRUE).setWindowContainerVariableName("gridPage");
		gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandableBuilder);
		
		
		//gridBuilder.getView(ViewMap.HEADER).getComponentsBuilder().addComponents(commandable);
		
		//commandableBuilder = (CommandableBuilder) gridBuilder.getView(ViewMap.HEADER).addComponentBuilderByObjectByMethodName(model, "submit",__inject__(SystemActionRead.class));
		
		
		/*
		gridBuilder.getCommandablesColumnBodyView(Boolean.TRUE).getCommandables(Boolean.TRUE).add(__inject__(CommandableBuilder.class)
				.setName("Modifier"),__inject__(CommandableBuilder.class).setName("Supprimer"));
		*/
		LayoutTypeGrid layoutTypeGrid = __inject__(LayoutTypeGrid.class);
		gridBuilder.getView(Boolean.TRUE).getComponentsBuilder(Boolean.TRUE).getLayout(Boolean.TRUE).setType(layoutTypeGrid);
		layoutTypeGrid.setIsHasHeader(Boolean.TRUE).setIsHasFooter(Boolean.TRUE).setIsHasOrderNumberColumn(Boolean.TRUE).setIsHasCommandablesColumn(Boolean.FALSE);
		return gridBuilder;
	}
	
	/*public Commandable getCommandableByIdentifier(String identifier) {
		Commandable commandable = getWindow().getView().getComponents().getCommandableByIdentifier(identifier, Boolean.TRUE);
		if(commandable == null)
			__inject__(MessageRender.class).addMessages("").setType(__inject__(MessageRenderTypeDialog.class)).execute(); 
			//throwRuntimeException("Commandable with identifier <<"+identifier+">> not found.");
		return commandable;
	}*/
	
	public void call() {
		System.out.println("GridPage.call()");
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Data extends AbstractObject {
		private static final long serialVersionUID = 1L;
		
		private String string;
		private String number;
		private String date;
		
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
	public static class Model {
		
		@Output @OutputString @OutputStringText
		private String __title__;
		
		@Input @InputString @InputStringLineOne
		@NotNull
		private String firstName;
		
		@Input @InputString @InputStringLineOne
		private String lastNames;
		
		@Input @InputString @InputStringLineMany
		@NotNull
		private String otherDetails;
		
		private SubModel subModel = new SubModel();
		
		@org.cyk.utility.client.controller.component.annotation.Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void submit() {
			System.out.println("ViewPage.Model.submit() : "+this);
		}
		
		@org.cyk.utility.client.controller.component.annotation.Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void close() {
			System.out.println("ViewPage.Model.close() : "+this);
		}
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class SubModel {
		
		@Output @OutputString @OutputStringText
		private String __title__;
		
		@Input @InputString @InputStringLineOne
		@NotNull
		private String phone1;
		
		@Input @InputString @InputStringLineOne
		private String phone2;
		
		@Input @InputString @InputStringLineMany
		@NotNull
		private String otherDetails;
		
	}
}
