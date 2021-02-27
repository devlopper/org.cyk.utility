package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.persistence.query.QueryContext;
import org.cyk.utility.__kernel__.persistence.query.QueryStringHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.playground.server.persistence.api.PersonTypePersistence;
import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class PersonTypePersistenceImpl extends AbstractPersistenceEntityImpl<PersonType> implements PersonTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByFiltersLike, 
				"SELECT personType FROM PersonType personType "
				+ "WHERE "
				+ QueryStringHelper.formatTupleFieldLike("personType", "code") + " AND (" + QueryStringHelper.formatTupleFieldLikeOrTokens("personType", "name","name",4,LogicalOperator.AND)+")"
				+ "ORDER BY personType.code ASC");
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties, Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByFiltersLike)) {
			if(ArrayHelper.isEmpty(objects)) {
				String code = queryContext.getStringLike("code");
				List<String> names = queryContext.getFieldValueLikes("name",5);
				objects = new Object[] {code,names.get(0),names.get(1),names.get(2),names.get(3),names.get(4)};
			}
			int index = 0;
			objects = new Object[]{"code",objects[index++],"name",objects[index++],"name1",objects[index++],"name2",objects[index++],"name3",objects[index++],"name4",objects[index++]};
			return objects;
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}
