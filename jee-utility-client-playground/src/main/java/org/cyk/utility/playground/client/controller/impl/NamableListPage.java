package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NamableListPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		/*
		list.getDataTable().addColumnsAfterRowIndex(
			Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_CODE,Column.FIELD_WIDTH,"200",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
			,Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_NAME,Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
		);
		*/
		dataTable = DataTable.build(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,Namable.class,DataTable.FIELD_SELECTION_MODE,"multiple");
		
		dataTable.addColumnsAfterRowIndex(Column.build(Column.FIELD_FIELD_NAME,Namable.FIELD_CODE),Column.build(Column.FIELD_FIELD_NAME,Namable.FIELD_NAME));
		/*
		dataTable.addHeaderToolbarLeftCommands(
				CommandButton.build(CommandButton.ConfiguratorImpl.FIELD_ACTION,Action.UPDATE,CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
				,CommandButton.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.OPEN_VIEW_IN_DIALOG
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE)
		);
		*/
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate();
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogRead();
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogUpdate();
		
		//dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogDelete();
		
		/*
		dataTable.addRecordMenuItems(
				MenuItem.build(MenuItem.ConfiguratorImpl.FIELD_ACTION,Action.UPDATE,MenuItem.ConfiguratorImpl.FIELD_COLLECTION,dataTable
				,MenuItem.ConfiguratorImpl.FIELD_LISTENER_ACTION,AbstractAction.Listener.Action.OPEN_VIEW_IN_DIALOG
				,MenuItem.ConfiguratorImpl.FIELD_COLLECTION_SELECTION_SESSIONABLE,Boolean.TRUE)	
		);
		*/
		
		
		dataTable.addRecordMenuItemByArgumentsOpenViewInDialogRead();
		dataTable.addRecordMenuItemByArgumentsOpenViewInDialogUpdate();
		dataTable.addRecordMenuItemByArgumentsExecuteFunctionDelete();
		
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Namable list";
	}
}