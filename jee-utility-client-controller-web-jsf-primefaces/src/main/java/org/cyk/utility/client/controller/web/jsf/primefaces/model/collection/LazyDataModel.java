package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.primefaces.model.SortOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LazyDataModel<ENTITY> extends org.primefaces.model.LazyDataModel<ENTITY> implements Serializable {

	private Map<String,ENTITY> map;
	private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controller;
	private String readQueryIdentifier,countQueryIdentifier,entityFieldsNamesAsString;
	private Collection<String> entityFieldsNames;
	private Listener listener;
	
	public LazyDataModel(Class<ENTITY> entityClass) {
		this.entityClass = entityClass;
		if(this.entityClass != null) {
			this.controller = DependencyInjection.inject(ControllerLayer.class).injectInterfaceClassFromEntityClass(entityClass);
		}
	}
	
	@Override
	public List<ENTITY> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		if(controller == null)
			return null;
		FilterDto filter = __instantiateFilter__(first, pageSize, sortField, sortOrder, filters);
		if(listener != null && filter != null)
			listener.processFilter(filter);
		List<ENTITY> list = (List<ENTITY>) controller.read(__getReadProperties__(filter,first, pageSize));
		if(CollectionHelper.isEmpty(list))
			setRowCount(0);
		else {
			Long count = controller.count(__getCountProperties__(filter));
			setRowCount(count == null ? 0 : count.intValue());	
		}
		return list;
	}
	
	protected FilterDto __instantiateFilter__(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
		FilterDto filter = new FilterDto();
		if(MapHelper.isNotEmpty(filters)) {
			for(Map.Entry<String, Object> entry : filters.entrySet())
				filter.addField(entry.getKey(), entry.getValue());
		}
		return filter;
	}
	
	protected String __getPropertiesFields__() {
		if(entityFieldsNamesAsString == null && CollectionHelper.isNotEmpty(entityFieldsNames))
			entityFieldsNamesAsString = StringHelper.concatenate(entityFieldsNames, ",");
		return entityFieldsNamesAsString;
	}
	
	protected Properties __getReadProperties__(FilterDto filter,int first, int pageSize) {
		Properties properties = new Properties().setQueryIdentifier(readQueryIdentifier)
				.setFields(__getPropertiesFields__())
				.setFilters(filter)
				.setIsPageable(Boolean.TRUE).setFrom(first).setCount(pageSize);
		if(listener != null && filter != null)
			listener.processReadProperties(properties);
		return properties;
	}
	
	protected Properties __getCountProperties__(FilterDto filter) {
		return new Properties().setQueryIdentifier(countQueryIdentifier).setFilters(filter);
	}
	
	/**/
	
	@Override
	public Object getRowKey(ENTITY entity) {
		if(entity == null)
			return null;
		Object identifier = FieldHelper.readSystemIdentifier(entity);
		if(identifier == null)
			return null;
		if(map == null)
			map = new HashMap<>();
		map.put(identifier.toString(), entity);
		return identifier;
	}
	
	@Override
	public ENTITY getRowData(String identifier) {
		if(map == null)
			return null;
		return map.get(identifier);
	}
	
	/**/
	
	public static interface Listener {
		
		void processFilter(FilterDto filter);
		
		void processReadProperties(Properties properties);
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable{
			
			@Override
			public void processFilter(FilterDto filter) {}
			
			@Override
			public void processReadProperties(Properties properties) {}
		}
	}
}
