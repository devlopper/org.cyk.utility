package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeListPage extends AbstractEntityListPageContainerManagedImpl<PersonType> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Map<Object, Object> __getDataTableArguments__() {
		Map<Object, Object> arguments = super.__getDataTableArguments__();
		arguments.put(DataTable.ConfiguratorImpl.FIELD_COLUMNS_FIELDS_NAMES_COMPUTABLE, Boolean.TRUE);
		return arguments;
	}
	
}