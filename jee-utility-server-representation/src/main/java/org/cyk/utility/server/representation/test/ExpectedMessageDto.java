package org.cyk.utility.server.representation.test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ExpectedMessageDto {
	
	private String code;
	private String value;
	private String valueContains;
}
