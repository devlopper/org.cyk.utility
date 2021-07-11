package org.cyk.utility.representation.entity;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Namable,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl(String identifier,String code,String name) {
		super(identifier,code);
		setName(name);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl(String code,String name) {
		super(code);
		setName(name);
	}
	
	public AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl(String name) {
		setName(name);
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
}
