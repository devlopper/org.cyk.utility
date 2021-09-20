package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl extends AbstractIdentifiableSystemScalarStringImpl implements IdentifiableBusiness<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(name=COLUMN_CODE,nullable=false,unique=true)
	protected String code;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl(String identifier,String code) {
		super(identifier);
		this.code = code;
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl(String code) {
		this(null,code);
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
		if(StringHelper.isBlank(code))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	
	public static final String COLUMN_CODE = FIELD_CODE;
}
