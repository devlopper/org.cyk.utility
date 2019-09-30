package org.cyk.utility.network.message;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.network.protocol.Protocol;
import org.cyk.utility.network.protocol.ProtocolDefaults;

public abstract class AbstractSenderReaderImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements SenderReader,Serializable {
	private static final long serialVersionUID = 6428760240698553361L;

	private Protocol protocol;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		//setProtocol(__inject__(__getProtocolClass__()));
	}
	
	@Override
	protected void ____execute____() throws Exception {
		Protocol protocol = getProtocol();
		if(protocol == null)
			protocol = __inject__(ProtocolDefaults.class).get(__getProtocolClass__());
		if(protocol == null)
			throw new RuntimeException("message sender/reader : protocol is required");
		if(Boolean.TRUE.equals(protocol.getIsAuthenticationRequired()))
			if(protocol.getAuthenticationCredentials() == null)
				throw new RuntimeException("message sender/reader : protocol credentials are required");
		______execute______(protocol);
	}
	
	protected abstract void ______execute______(Protocol protocol) throws Exception;
	
	protected abstract Class<? extends Protocol> __getProtocolClass__();
	
	@Override
	public Protocol getProtocol() {
		return protocol;
	}
	
	@Override
	public Protocol getProtocol(Boolean injectIfNull) {
		Protocol protocol = getProtocol();
		if(protocol == null)
			setProtocol(protocol = __inject__(__getProtocolClass__()));
		return protocol;
	}
	
	@Override
	public SenderReader setProtocol(Protocol protocol) {
		this.protocol = protocol;
		return this;
	}
	
	/**/
	
	public static final String FIELD_PROTOCOL = "protocol";
}
