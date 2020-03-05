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
public class DataTableLazyCustomPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTable dataTable;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DataTable Lazy Custom Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable = DataTable.build(DataTable.ConfiguratorImpl.FIELD_TITLE_VALUE,"List of namable",DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.ConfiguratorImpl.FIELD_ENTIY_CLASS,Namable.class
				,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE,DataTable.FIELD_SELECTION_MODE,"multiple");
		
		dataTable.addColumnsAfterRowIndex(
				Column.build(Column.FIELD_FIELD_NAME,"code",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE,Column.FIELD_WIDTH,"100")
				,Column.build(Column.FIELD_FIELD_NAME,"codeStyled",Column.FIELD_HEADER_TEXT,"code styled",Column.FIELD_WIDTH,"200",Column.FIELD_FOOTER_TEXT,"FOOTER")
				,Column.build(Column.FIELD_FIELD_NAME,"name",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE)
				);		
	}
	
}
