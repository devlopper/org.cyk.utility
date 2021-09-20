package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringNamableImpl extends AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient protected String name;
	
	public AbstractIdentifiableSystemScalarStringBoundedContextIdentifiableBusinessStringNamableImpl(String name) {
		this.name = name;
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
	
}
