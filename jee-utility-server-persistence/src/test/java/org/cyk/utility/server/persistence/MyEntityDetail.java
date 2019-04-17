package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
@Access(AccessType.FIELD)
public class MyEntityDetail extends AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Basic(fetch=FetchType.LAZY)
	private Integer integerValue;
	
	@Basic(fetch=FetchType.LAZY)
	private byte[] bytes;
	
	@Basic(fetch=FetchType.LAZY) @ElementCollection
	private List<String> lines;
		
}
