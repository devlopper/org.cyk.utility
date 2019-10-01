package org.cyk.utility.__kernel__;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAsFunctionParameter<VALUE> {

	/**
	 * To handle inheritance
	 */
	private Class<? extends VALUE> klass;
	
	/**
	 * To handle known value
	 */
	private VALUE value;
}
