package org.cyk.utility.common.computation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum Function {

	COUNT(Long.class),
	
	;
	
	private Class<?> resultClass;
}
