package org.cyk.utility.__kernel__.__entities__;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Embeddable
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class CompositeIdClass implements Serializable {

	private String identifier1;
	private String identifier2;
}
