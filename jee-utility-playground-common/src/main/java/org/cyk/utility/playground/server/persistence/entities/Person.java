package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.persistence.AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@Entity @Access(AccessType.FIELD)
public class Person extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastNames;
	
	@NotNull
	@ManyToOne
	private PersonType type;
	
}
