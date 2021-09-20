package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringImpl extends AbstractIdentifiableSystemScalarStringBoundedContextImpl implements IdentifiableBusiness<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient protected String code;
	
	public AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringImpl(String code) {
		this.code = code;
	}
	
	@Override
	public String getBusinessIdentifier() {
		return getCode();
	}
	
	@Override
	public IdentifiableBusiness<String> setBusinessIdentifier(String identifier) {
		setCode(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(string))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
}
