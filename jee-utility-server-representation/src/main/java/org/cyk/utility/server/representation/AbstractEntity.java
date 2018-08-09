package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.field.FieldHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractEntity<PERSISTENCE_ENTITY> extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identifier;
	private String code;
	
	public AbstractEntity(PERSISTENCE_ENTITY entity) {
		if(entity!=null) {
			Object identifier = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(entity);
			if( identifier!=null)
				this.identifier = identifier.toString();
			identifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(entity);
			if( identifier!=null)
				this.code = identifier.toString();
		}
	}
	
	public abstract PERSISTENCE_ENTITY getPersistenceEntity();
	
}
