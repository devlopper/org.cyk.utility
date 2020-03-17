package org.cyk.utility.__kernel__.object.__static__.controller;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter /*@Accessors(chain=true)/* : because of jsf not supporting chain setter */ @EqualsAndHashCode(callSuper = false,of = "identifier")
public abstract class AbstractDataIdentifiableSystemStringImpl extends AbstractDataImpl implements IdentifiableSystem<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @NotNull
	private String identifier;
	
	@Override
	public String getSystemIdentifier() {
		return getIdentifier();
	}
	
	@Override
	public IdentifiableSystem<String> setSystemIdentifier(String identifier) {
		setIdentifier(identifier);
		return this;
	}
	
	@Override
	public String toString() {
		String string = getIdentifier();
		if(StringHelper.isBlank(string))
			string = super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
}