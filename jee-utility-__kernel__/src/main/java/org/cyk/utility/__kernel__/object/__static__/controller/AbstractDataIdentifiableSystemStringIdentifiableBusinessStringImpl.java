package org.cyk.utility.__kernel__.object.__static__.controller;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Column;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter /*@Accessors(chain=true) /*: because of jsf not supporting chain setter*/
public abstract class AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl extends AbstractDataIdentifiableSystemStringImpl implements IdentifiableBusiness<String>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @NotNull
	@Column	
	protected String code;
	
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
			string = super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
}