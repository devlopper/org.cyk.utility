package org.cyk.utility.persistence.entity;

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

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringNamableImpl extends AbstractIdentifiableSystemScalarStringImpl implements Namable,Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(name=COLUMN_NAME,nullable=false)
	protected String name;
	
	public AbstractIdentifiableSystemScalarStringNamableImpl(String identifier,String name) {
		super(identifier);
		this.name = name;
	}
	
	public AbstractIdentifiableSystemScalarStringNamableImpl(String name) {
		this(null,name);
	}
	
	@Override
	public String toString() {
		String string = getName();
		if(StringHelper.isBlank(string))
			return super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
	
	public static final String COLUMN_NAME = FIELD_NAME;
}