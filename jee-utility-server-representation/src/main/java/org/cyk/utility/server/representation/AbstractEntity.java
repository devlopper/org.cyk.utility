package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.server.persistence.jpa.Persistence;
import org.cyk.utility.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractEntity extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identifier;
	private String code;
	
	protected <T> T __getFromBusinessIdentifier__(Class<T> aClass,Object identifier){
		return identifier == null ? null : __inject__(Persistence.class).readOne(aClass,identifier,new Properties().setValueUsageType(ValueUsageType.BUSINESS));
	}
	
	protected Integer __getIntegerFrom__(Object object) {
		return __inject__(NumberHelper.class).getInteger(object);
	}
}
