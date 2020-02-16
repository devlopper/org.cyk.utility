package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.properties.Properties;
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
	private String readQueryIdentifier,countQueryIdentifier;
	
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
		FilterDto filter = new FilterDto();
		if(MapHelper.isNotEmpty(filters)) {
			for(Map.Entry<String, Object> entry : filters.entrySet())
				filter.addField(entry.getKey(), entry.getValue());
		}
		Properties properties = new Properties().setQueryIdentifier(readQueryIdentifier).setFilters(filter).setIsPageable(Boolean.TRUE).setFrom(first).setCount(pageSize);		
		List<ENTITY> list = (List<ENTITY>) controller.read(properties);
		if(CollectionHelper.isEmpty(list))
			setRowCount(0);
		else {
			Long count = controller.count(new Properties().setQueryIdentifier(countQueryIdentifier).setFilters(filter));
			setRowCount(count == null ? 0 : count.intValue());	
		}
		return list;
	}
	
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
	
}
