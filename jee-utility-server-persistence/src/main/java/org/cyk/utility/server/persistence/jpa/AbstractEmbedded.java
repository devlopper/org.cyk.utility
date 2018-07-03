package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass
public abstract class AbstractEmbedded extends org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractEmbedded implements Serializable {
	private static final long serialVersionUID = 1L;
	
}