package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class Namable extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Namable setIdentifier(String identifier) {
		return (Namable) super.setIdentifier(identifier);
	}
	
	@Override
	public Namable setCode(String code) {
		return (Namable) super.setCode(code);
	}
	
	@Override
	public Namable setName(String name) {
		return (Namable) super.setName(name);
	}
	
}
