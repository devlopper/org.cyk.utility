package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityListPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> entityClass;
	protected DataTable dataTable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		dataTable = __buildDataTable__();		
	}
	
	protected Map<Object,Object> __getDataTableArguments__() {
		return MapHelper.instantiate(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,entityClass
				,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.FALSE
				,DataTable.ConfiguratorImpl.FIELD_ACTIONS,CollectionHelper.listOf(Action.CREATE)
				,DataTable.ConfiguratorImpl.FIELD_RECORD_ACTIONS,CollectionHelper.listOf(Action.READ,Action.UPDATE,Action.DELETE)
				);
	}
	
	protected DataTable __buildDataTable__() {
		Map<Object,Object> arguments = __getDataTableArguments__();
		if(MapHelper.isEmpty(arguments))
			return null;
		DataTable dataTable = DataTable.build(arguments);	
		return dataTable;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		if(dataTable == null || dataTable.getTitle() == null || StringHelper.isBlank(dataTable.getTitle().getValue()))
			return super.__getWindowTitleValue__();
		return dataTable.getTitle().getValue();
	}
}