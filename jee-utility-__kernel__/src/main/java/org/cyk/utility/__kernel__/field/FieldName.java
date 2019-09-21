package org.cyk.utility.__kernel__.field;

import org.cyk.utility.__kernel__.value.ValueUsageType;

@lombok.Getter
public enum FieldName {
	IDENTIFIER("code", "identifier")
	, TYPE
	, NAME
	, BIRTH_DATE("birthDate")
	, DEATH_DATE("deathDate")
	, IDENTIFIABLE_PERIOD("__identifiablePeriod__")
	, ORDER_NUMBER("orderNumber", "creationOrderNumber");
	
	private java.util.Map<ValueUsageType, String> valueMap = new java.util.HashMap<>();

	private FieldName(String business, String system) {
		if (business == null || business.isBlank())
			business = name().toLowerCase();
		valueMap.put(ValueUsageType.BUSINESS, business);
		
		if (system == null || system.isBlank())
			system = name().toLowerCase();
		valueMap.put(ValueUsageType.SYSTEM, system);
	}

	private FieldName(String business) {
		this(business, business);
	}

	private FieldName() {
		this(null, null);
	}

	public String getByValueUsageType(ValueUsageType valueUsageType) {
		return valueMap.get(valueUsageType);
	}

}