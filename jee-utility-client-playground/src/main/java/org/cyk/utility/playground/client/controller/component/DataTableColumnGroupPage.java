package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTableColumnGroupPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = DataTable.build(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,Namable.class);
		dataTable.addColumnsAfterRowIndex(Column.build(Column.FIELD_FIELD_NAME,"identifier",Column.FIELD_HEADER_TEXT,"Système",Column.FIELD_WIDTH,"350")
				,Column.build(Column.FIELD_FIELD_NAME,"code",Column.FIELD_HEADER_TEXT,"Métier",Column.FIELD_WIDTH,"250"),Column.build(Column.FIELD_FIELD_NAME,"name"));
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Column Group Page";
	}
}
