package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCodedAndNamed;
import org.cyk.utility.random.RandomHelper;

public abstract class AbstractControllerEntityPersistedInCollectionImpl<ENTITY> extends AbstractControllerEntityImpl<ENTITY> implements ControllerEntityPersistedInCollection<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<ENTITY> collection;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		collection = new ArrayList<>();
		Collection<ENTITY> initialCollection = __getInitialCollection__();
		if(initialCollection!=null)
			collection.addAll(initialCollection);
	}
	
	protected Collection<ENTITY> __getInitialCollection__(){
		Collection<ENTITY> collection = new ArrayList<>();
		Integer count = __getNumberOfInitialCount__();
		if(count!=null && count > 0)
			for(Integer index = 1 ; index <= count ; index = index + 1)
				collection.add(__instanciateOneRandomly__(index));
		return collection;
	}
	
	protected Integer __getNumberOfInitialCount__() {
		return 3;
	}
	
	protected ENTITY __instanciateOneRandomly__(Integer index) {
		ENTITY entity = __inject__(__entityClass__);
		FieldHelper.writeSystemIdentifier(entity, index);
		FieldHelper.writeBusinessIdentifier(entity, RandomHelper.getAlphanumeric(4));
		return entity;
	}
	
	@Override
	public ControllerServiceProvider<ENTITY> create(ENTITY entity, Properties properties) {
		FieldHelper.writeSystemIdentifier(entity, RandomHelper.getNumeric(4).toString());
		collection.add(entity);
		return this;
	}
	
	@Override
	public Collection<ENTITY> readByIdentifiers(Collection<Object> identifiers, ValueUsageType valueUsageType,Properties properties) {
		return __filter__(collection, properties,Boolean.FALSE);
	}
	
	@Override
	public ENTITY readByIdentifier(Object identifier, ValueUsageType valueUsageType, Properties properties) {
		for(ENTITY index : collection) {
			if(ValueUsageType.BUSINESS.equals(valueUsageType)) {
				if(FieldHelper.readBusinessIdentifier(index).toString().equals(identifier.toString()))
					return index;
			}else {
				if(FieldHelper.readSystemIdentifier(index).toString().equals(identifier.toString()))
					return index;
			}
		}
		return null;
	}
	
	@Override
	public ControllerEntityPersistedInCollection<ENTITY> update(ENTITY entity, Properties properties) {
		//Nothing to do
		return this;
	}
	
	@Override
	public ControllerEntityPersistedInCollection<ENTITY> delete(ENTITY entity, Properties properties) {
		collection.remove(entity);
		return this;
	}
	
	@Override
	public Long count(Properties properties) {
		Integer count = CollectionHelper.getSize(__filter__(collection, properties,Boolean.TRUE));
		return count == null ? null : Long.valueOf(count);
	}
	
	protected Collection<ENTITY> __filter__(Collection<ENTITY> entities,Properties properties,Boolean counting) {
		Collection<ENTITY> result = null;
		Integer from = counting ? null : (Integer) Properties.getFromPath(properties,Properties.FROM);
		Integer count = counting ? null :(Integer) Properties.getFromPath(properties,Properties.COUNT);
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(entities))) {
			result = new ArrayList<ENTITY>();
			String query = CollectionHelper.getFirst((List<String>) Properties.getFromPath(properties,Properties.FILTERS));
			for(ENTITY index : entities) {
				if( StringUtils.isEmpty(query)
						|| (index instanceof DataIdentifiedByStringAndCoded && StringUtils.contains(((DataIdentifiedByStringAndCoded)index).getCode(), query))
						|| (index instanceof DataIdentifiedByStringAndCodedAndNamed && StringUtils.contains(((DataIdentifiedByStringAndCodedAndNamed)index).getName(), query))
						) {
					
					if(from!=null && from-- > 0)
						continue;
					
					if(count == null || count-- > 0 ) {
						result.add(index);
					}
					
					if(count!= null && count == 0)
						break;
					
				}
			}
		}
		return result;
	}
	
}
