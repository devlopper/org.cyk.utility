package org.cyk.utility.__kernel__.__entities__;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class DepartmentAddressDto extends AbstractIdentifiableSystemScalarStringImpl {

	private DepartmentDto department;
	private AddressDto address;
	
}