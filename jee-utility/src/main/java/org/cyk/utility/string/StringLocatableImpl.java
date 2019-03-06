package org.cyk.utility.string;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class StringLocatableImpl extends AbstractObject implements StringLocatable,Serializable {
	private static final long serialVersionUID = 1L;

	private String string;
	private StringLocation location;
	
	@Override
	public String getString() {
		return string;
	}

	@Override
	public StringLocatable setString(String string) {
		this.string = string;
		return this;
	}

	@Override
	public StringLocation getLocation() {
		return location;
	}

	@Override
	public StringLocatable setLocation(StringLocation location) {
		this.location = location;
		return this;
	}

	@Override
	public String toString() {
		return string+"/"+location;
	}
}
