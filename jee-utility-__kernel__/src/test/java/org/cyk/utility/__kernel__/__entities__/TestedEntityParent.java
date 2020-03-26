package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityParent extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	public TestedEntityParent(String identifier, String code, String name) {
		super();
		this.identifier = identifier;
		this.code = code;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return identifier+" "+code+" "+name;
	}
}
