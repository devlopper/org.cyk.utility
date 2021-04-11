package org.cyk.utility.business.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;
import org.cyk.utility.business.SpecificBusiness;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

public abstract class AbstractSpecificBusinessImpl<ENTITY> extends AbstractObject implements SpecificBusiness<ENTITY>,Serializable {
	
	@Override @Transactional
	public void create(QueryExecutorArguments arguments) {
		if(arguments == null)
			return;
		__create__(arguments);
	}
	
	protected void __create__(QueryExecutorArguments arguments) {
		ThrowablesMessages throwablesMessages = new ThrowablesMessages();
		__prepare__((Collection<ENTITY>) arguments.getObjects(),Action.CREATE,throwablesMessages);
		throwablesMessages.throwIfNotEmpty();
		EntityCreator.getInstance().create(arguments);
	}
	
	protected void __prepare__(Collection<ENTITY> entities,Action action,ThrowablesMessages throwablesMessages) {
		for(ENTITY entity : entities) {
			if(entity == null)
				continue;
			__prepare__(entity,action,throwablesMessages);
		}
	}
	
	protected void __prepare__(ENTITY entity,Action action,ThrowablesMessages throwablesMessages) {
		
	}
	
	@Override @Transactional
	public void create(Collection<ENTITY> entities) {
		if(CollectionHelper.isEmpty(entities))
			return;
		create(new QueryExecutorArguments().setObjects(CollectionHelper.cast(Object.class, entities.stream().filter(x -> x != null).collect(Collectors.toList()))));
	}
	
	@Override @Transactional
	public void create(ENTITY... entities) {
		if(ArrayHelper.isEmpty(entities))
			return;
		create(CollectionHelper.listOf(entities));
	}
}