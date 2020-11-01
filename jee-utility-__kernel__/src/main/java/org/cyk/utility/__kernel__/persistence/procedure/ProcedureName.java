package org.cyk.utility.__kernel__.persistence.procedure;

import lombok.Getter;

@Getter
public enum ProcedureName {

	REFRESH_MATERIALIZED_VIEW("refreshMaterializedView")
	
	;
	
	private String value;
	
	private ProcedureName(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	private ProcedureName() {
		this.value = name().toLowerCase();
	}
}