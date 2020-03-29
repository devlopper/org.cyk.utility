package org.cyk.utility.__kernel__.__entities__;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityParentDto extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	private String lazy;
	private String earger;
	
}