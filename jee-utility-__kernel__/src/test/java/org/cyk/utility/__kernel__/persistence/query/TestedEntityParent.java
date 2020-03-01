package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true)
public class TestedEntityParent {

	@Id
	private String identifier;
	
}
