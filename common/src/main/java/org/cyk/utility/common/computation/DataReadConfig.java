package org.cyk.utility.common.computation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DataReadConfig implements Serializable {

	private static final long serialVersionUID = -8258556701704160443L;
	
	private Long firstResultIndex;
	private Long maximumResultCount;
	
}
