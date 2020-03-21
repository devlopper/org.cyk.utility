package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractDataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;
import org.cyk.utility.playground.client.controller.entities.Namable;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTableDynamicPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,PersonType.class,DataTable.FIELD_VALUE,/*__inject__(PersonTypeController.class).read()*/new ArrayList<PersonType>());
		dataTable.addColumnsAfterRowIndex(
				Column.build(Column.FIELD_FIELD_NAME,PersonType.FIELD_CODE,Column.FIELD_HEADER_TEXT,"Type code")
				,Column.build(Column.FIELD_FIELD_NAME,PersonType.FIELD_NAME,Column.FIELD_HEADER_TEXT,"Type name")
				//,Column.build(Column.FIELD_FIELD_NAME,"code",Column.FIELD_HEADER_TEXT,"Métier",Column.FIELD_WIDTH,"250"),Column.build(Column.FIELD_FIELD_NAME,"name")
				);
		dataTable.enableAjaxCellEdit().enableCommandButtonAddRow().enableCommandButtonAddColumn();
		
		dataTable.setListener(new AbstractDataTable.Listener.AbstractImpl() {
			@Override
			public Object listenGetCellValueByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				if(columnIndex != null && columnIndex > 1)
					return "VALUE";
				return super.listenGetCellValueByRecordByColumn(record, recordIndex, column, columnIndex);
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Dynamic Page";
	}
}
