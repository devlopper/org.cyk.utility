package org.cyk.utility.__kernel__.persistence.query;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface ByDimensionOneBusinessIdentifierQuerier<ENTITY,DIMENSION,DIMENSION_IDENTIFIER> extends ByDimensionOneIdentifierQuerier<ENTITY, DIMENSION, DIMENSION_IDENTIFIER> {

	@Override
	default ValueUsageType getIdentifierValueUsageType() {
		return ValueUsageType.BUSINESS;
	}
	
}