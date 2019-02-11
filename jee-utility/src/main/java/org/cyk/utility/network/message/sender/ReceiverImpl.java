package org.cyk.utility.network.message.sender;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class ReceiverImpl extends AbstractObject implements Receiver,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Receiver setIdentifier(Object identifier) {
		return (Receiver) super.setIdentifier(identifier);
	}
	
}
