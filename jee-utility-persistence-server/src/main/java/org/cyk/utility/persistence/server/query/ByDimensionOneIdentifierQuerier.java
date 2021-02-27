package org.cyk.utility.persistence.server.query;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface ByDimensionOneIdentifierQuerier<ENTITY,DIMENSION,DIMENSION_IDENTIFIER> extends Querier {

	ValueUsageType getIdentifierValueUsageType();
	
	default Collection<DIMENSION_IDENTIFIER> getIdentifiers(Collection<DIMENSION> dimensions) {
		if(CollectionHelper.isEmpty(dimensions)) {
			LogHelper.logFine("collection to deduce identifiers is empty", getClass());
			return null;
		}
		Collection<DIMENSION_IDENTIFIER> identifiers = (Collection<DIMENSION_IDENTIFIER>) (ValueUsageType.SYSTEM.equals(getIdentifierValueUsageType()) ?
				FieldHelper.readSystemIdentifiers(dimensions) : FieldHelper.readBusinessIdentifiers(dimensions));
		if(CollectionHelper.isEmpty(identifiers)) {
			LogHelper.logFine("deduced identifiers collection is empty", getClass());
			return null;
		}		
		return identifiers;
	}
	
	/* read */
	
	Collection<ENTITY> readByIdentifiers(Collection<DIMENSION_IDENTIFIER> identifiers,QueryExecutorArguments arguments);
	
	default Collection<ENTITY> readByIdentifiers(Collection<DIMENSION_IDENTIFIER> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return readByIdentifiers(identifiers,null);
	}
	
	default Collection<ENTITY> readByIdentifiers(QueryExecutorArguments arguments,DIMENSION_IDENTIFIER...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return readByIdentifiers(CollectionHelper.listOf(identifiers),arguments);
	}
	
	default Collection<ENTITY> readByIdentifiers(DIMENSION_IDENTIFIER...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return readByIdentifiers(CollectionHelper.listOf(identifiers),null);
	}
	
	default Collection<ENTITY> read(Collection<DIMENSION> dimensions,QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(dimensions)) {
			LogHelper.logFine("collection is empty", getClass());
			return null;
		}
		Collection<DIMENSION_IDENTIFIER> identifiers = (Collection<DIMENSION_IDENTIFIER>) getIdentifiers(dimensions); 
		if(CollectionHelper.isEmpty(identifiers)) {
			LogHelper.logFine("identifiers is empty", getClass());
			return null;
		}
		return readByIdentifiers(identifiers, arguments);
	}
	
	default Collection<ENTITY> read(Collection<DIMENSION> dimensions) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		return read(dimensions,null);
	}
	
	default Collection<ENTITY> read(QueryExecutorArguments arguments,DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return read(CollectionHelper.listOf(dimensions),arguments);
	}
	
	default Collection<ENTITY> read(DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions)) {
			LogHelper.logFine("array is empty", getClass());
			return null;
		}
		return read(CollectionHelper.listOf(dimensions),null);
	}
	
	/* count */
	
	Long countByIdentifiers(Collection<DIMENSION_IDENTIFIER> identifiers,QueryExecutorArguments arguments);
	
	default Long countByIdentifiers(Collection<DIMENSION_IDENTIFIER> identifiers) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return countByIdentifiers(identifiers,null);
	}
	
	default Long countByIdentifiers(QueryExecutorArguments arguments,DIMENSION_IDENTIFIER...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return countByIdentifiers(CollectionHelper.listOf(identifiers),arguments);
	}
	
	default Long countByIdentifiers(DIMENSION_IDENTIFIER...identifiers) {
		if(ArrayHelper.isEmpty(identifiers))
			return null;
		return countByIdentifiers(CollectionHelper.listOf(identifiers),null);
	}
	
	default Long count(Collection<DIMENSION> dimensions,QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		Collection<DIMENSION_IDENTIFIER> identifiers = (Collection<DIMENSION_IDENTIFIER>) getIdentifiers(dimensions); 
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return countByIdentifiers(identifiers, arguments);
	}
	
	default Long count(Collection<DIMENSION> dimensions) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		return count(dimensions,null);
	}
	
	default Long count(QueryExecutorArguments arguments,DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return count(CollectionHelper.listOf(dimensions),arguments);
	}
	
	default Long count(DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return count(CollectionHelper.listOf(dimensions),null);
	}
	
}