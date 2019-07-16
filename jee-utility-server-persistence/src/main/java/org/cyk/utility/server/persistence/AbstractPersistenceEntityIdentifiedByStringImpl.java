package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentified;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

public abstract class AbstractPersistenceEntityIdentifiedByStringImpl<ENTITY> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceEntityIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String readWhereIdentifierContains;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		if(Boolean.TRUE.equals(getIsPhysicallyMapped())) {
			addQueryCollectInstances(readWhereIdentifierContains, String.format("SELECT tuple FROM %s tuple WHERE lower(tuple.identifier) LIKE lower(:identifier)",__classInstance__.getTupleName()));			
		}
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass,Properties properties,Object...parameters){
		Map<String, Object> filters = __getFiltersFromProperties__(properties);
		if(PersistenceFunctionReader.class.equals(functionClass) &&  filters != null && filters.get(AbstractIdentified.FIELD_IDENTIFIER) instanceof String)
			return readWhereIdentifierContains;
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereIdentifierContains)) {
			if(Boolean.TRUE.equals(__inject__(ArrayHelper.class).isEmpty(objects)))
				objects = new Object[] {"%"+queryContext.getFilterByKeysValue(AbstractIdentified.FIELD_IDENTIFIER)+"%"};
			return new Object[]{AbstractIdentified.FIELD_IDENTIFIER, objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
}
