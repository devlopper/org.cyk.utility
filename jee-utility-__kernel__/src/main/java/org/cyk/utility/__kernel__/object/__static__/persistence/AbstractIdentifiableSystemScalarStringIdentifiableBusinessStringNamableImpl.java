package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Namable,Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(name=COLUMN_NAME,nullable=false)
	protected String name;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl(String code,String name) {
		super(code);
		this.name = name;
	}
	
	@Override
	public String toString() {
		String string = getCode();
		if(StringHelper.isBlank(code))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
	
	public static final String COLUMN_NAME = FIELD_NAME;
}
