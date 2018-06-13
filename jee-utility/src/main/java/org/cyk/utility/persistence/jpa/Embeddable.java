package org.cyk.utility.persistence.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass
public class Embeddable extends org.cyk.utility.__kernel__.object.__static__.identifiable.Embeddable.BaseClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
}