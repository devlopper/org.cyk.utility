package org.cyk.utility.__kernel__.__entities__;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@Table(name = "table1")
public class JpaEntityCompositeId implements Serializable {

	@Id private String identifier1;
	//@Id private String identifier2;
}
