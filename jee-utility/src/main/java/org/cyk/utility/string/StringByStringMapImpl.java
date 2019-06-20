package org.cyk.utility.string;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@Dependent
public class StringByStringMapImpl extends AbstractMapInstanceImpl<String, String> implements StringByStringMap,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public StringByStringMap setIsOrdered(Boolean isOrdered) {
		return (StringByStringMap) super.setIsOrdered(isOrdered);
	}
	
	@Override
	public StringByStringMap setIsSequential(Boolean isSequential) {
		return (StringByStringMap) super.setIsSequential(isSequential);
	}
	
	@Override
	public StringByStringMap setEntrySeparator(Object entrySeparator) {
		return (StringByStringMap) super.setEntrySeparator(entrySeparator);
	}
	
	@Override
	public StringByStringMap setKeyValueSeparator(Object keyValueSeparator) {
		return (StringByStringMap) super.setKeyValueSeparator(keyValueSeparator);
	}
}
