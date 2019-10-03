package org.cyk.utility.__kernel__.value;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Identifier implements Serializable {
	private static final long serialVersionUID = 1L;

	private ValueUsageType usageType;
	private Object value;
	
}
