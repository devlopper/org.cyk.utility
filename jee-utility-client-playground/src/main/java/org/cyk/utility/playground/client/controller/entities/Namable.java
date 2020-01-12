package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Namable extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringNamableImpl implements Serializable {
	private static final long serialVersionUID = 1L;

}
