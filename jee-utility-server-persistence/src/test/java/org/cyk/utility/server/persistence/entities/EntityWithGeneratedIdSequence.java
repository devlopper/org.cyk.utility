package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
@Access(AccessType.FIELD)
public class EntityWithGeneratedIdSequence implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer identifier;
	
}
