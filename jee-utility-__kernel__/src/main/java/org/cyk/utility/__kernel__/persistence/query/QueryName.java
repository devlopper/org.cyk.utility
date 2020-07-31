package org.cyk.utility.__kernel__.persistence.query;

import lombok.Getter;

@Getter
public enum QueryName {

	READ,COUNT
	,READ_BY_SYSTEM_IDENTIFIERS("readBySystemIdentifiers")
	,READ_BY_BUSINESS_IDENTIFIERS("readByBusinessIdentifiers")
	
	,READ_WHERE_CODE_OR_NAME_LIKE("readWhereCodeOrNameLike")
	,COUNT_WHERE_CODE_OR_NAME_LIKE("countWhereCodeOrNameLike")
	
	,FILTER
	
	;
	
	private String value;
	
	private QueryName(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	private QueryName() {
		this.value = name().toLowerCase();
	}
}
