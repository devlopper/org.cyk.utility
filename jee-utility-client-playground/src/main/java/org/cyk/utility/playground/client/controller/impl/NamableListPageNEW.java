package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.LazyDataModel;
import org.cyk.utility.client.controller.web.jsf.primefaces.page.AbstractEntityListPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.Namable;
import org.cyk.utility.playground.server.persistence.api.NamableQuerier;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NamableListPageNEW extends AbstractEntityListPageContainerManagedImpl<Namable> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected DataTable __buildDataTable__() {
		DataTable dataTable = super.__buildDataTable__();
		@SuppressWarnings("unchecked")
		LazyDataModel<Namable> lazyDataModel = (LazyDataModel<Namable>) dataTable.getValue();
		lazyDataModel.setReaderUsable(Boolean.TRUE);
		lazyDataModel.setReadQueryIdentifier(NamableQuerier.QUERY_IDENTIFIER_READ_VIEW_01);
		return dataTable;
	}
	
	@Override
	protected Collection<String> __getColumnsFieldsNames__(Class<Namable> entityClass) {
		return List.of(Namable.FIELD_CODE,Namable.FIELD_NAME);
	}
	
	@Override
	protected Map<Object, Object> __getColumnArguments__(String fieldName) {
		Map<Object, Object> map = super.__getColumnArguments__(fieldName);
		map.put(Column.ConfiguratorImpl.FIELD_FILTERABLE, Boolean.TRUE);
		return map;
	}
}