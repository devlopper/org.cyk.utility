package org.cyk.utility.field;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MyEntity {

	private Integer identifier;
	private String code;
	
	public String getIdentifierAndCode() {
		return identifier+code;
	}
}
