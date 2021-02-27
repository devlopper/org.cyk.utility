package org.cyk.utility.persistence.server.query;

import lombok.Getter;

@Getter
public enum QueryName {

	READ
	,READ_FOR_UI("readForUI")
	,COUNT
	
	,READ_CODES("readCodes")
	,COUNT_CODES("countCodes")
	
	,READ_BY_IDENTIFIER_FOR_UI("readByIdentifierForUI")
	,READ_BY_IDENTIFIER_FOR_EDIT("readByIdentifierForEdit")
	,READ_BY_SYSTEM_IDENTIFIERS("readBySystemIdentifiers")
	,READ_BY_SYSTEM_IDENTIFIERS_WITH_ALL("readBySystemIdentifiersWithAll")
	,READ_BY_SYSTEM_IDENTIFIER_WITH_ALL("readBySystemIdentifierWithAll")
	
	,READ_BY_BUSINESS_IDENTIFIERS("readByBusinessIdentifiers")
	
	,READ_WHERE_CODE_OR_NAME_LIKE("readWhereCodeOrNameLike")
	,COUNT_WHERE_CODE_OR_NAME_LIKE("countWhereCodeOrNameLike")
	
	,READ_WHERE_FILTER("readWhereFilter")
	,READ_WHERE_FILTER_FOR_UI("readWhereFilterForUI")
	,READ_WHERE_FILTER_FOR_EDIT("readWhereFilterForEdit")
	,COUNT_WHERE_FILTER("countWhereFilter")
	,UPDATE_WHERE_FILTER("updateWhereFilter")
	,DELETE_WHERE_FILTER("deleteWhereFilter")
	
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