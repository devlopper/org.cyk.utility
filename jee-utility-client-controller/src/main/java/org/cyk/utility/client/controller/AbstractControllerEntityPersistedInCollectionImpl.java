package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCodedAndNamed;
import org.cyk.utility.client.controller.navigation.Navigation;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.navigation.NavigationRedirector;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.system.action.SystemActionRead;

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
		ENTITY entity = __inject__(getEntityClass());
		__injectFieldHelper__().setFieldValueSystemIdentifier(entity, index);
		__injectFieldHelper__().setFieldValueBusinessIdentifier(entity, __inject__(RandomHelper.class).getAlphanumeric(4));
		return entity;
	}
	
	@Override
	public ControllerServiceProvider<ENTITY> create(ENTITY entity, Properties properties) {
		__injectFieldHelper__().setFieldValueSystemIdentifier(entity, __inject__(RandomHelper.class).getNumeric(4).toString());
		collection.add(entity);
		return this;
	}
	
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		for(ENTITY index : collection) {
			if(__injectFieldHelper__().getFieldValueSystemIdentifier(index).toString().equals(identifier.toString()))
				return index;
		}
		return null;
	}
	
	@Override
	public ENTITY readOneByBusinessIdentifier(Object identifier) {
		for(ENTITY index : collection) {
			if(__injectFieldHelper__().getFieldValueBusinessIdentifier(index).toString().equals(identifier.toString()))
				return index;
		}
		return null;
	}
	
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		return __filter__(collection, properties,Boolean.FALSE);
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
		Integer count = __injectCollectionHelper__().getSize(__filter__(collection, properties,Boolean.TRUE));
		return count == null ? null : new Long(count);
	}
	
	@Override
	public ControllerEntity<ENTITY> redirect(Object identifier, Properties properties) {
		ENTITY entity = readOneByBusinessIdentifier(identifier);
		if(entity == null) {
			__injectThrowableHelper__().throwRuntimeException("Entity with identifier "+identifier+" not found");
		}else {
			SystemActionRead systemActionRead = __inject__(SystemActionRead.class);
			systemActionRead.setEntityClass(getEntityClass());
			systemActionRead.getEntitiesIdentifiers(Boolean.TRUE).add(__injectFieldHelper__().getFieldValueSystemIdentifier(entity));
			
			NavigationBuilder navigationBuilder = __inject__(NavigationBuilder.class).setIdentifierBuilderSystemAction(systemActionRead);
			Navigation navigation = navigationBuilder.execute().getOutput();
			__inject__(NavigationRedirector.class).setNavigation(navigation).execute();	
		}
		return this;
	}
	
	protected Collection<ENTITY> __filter__(Collection<ENTITY> entities,Properties properties,Boolean counting) {
		Collection<ENTITY> result = null;
		Integer from = counting ? null : (Integer) properties.getFrom();
		Integer count = counting ? null :(Integer) properties.getCount();
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(entities))) {
			result = new ArrayList<ENTITY>();
			String query = __injectCollectionHelper__().getFirst((List<String>) properties.getFilters());
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
