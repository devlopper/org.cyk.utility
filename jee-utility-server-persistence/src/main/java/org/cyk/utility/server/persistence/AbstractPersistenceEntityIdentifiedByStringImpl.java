package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentified;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;

public abstract class AbstractPersistenceEntityIdentifiedByStringImpl<ENTITY> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceEntityIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String readWhereIdentifierContains;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(Boolean.TRUE.equals(getIsPhysicallyMapped())) {
			addQueryCollectInstances(readWhereIdentifierContains, "SELECT tuple FROM "+getEntityClass().getSimpleName()+" tuple WHERE lower(tuple.identifier) LIKE lower(:identifier)");	
		}
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass,Properties properties,Object...parameters){
		if(PersistenceFunctionReader.class.equals(functionClass) && Properties.getFromPath(properties,Properties.QUERY_FILTERS) != null) {
			return readWhereIdentifierContains;
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQuery query, Properties properties, Object... objects) {
		if(query.isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {__injectCollectionHelper__().getFirst((Collection<String>) Properties.getFromPath(properties,Properties.QUERY_FILTERS))};
			return new Object[]{AbstractIdentified.FIELD_IDENTIFIER, "%"+objects[0]+"%"};
		}
		return super.__getQueryParameters__(query, properties, objects);
	}
	
}
