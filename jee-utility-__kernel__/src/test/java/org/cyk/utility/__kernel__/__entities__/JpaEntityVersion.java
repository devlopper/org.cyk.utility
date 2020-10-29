package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class JpaEntityVersion {

	@Id
	private Integer identifier;
	
	@Version
	private Integer version;
	
	//@Id
	//private String identifier;
	
	private String code;
	private String name;
	
}
