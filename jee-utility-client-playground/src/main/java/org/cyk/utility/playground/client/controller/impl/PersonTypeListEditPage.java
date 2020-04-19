package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeListEditPage extends AbstractEntityListPageContainerManagedImpl<PersonType> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Map<Object, Object> __getDataTableArguments__() {
		Map<Object, Object> arguments = super.__getDataTableArguments__();
		arguments.put(DataTable.FIELD_VALUE, __inject__(PersonTypeController.class).read());
		arguments.put(DataTable.FIELD_EDITABLE, Boolean.TRUE);
		arguments.put(DataTable.ConfiguratorImpl.FIELD_EDITABLE_CELL, Boolean.TRUE);
		return arguments;
	}
}