package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemImpl<IDENTIFIER> extends AbstractObjectImpl implements IdentifiableSystem<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient protected String asString;
	
	protected abstract IDENTIFIER getIdentifier();
	
	protected abstract AbstractIdentifiableSystemImpl<IDENTIFIER> setIdentifier(IDENTIFIER identifier);
	
	@Override
	public IDENTIFIER getSystemIdentifier() {
		return getIdentifier();
	}

	@Override
	public IdentifiableSystem<IDENTIFIER> setSystemIdentifier(IDENTIFIER identifier) {
		setIdentifier(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		IDENTIFIER identifier = getIdentifier();
		if(identifier == null)
			return super.toString();
		return identifier.toString();
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_AS_STRING = "asString";
}