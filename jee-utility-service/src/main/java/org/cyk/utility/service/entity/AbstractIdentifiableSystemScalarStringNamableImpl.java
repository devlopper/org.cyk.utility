package org.cyk.utility.service.entity;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringNamableImpl extends AbstractIdentifiableSystemScalarStringImpl implements Namable,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	public AbstractIdentifiableSystemScalarStringNamableImpl(String identifier,String name) {
		super(identifier);
		setName(name);
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
}