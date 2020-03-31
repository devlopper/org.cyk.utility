package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Address extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	@Override
	public Address setIdentifier(String identifier) {
		return (Address) super.setIdentifier(identifier);
	}
	
	@Override
	public Address setCode(String code) {
		return (Address) super.setCode(code);
	}
	
	@Override
	public Address setName(String name) {
		return (Address) super.setName(name);
	}
	
}
