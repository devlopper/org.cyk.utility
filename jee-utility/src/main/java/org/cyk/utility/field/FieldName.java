package org.cyk.utility.field;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueUsageType;

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
		if (DependencyInjection.inject(StringHelper.class).isBlank(business))
			business = name().toLowerCase();
		if (DependencyInjection.inject(StringHelper.class).isBlank(system))
			system = name().toLowerCase();
		valueMap.put(ValueUsageType.BUSINESS, business);
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