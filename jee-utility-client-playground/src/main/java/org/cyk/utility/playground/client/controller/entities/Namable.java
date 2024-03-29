package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.controller.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Namable extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String __code__;
	private String __name__;
	private String codeStyled;
	
	{
		setCodeStyled(getCode());
	}
	
	@Override
	public String toString() {
		return getCode()+" "+getName();
	}
}
