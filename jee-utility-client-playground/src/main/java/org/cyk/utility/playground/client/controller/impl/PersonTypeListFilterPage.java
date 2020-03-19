package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeListFilterPage extends AbstractEntityListPageContainerManagedImpl<PersonType> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Map<Object, Object> __getDataTableArguments__() {
		Map<Object, Object> arguments = super.__getDataTableArguments__();
		arguments.put(DataTable.ConfiguratorImpl.FIELD_FILTERABLE, Boolean.TRUE);
		return arguments;
	}
	
	@Override
	protected Collection<String> __getColumnsFieldsNames__(Class<PersonType> entityClass) {
		return List.of(PersonType.FIELD_CODE,PersonType.FIELD_NAME);
	}
	
	@Override
	protected Map<Object, Object> __getColumnArguments__(String fieldName) {
		Map<Object, Object> arguments = super.__getColumnArguments__(fieldName);
		arguments.put(Column.ConfiguratorImpl.FIELD_FILTERABLE, Boolean.TRUE);
		return arguments;
	}
}