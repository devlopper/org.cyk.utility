package org.cyk.utility.network.message.sender;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.network.message.SenderReader;
import org.cyk.utility.network.protocol.Protocol;

public abstract class AbstractSenderReaderImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SenderReader,Serializable {
	private static final long serialVersionUID = 6428760240698553361L;

	private Protocol protocol;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setProtocol(__inject__(__getProtocolClass__()));
	}
	
	@Override
	protected void ____execute____() throws Exception {
		Protocol protocol = getProtocol();
		throwRuntimeExceptionIfIsNull(protocol,"protocol");
		if(Boolean.TRUE.equals(protocol.getIsAuthenticationRequired()))
			throwRuntimeExceptionIfIsNull(protocol.getAuthenticationCredentials(),"credentials");
		______execute______(protocol);
	}
	
	protected abstract void ______execute______(Protocol protocol) throws Exception;
	
	protected abstract Class<? extends Protocol> __getProtocolClass__();
	
	@Override
	public Protocol getProtocol() {
		return protocol;
	}
	
	@Override
	public SenderReader setProtocol(Protocol protocol) {
		this.protocol = protocol;
		return this;
	}
	
}
