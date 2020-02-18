package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractDataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.Button;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTableLazyPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	private CommandButton commandButtonOperation1Mass;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Lazy Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = Builder.build(DataTable.class,Map.of(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.ConfiguratorImpl.FIELD_ENTIY_CLASS,Namable.class
				,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE
				,DataTable.FIELD_SELECTION_MODE,"multiple"));
		
		dataTable.addColumnsAfterRowIndex(Builder.build(Column.class, Map.of(Column.FIELD_HEADER,"Code",Column.FIELD_FIELD_NAME,"code",Column.FIELD_WIDTH,"100"))
				,Builder.build(Column.class, Map.of(Column.FIELD_HEADER,"Nom",Column.FIELD_FIELD_NAME,"name")));
				
		dataTable.addHeaderToolbarLeftCommands(
				Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 1. Min1",CommandButton.ConfiguratorImpl.FIELD_DATA_TABLE,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractCollection.AbstractActionListenerImpl(dataTable) {
					@Override
					protected void __showDialog__() {
						dataTable.getDialog().setHeader("Réalisation de l'opération 1");
						super.__showDialog__();
					}
				}))
				,Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 2. Min2",CommandButton.ConfiguratorImpl.FIELD_DATA_TABLE,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractCollection.AbstractActionListenerImpl(dataTable) {
					@Override
					protected void __showDialog__() {
						dataTable.getDialog().setHeader("Réalisation de l'opération 2");
						super.__showDialog__();
					}
				}.setMinimumSelectionSize(2)))
				,Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 3. No Constraint",CommandButton.ConfiguratorImpl.FIELD_DATA_TABLE,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractCollection.AbstractActionListenerImpl(dataTable) {
					@Override
					protected void __showDialog__() {
						dataTable.getDialog().setHeader("Réalisation de l'opération 3");
						super.__showDialog__();
					}
				}.setMinimumSelectionSize(0).setIsSelectionShowable(Boolean.FALSE)))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Button Without Params",Button.FIELD_OUTCOME,"namableEditView"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Button With Params",Button.FIELD_OUTCOME,"namableEditView",Button.FIELD_PARAMETERS,Map.of("p1","v1")))
			);
		
		dataTable.addRecordCommands(Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Op1"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Op2",Button.FIELD_OUTCOME,"namableEditView"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Op3",Button.FIELD_OUTCOME,"namableEditView",Button.FIELD_PARAMETERS,Map.of("p1","v1")))
			);
		
		dataTable.getDialog().getCloseAjax().setListener(new AbstractAction.Listener() {			
			@Override
			public void listenAction(Object argument) {
				
			}
		});
		dataTable.getDialog().getCloseAjax().setDisabled(Boolean.FALSE);
		
		dataTable.setListener(new AbstractDataTable.Listener() {
			
			@Override
			public String listenGetStyleClassByRecord(Object record,Integer recordIndex) {
				if(NumberHelper.compare(recordIndex, 3, ComparisonOperator.EQ))
					return "cyk-background-highlight";
				return null;
			}
			
			@Override
			public String listenGetStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex) {
				if(NumberHelper.compare(recordIndex, 7, ComparisonOperator.EQ) && NumberHelper.compare(columnIndex, 1, ComparisonOperator.EQ))
					return "cyk-background-highlight";
				return null;
			}
		});
	}
	
}
