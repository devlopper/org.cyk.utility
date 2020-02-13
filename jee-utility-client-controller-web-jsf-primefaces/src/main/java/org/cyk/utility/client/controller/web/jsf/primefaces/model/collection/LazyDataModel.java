package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.ControllerLayer;
import org.primefaces.model.SortOrder;

public class LazyDataModel<ENTITY> extends org.primefaces.model.LazyDataModel<ENTITY> implements Serializable {

	private Map<String,ENTITY> map;
	private Class<ENTITY> entityClass;
	private ControllerEntity<ENTITY> controller;
	
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
		/*filter.addField("administrativeUnit", MapHelper.readByKey(filters, "administrativeUnit"));
		String sectionCode = (String) MapHelper.readByKey(filters, AdministrativeUnit.FIELD_SECTION);
		if(StringHelper.isBlank(sectionCode))
			sectionCode = defaultSection == null ? null : defaultSection.getCode();
		filter.addField(AdministrativeUnit.FIELD_SECTION, sectionCode);				
		filter.addField(AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION, MapHelper.readByKey(filters, AdministrativeUnit.FIELD_FUNCTIONAL_CLASSIFICATION));
		filter.addField(AdministrativeUnit.FIELD_SERVICE_GROUP, MapHelper.readByKey(filters, AdministrativeUnit.FIELD_SERVICE_GROUP));
		filter.addField(AdministrativeUnit.FIELD_LOCALISATION, MapHelper.readByKey(filters, AdministrativeUnit.FIELD_LOCALISATION));
		*/
		
		Properties properties = new Properties().setFilters(filter).setIsPageable(Boolean.TRUE).setFrom(first).setCount(pageSize);
		
		List<ENTITY> list = (List<ENTITY>) controller.read(properties);
		if(CollectionHelper.isEmpty(list))
			setRowCount(0);
		else {
			Long count = controller.count(new Properties().setFilters(filter));
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
