package org.cyk.utility.network.message;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Dependent
public class ReceiverImpl extends AbstractObject implements Receiver,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Receiver setIdentifier(Object identifier) {
		return (Receiver) super.setIdentifier(identifier);
	}
	
}
