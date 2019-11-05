package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class PersonType extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersonType setIdentifier(String identifier) {
		return (PersonType) super.setIdentifier(identifier);
	}
	
	@Override
	public PersonType setCode(String code) {
		return (PersonType) super.setCode(code);
	}
	
	@Override
	public PersonType setName(String name) {
		return (PersonType) super.setName(name);
	}
	
}
