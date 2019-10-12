package org.cyk.utility.server.representation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @Deprecated
public abstract class AbstractEntityFromPersistenceEntityCodedAndNamed extends AbstractEntityFromPersistenceEntityCoded implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@Override
	public String toString() {
		return name;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
}
