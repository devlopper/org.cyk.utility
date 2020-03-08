package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.identifier.resource.PathAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.QueryAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractDataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.Button;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.cyk.utility.playground.client.controller.api.NamableController;
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
				,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE,DataTable.FIELD_SELECTION_MODE,"multiple"));
		
		dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Edit",MenuItem.FIELD_ICON,"fa fa-pencil",MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {			
			@Override public void listenAction(Object argument) {
				UniformResourceIdentifierAsFunctionParameter p = new UniformResourceIdentifierAsFunctionParameter();
				p.setRequest(__getRequest__());
				p.setPath(new PathAsFunctionParameter());
				p.getPath().setIdentifier("namableEditView");
				p.setQuery(new QueryAsFunctionParameter());
				p.getQuery().setValue("entityidentifier="+((Namable)argument).getIdentifier());
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(UniformResourceIdentifierHelper.build(p));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE);
		
		dataTable.addRecordMenuItemByArguments(MenuItem.FIELD_VALUE,"Delete",MenuItem.FIELD_ICON,"fa fa-remove",MenuItem.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {			
			@Override public void listenAction(Object argument) {__inject__(NamableController.class).delete((Namable) argument);}
		},MenuItem.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE
						,MenuItem.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,CollectionHelper.listOf(RenderType.GROWL));
		
		dataTable.addColumnsAfterRowIndex(Builder.build(Column.class, Map.of(Column.FIELD_FIELD_NAME,"code",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE
				,Column.FIELD_WIDTH,"100"))
				,Builder.build(Column.class, Map.of(Column.FIELD_FIELD_NAME,"name",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE)));
				
		dataTable.addHeaderToolbarLeftCommands(
				Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 1. Min1",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
					@Override
					protected void __showDialog__(Object argument) {
						dataTable.getDialog().setHeader("Réalisation de l'opération 1");
						super.__showDialog__(argument);
					}
				}.setCollection(dataTable),CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.FALSE))
				,Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 2. Min2",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
					@Override
					protected void __showDialog__(Object argument) {
						dataTable.getDialog().setHeader("Réalisation de l'opération 2");
						super.__showDialog__(argument);
					}
				}.setCollection(dataTable).setMinimumSelectionSize(2),CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.FALSE))
				,Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Opération 3. No Constraint",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
						,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
					@Override
					protected void __showDialog__(Object argument) {
						dataTable.getDialog().setHeader("Réalisation de l'opération 3");
						super.__showDialog__(argument);
					}
				}.setCollection(dataTable).setMinimumSelectionSize(0).setIsSelectionShowable(Boolean.FALSE),CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.FALSE))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Button Without Params",Button.FIELD_OUTCOME,"namableEditView"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Button With Params",Button.FIELD_OUTCOME,"namableEditView",Button.FIELD_PARAMETERS,Map.of("p1","v1")))
			);
		
		dataTable.addRecordCommands(Builder.build(CommandButton.class,Map.of(CommandButton.FIELD_VALUE,"Op1"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Op2",Button.FIELD_OUTCOME,"namableEditView"))
				,Builder.build(Button.class,Map.of(Button.FIELD_VALUE,"Op3",Button.FIELD_OUTCOME,"namableEditView",Button.FIELD_PARAMETERS,Map.of("p1","v1")))
			);
		
		dataTable.getDialog().getCloseAjax().setListener(new AbstractAction.Listener.AbstractImpl() {			
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
