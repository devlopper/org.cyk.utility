package org.cyk.utility.__kernel__.object.__static__.controller;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Column;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter /*@Accessors(chain=true)*/
public abstract class AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Input @NotNull
	@Column
	protected String name;

	@Override
	public String toString() {
		String string = getName();
		if(StringHelper.isBlank(string))
			string = super.toString();
		return string;
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
}