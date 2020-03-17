package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.api.PersonTypeController;
import org.cyk.utility.playground.client.controller.entities.PersonType;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.event.CellEditEvent;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonTypeListEditPage extends AbstractEntityListPageContainerManagedImpl<PersonType> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		dataTable.setLazy(Boolean.FALSE);
		dataTable.setValue(__inject__(PersonTypeController.class).read());
		dataTable.setEditable(Boolean.TRUE);
		dataTable.setEditMode("cell");
		dataTable.getAjaxes().get("cellEdit").setDisabled(Boolean.FALSE);
		dataTable.getAjaxes().get("cellEdit").setListener(new AbstractAction.Listener.AbstractImpl() {
			protected Object __executeFunction__(Object argument) {
				CellEditEvent event = (CellEditEvent) argument;
				DynamicColumn dynamicColumn = (DynamicColumn) event.getColumn();
				PersonType personType = (PersonType) dataTable.getValueAt(event.getRowIndex());
				String fieldName = dynamicColumn.getIndex() == 0 ? "code" : "name";
				__inject__(PersonTypeController.class).update(personType,new Properties().setFields(fieldName));
				return null;
			}
		}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
	}
	
	@Override
	protected Collection<String> __getColumnsFieldsNames__(Class<PersonType> entityClass) {
		return List.of(PersonType.FIELD_CODE,PersonType.FIELD_NAME);
	}
}