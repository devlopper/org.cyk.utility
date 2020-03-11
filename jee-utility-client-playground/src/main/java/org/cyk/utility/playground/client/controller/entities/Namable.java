package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Namable extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String __code__;
	private String __name__;
	
	public String getCodeStyled() {
		return getCode();
	}
	
	@Override
	public String toString() {
		return getCode()+" "+getName();
	}
}
