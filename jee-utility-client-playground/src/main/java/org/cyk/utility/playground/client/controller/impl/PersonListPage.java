package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.Person;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonListPage extends AbstractEntityListPageContainerManagedImpl<Person> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<String> __getColumnsFieldsNames__(Class<Person> entityClass) {
		return List.of(Person.FIELD_CODE,Person.FIELD_FIRST_NAME,Person.FIELD_LAST_NAMES,Person.FIELD_TYPE+".name");
	}
	
	protected DataTable __buildDataTable__(Class<Person> entityClass) {
		DataTable dataTable = DataTable.build(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,entityClass,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.FALSE);
		Collection<Column> columns = __getColumns__(entityClass);
		if(CollectionHelper.isEmpty(columns))
			return dataTable;
		dataTable.addColumnsAfterRowIndex(columns);		
		__addDataTableHeaderToolbarLeftCommandsByArguments__(dataTable);				
		__addDataTableRecordMenuItemByArguments__(dataTable);
		return dataTable;
	}
}