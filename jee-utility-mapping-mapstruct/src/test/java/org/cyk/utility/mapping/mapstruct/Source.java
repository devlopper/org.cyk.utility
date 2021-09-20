package org.cyk.utility.mapping.mapstruct;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Source extends BaseSource {

	private String name;
	
}