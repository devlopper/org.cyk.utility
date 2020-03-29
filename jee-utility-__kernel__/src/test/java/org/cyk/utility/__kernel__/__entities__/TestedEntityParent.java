package org.cyk.utility.__kernel__.__entities__;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestedEntityParent extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl {

	@Basic(fetch = FetchType.LAZY)
	private String lazy;
	
	@Basic(fetch = FetchType.EAGER)
	private String eager;
	
	public TestedEntityParent(String identifier, String code, String name) {
		super();
		this.identifier = identifier;
		this.code = code;
		this.name = name;
	}
	
	public TestedEntityParent(String identifier, String code, String name,String lazy,String eager) {
		super();
		this.identifier = identifier;
		this.code = code;
		this.name = name;
		this.lazy = lazy;
		this.eager = eager;
	}
	
	@Override
	public String toString() {
		return identifier+" "+code+" "+name;
	}
}
