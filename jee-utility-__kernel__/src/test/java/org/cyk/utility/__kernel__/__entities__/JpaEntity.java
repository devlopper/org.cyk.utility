package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class JpaEntity {

	@Id
	private Integer identifier;
	
	//@Id
	//private String identifier;
	
	private String code;
	private String name;
	
}
