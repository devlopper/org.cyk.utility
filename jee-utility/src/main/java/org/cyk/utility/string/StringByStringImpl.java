package org.cyk.utility.string;

import java.io.Serializable;

import org.cyk.utility.map.AbstractMapInstanceImpl;

public class StringByStringImpl extends AbstractMapInstanceImpl<String, String> implements StringByString,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public StringByString setEntrySeparator(Object entrySeparator) {
		return (StringByString) super.setEntrySeparator(entrySeparator);
	}
	
	@Override
	public StringByString setKeyValueSeparator(Object keyValueSeparator) {
		return (StringByString) super.setKeyValueSeparator(keyValueSeparator);
	}
}
