package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityParent {

	@Id private String identifier;
	@NotNull @Column(unique = true) private String code;
	@NotNull private String name;
	
	public TestedEntityParent(String identifier, String code, String name) {
		super();
		this.identifier = identifier;
		this.code = code;
		this.name = name;
	}
	
	
}
