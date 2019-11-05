package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.Namable;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringNamableImpl extends AbstractIdentifiableSystemScalarStringIdentifiableBusinessStringImpl implements Namable,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
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
