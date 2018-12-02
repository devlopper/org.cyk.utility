package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
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
	public Collection<ENTITY> readMany(Properties properties) {
		return collection;
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
	
}
