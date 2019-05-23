package org.cyk.utility.server.representation;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractEntityFromPersistenceEntityLinked extends AbstractEntityFromPersistenceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String link;
	
	@Override
	public String toString() {
		return link;
	}
	
	/**/
	
	public static final String FIELD_LINK = "link";
}
