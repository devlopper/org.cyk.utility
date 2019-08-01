package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.collection.CollectionInstance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractIdentifiedByStringAndCoded<ENTITY,COLLECTION extends CollectionInstance<ENTITY>> extends AbstractIdentifiedByString<ENTITY,COLLECTION> implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull @Column(name=COLUMN_CODE,nullable=false,unique=true)
	protected String code;

	@Override
	protected String getRuntimeIdentifier() {
		return getCode();
	}
	
	@Override
	public String toString() {
		return getCode();
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	
	public static final String COLUMN_CODE = FIELD_CODE;
}