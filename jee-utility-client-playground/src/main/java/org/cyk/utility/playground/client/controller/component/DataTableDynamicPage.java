package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.__static__.controller.DataGrid;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractDataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTableDynamicPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,DataGrid.Row.class,DataTable.FIELD_EDITABLE,Boolean.TRUE);
		dataTable.setListener(new AbstractDataTable.Listener.AbstractImpl() {
			@Override
			public Object listenGetCellValueByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				if(column != null)
					return column.getFieldName();
				return super.listenGetCellValueByRecordByColumn(record, recordIndex, column, columnIndex);
			}
			
			@Override
			public Object listenSave(AbstractCollection collection) {
				AbstractDataTable dataTable = (AbstractDataTable) collection;
				MessageRenderer.getInstance().render(dataTable.getDataGrid().toString());
				return null;
			}
		});
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Dynamic Page";
	}
}
