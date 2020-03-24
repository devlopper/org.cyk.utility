package org.cyk.utility.__kernel__.persistence.query;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface ByDimensionOneSystemIdentifierQuerier<ENTITY,DIMENSION,DIMENSION_IDENTIFIER> extends ByDimensionOneIdentifierQuerier<ENTITY, DIMENSION, DIMENSION_IDENTIFIER> {

	/* read */
	
	@Override
	default ValueUsageType getIdentifierValueUsageType() {
		return ValueUsageType.SYSTEM;
	}
	
}