package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.Namable;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NamableListPage extends AbstractEntityListPageContainerManagedImpl<Namable> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();

		list.getDataTable().addColumnsAfterRowIndex(
			Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_CODE,Column.FIELD_WIDTH,"200",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
			,Column.build(Map.of(Column.FIELD_FIELD_NAME,Namable.FIELD_NAME,Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.TRUE))
		);
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Namable list";
	}
}