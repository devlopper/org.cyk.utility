package org.cyk.utility.network.protocol;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.map.AbstractMapInstanceImpl;

@SuppressWarnings("rawtypes")
@ApplicationScoped
public class ProtocolDefaultsImpl extends AbstractMapInstanceImpl<Class, Protocol> implements ProtocolDefaults,Serializable {
	private static final long serialVersionUID = -3150103584667313000L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setSimpleMailTransfer(__inject__(ProtocolSimpleMailTransfer.class));
		setInteractiveMailAccess(__inject__(ProtocolInteractiveMailAccess.class));
	}

	@Override
	public ProtocolSimpleMailTransfer getSimpleMailTransfer() {
		return (ProtocolSimpleMailTransfer) get(ProtocolSimpleMailTransfer.class);
	}

	@Override
	public ProtocolDefaults setSimpleMailTransfer(ProtocolSimpleMailTransfer simpleMailTransfer) {
		set(ProtocolSimpleMailTransfer.class,simpleMailTransfer);
		return this;
	}

	@Override
	public ProtocolInteractiveMailAccess getInteractiveMailAccess() {
		return (ProtocolInteractiveMailAccess) get(ProtocolInteractiveMailAccess.class);
	}

	@Override
	public ProtocolDefaults setInteractiveMailAccess(ProtocolInteractiveMailAccess interactiveMailAccess) {
		set(ProtocolInteractiveMailAccess.class,interactiveMailAccess);
		return this;
	}
	
}
