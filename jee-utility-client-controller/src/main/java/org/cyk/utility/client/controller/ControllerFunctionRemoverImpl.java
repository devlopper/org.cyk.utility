package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.type.BooleanHelper;

public class ControllerFunctionRemoverImpl extends AbstractControllerFunctionImpl implements ControllerFunctionRemover , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionDelete.class));
	}
	
	@Override
	protected void __executeRepresentation__() {
		Boolean isAll = getProperties().getAll() == null ? Boolean.FALSE : BooleanHelper.get(getProperties().getAll());
		if(Boolean.TRUE.equals(isAll)) {
			if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
				__response__ = ((RepresentationEntity<?,?,?>)__representation__).deleteAll();
		}else {
			if(__representation__ instanceof RepresentationEntity<?, ?, ?>) {
				if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__entitiesSystemIdentifiers__)))
					__response__ = ((RepresentationEntity<?,Object,?>)__representation__).deleteByIdentifiers(
							__entitiesSystemIdentifiers__.stream().map(x -> x.toString()).collect(Collectors.toList()), ValueUsageType.SYSTEM.name());
				else if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__entitiesBusinessIdentifiers__)))
					__response__ = ((RepresentationEntity<?,Object,?>)__representation__).deleteByIdentifiers(
							__entitiesBusinessIdentifiers__.stream().map(x -> x.toString()).collect(Collectors.toList()), ValueUsageType.BUSINESS.name());
				//__response__ = ((RepresentationEntity<?,Object,?>)__representation__).deleteOne(__representationEntities__.iterator().next());
			}
		}
		//Object identifierType = (String) getProperty(Properties.VALUE_USAGE_TYPE);
		//if(Boolean.TRUE.equals(CollectionHelper.isEmpty(__entities__)))
		//	;
	}
	
	@Override
	public ControllerFunctionRemover addActionEntities(Object... entities) {
		return (ControllerFunctionRemover) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionRemover setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionRemover) super.setActionEntityClass(entityClass);
	}

}
