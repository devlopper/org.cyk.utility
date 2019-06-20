package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class MessageImpl extends AbstractObject implements Message,Serializable {
	private static final long serialVersionUID = 1L;

	private Object key,value;
	
	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public Message setKey(Object key) {
		this.key = key;
		return this;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public Message setValue(Object value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		return "("+key+","+value+")";
	}
	
}
