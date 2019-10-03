package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.api.ParentChildPersistence;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.persistence.entities.ParentChild;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

@ApplicationScoped
public class ParentChildPersistenceImpl extends AbstractPersistenceEntityImpl<ParentChild> implements ParentChildPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByParentsCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByParentsCodes, "SELECT tuple FROM ParentChild tuple WHERE tuple.parent.code IN :parentsCodes");
	}

	@Override
	public Collection<ParentChild> readByParentsCodes(Collection<String> parentsCodes) {
		if(CollectionHelper.isEmpty(parentsCodes))
			return null;
		Properties properties = new Properties().setQueryIdentifier(readByParentsCodes);
		return __readMany__(properties,____getQueryParameters____(properties,parentsCodes));
	}

	@Override
	public Collection<ParentChild> readByParentsCodes(String... parentsCodes) {
		if(ArrayHelper.isEmpty(parentsCodes))
			return null;
		return readByParentsCodes(CollectionHelper.listOf(parentsCodes));
	}

	@Override
	public Collection<ParentChild> readByParents(Collection<Parent> parents) {
		if(CollectionHelper.isEmpty(parents))
			return null;
		return readByParentsCodes(parents.stream().map(Parent::getCode).collect(Collectors.toList()));
	}

	@Override
	public Collection<ParentChild> readByParents(Parent... parents) {
		if(ArrayHelper.isEmpty(parents))
			return null;
		return readByParents(CollectionHelper.listOf(parents));
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsCodes)) {
			if(ArrayHelper.isEmpty(objects))
				objects = new Object[] {queryContext.getFilterByKeysValue(ParentChild.FIELD_PARENT)};
			return new Object[]{"parentsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}
