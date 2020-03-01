package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true)
public class TestedEntityChild {

	@Id
	private String identifier;
	
	@ManyToOne @NotNull
	private TestedEntityParent parent;
}
