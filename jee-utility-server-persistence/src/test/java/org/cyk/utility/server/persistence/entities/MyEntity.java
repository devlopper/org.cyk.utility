package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCoded;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
@Access(AccessType.FIELD)
public class MyEntity extends AbstractIdentifiedByStringAndCoded implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Basic(fetch=FetchType.LAZY)
	private Integer integerValue;
	
	@Basic(fetch=FetchType.LAZY)
	private byte[] bytes;
	
	@ElementCollection
	private List<String> phones;
	
	@ManyToOne
	private MyEntityDetail detail;
	
	@Override
	public MyEntity setIdentifier(String identifier) {
		return (MyEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
		
	/**/
	
	public static final String FIELD_INTEGER_VALUE = "integerValue";
}
