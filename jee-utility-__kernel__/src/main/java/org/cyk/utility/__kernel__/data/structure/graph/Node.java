package org.cyk.utility.__kernel__.data.structure.graph;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identifier,name;
	
	
}
